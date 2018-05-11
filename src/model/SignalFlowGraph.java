package model;

import java.util.*;

public class SignalFlowGraph implements ISignalFlowGraph {
	private List<INode> graph;
	private boolean[] visited;
    private List<IPath> forwardPaths, individualLoops;
	private List<ArrayList<IDirectedEdge>> edgesPerPath;
	private Map<String, Integer> nodes;

	public SignalFlowGraph(List<INode> graph) {
		this.graph = graph;
		nodes = new HashMap<String, Integer>();
		visited = new boolean[this.graph.size()];
		int i = 0;
		for (INode node : this.graph) {
			nodes.put(node.getNodeName(), i);
			visited[i++] = false;
		}
		replaceParallelEdges();
	}

	@Override
	public List<IPath> getForwardPaths(INode inputNode, INode outputNode) {
		this.forwardPaths = new ArrayList<IPath>();
		this.edgesPerPath = new ArrayList<ArrayList<IDirectedEdge>>();
		List<IDirectedEdge> takenEdges = new ArrayList<IDirectedEdge>();
		dfs(inputNode, outputNode, takenEdges);
		for (ArrayList<IDirectedEdge> list : this.edgesPerPath) {
			this.forwardPaths.add(getPaths(list, inputNode));
		}
		return this.forwardPaths;
	}

	@Override
	public List<IPath> getIndividualLoops() {
        int values[] = new int[graph.size()];
        int index = 0;
        List<List<IDirectedEdge>> tempLoopEdges = new ArrayList<List<IDirectedEdge>>();
        List<List<IDirectedEdge>> loopEdges = new ArrayList<List<IDirectedEdge>>();
        for (INode node : graph) {
            this.edgesPerPath = new ArrayList<ArrayList<IDirectedEdge>>();
            List<IDirectedEdge> takenEdges = new ArrayList<IDirectedEdge>();
            dfs(node, node, takenEdges);
            tempLoopEdges.addAll(this.edgesPerPath);
            values[index++] = tempLoopEdges.size() - 1;
        }
        loopEdges = removeRepeatedLoops(tempLoopEdges);
        this.individualLoops = new ArrayList<IPath>();
        for (List<IDirectedEdge> list : loopEdges) {
            index = tempLoopEdges.indexOf(list);
            for (int i = 0; i < values.length; i++) {
                if (index <= values[i]) {
                    index = i;
                    break;
                }
            }
            this.individualLoops.add(getPaths(list, graph.get(index)));
        }
        return this.individualLoops;
	}

	@Override
    public List<List<List<IPath>>> getNonTouchingLoops() {
        return getNonTouchingLoops(this.individualLoops);
    }

    private List<List<List<IPath>>> getNonTouchingLoops(List<IPath> individualLoops) {
        List<List<Integer>> adjLoopsInd = new ArrayList<List<Integer>>();
        List<INode> loopNodes1, loopNodes2;
        for (int i = 0; i < individualLoops.size(); i++) {
            adjLoopsInd.add(new ArrayList<Integer>());
            for (int j = i + 1; j < individualLoops.size(); j++) {
                loopNodes1 = getNodes(individualLoops.get(i));
                loopNodes2 = getNodes(individualLoops.get(j));
                boolean temp = true;
                for (INode node : loopNodes1) {
                    if (loopNodes2.contains(node)) {
                        temp = false;
                    }
                }
                if (temp) {
                    adjLoopsInd.get(i).add(j);
                }
            }
        }

        List<List<List<IPath>>> result = new ArrayList<List<List<IPath>>>();
        for (int i = 0; i < individualLoops.size(); i++) {
            result.add(new ArrayList<List<IPath>>());
        }
        for (int i = 0; i < individualLoops.size(); i++) {
            List<Integer> prev = new ArrayList<Integer>();
            prev.add(i);
            dfs(i, adjLoopsInd, individualLoops, prev, result);
            prev.clear();
        }

        return result;
	}

	@Override
	public List<Float> getDeltas() {
        List<Float> deltas = new ArrayList<Float>();
        List<List<IPath>> allIndividualLoops = new ArrayList<List<IPath>>();
        allIndividualLoops.add(this.individualLoops);
        int ind = 1;
        for (IPath forwardPath : this.forwardPaths) {
            allIndividualLoops.add(new ArrayList<IPath>());
            for (IPath loopPath : this.individualLoops) {
                if (check(getNodes(forwardPath), getNodes(loopPath))) {
                    allIndividualLoops.get(ind).add(loopPath);
                }
            }
            ind++;
        }

        for (List<IPath> loops : allIndividualLoops) {
            List<List<List<IPath>>> allLoops = new ArrayList<List<List<IPath>>>();
            allLoops = getNonTouchingLoops(loops);
            float answer = 1;
            int sign = -1;
            for (int i = 0; i < allLoops.size(); i++) {
                float loopsValue = 0;
                for (int j = 0; j < allLoops.get(i).size(); j++) {
                    float value = 1;
                    for (IPath path : allLoops.get(i).get(j)) {
                        value *= path.getPathGain();
                    }
                    loopsValue += value;
                }
                answer += (sign * loopsValue);
                sign *= -1;
            }
            deltas.add(answer);
        }

        return deltas;
	}

	@Override
	public Float getOverAllTransferFunction(INode inputNode, INode outputNode) {
        float answer = 0;
        List<IPath> paths = getForwardPaths(inputNode, outputNode);
        getIndividualLoops();
        List<Float> deltas = getDeltas();
        int n = paths.size();
        for (int i = 1; i <= n; i++) {
            answer += (paths.get(i - 1).getPathGain()) * (deltas.get(i));
        }
        answer /= deltas.get(0);
        return answer;
    }

    private Boolean check(List<INode> forwardPath, List<INode> loopPath) {
        for (INode node : forwardPath) {
            if (loopPath.contains(node))
                return false;
        }
        return true;
	}

	private void replaceParallelEdges() {
		for (INode node : this.graph) {
			List<INode> destinationNodes = new ArrayList<INode>();
			List<Float> gains = new ArrayList<Float>();
			for (IDirectedEdge edge : node.getOutwardEdges()) {
				int index = destinationNodes.indexOf(edge.getNode());
				if (destinationNodes.contains(edge.getNode())) {
					gains.set(index, gains.get(index) + edge.getEdgeGain());
				} else {
					destinationNodes.add(edge.getNode());
					gains.add(edge.getEdgeGain());
				}
			}
			node.removeEdges();
			for (INode desNode : destinationNodes) {
				node.addEdge(new DirectedEdge(desNode, gains.get(destinationNodes.indexOf(desNode))));
			}
		}
	}

	private void dfs(INode inputNode, INode outputNode, List<IDirectedEdge> takenEdges) {
		for (IDirectedEdge edge : inputNode.getOutwardEdges()) {
			INode desNode = edge.getNode();

			if (desNode.getNodeName().equals(outputNode.getNodeName())) {
				takenEdges.add(edge);
				this.edgesPerPath.add((ArrayList<IDirectedEdge>) ((ArrayList<IDirectedEdge>) takenEdges).clone());
				takenEdges.remove(edge);
				continue;
			}
			if (!visited[nodes.get(desNode.getNodeName())]) {
				takenEdges.add(edge);
				visited[nodes.get(desNode.getNodeName())] = true;
				dfs(desNode, outputNode, takenEdges);
				takenEdges.remove(edge);
				visited[nodes.get(desNode.getNodeName())] = false;
			}
		}
	}

	private IPath getPaths(List<IDirectedEdge> list, INode inputNode) {
		IPath path = new Path(inputNode.getNodeName(), 1);
		for (IDirectedEdge edge : list) {
			path.setPathContent(path.getPathContent() + " " + edge.getNode().getNodeName());
			path.setPathGain(path.getPathGain() * edge.getEdgeGain());
		}
		return path;
	}

    private List<INode> getNodes(IPath path) {
        List<INode> pathNodes = new ArrayList<INode>();
        String[] content = path.getPathContent().split(" ");
        for (String node : content) {
            pathNodes.add(graph.get(nodes.get(node)));
        }
        return pathNodes;
    }

    private List<List<IDirectedEdge>> removeRepeatedLoops(List<List<IDirectedEdge>> loopEdges) {
        List<List<IDirectedEdge>> edges = new ArrayList<List<IDirectedEdge>>();
        boolean visited[] = new boolean[loopEdges.size()];
        Arrays.fill(visited, false);
        for (int i = 0; i < loopEdges.size(); i++) {
            if (visited[i]) {
                continue;
            }
            edges.add(loopEdges.get(i));
            visited[i] = true;
            for (int j = i + 1; j < loopEdges.size(); j++) {
                if (equalLoops(loopEdges.get(i), loopEdges.get(j))) {
                    visited[j] = true;
                }
            }
        }
        return edges;
    }

    private boolean equalLoops(List<IDirectedEdge> loop1, List<IDirectedEdge> loop2) {
        if (loop1.size() != loop2.size()) {
            return false;
        }
        for (IDirectedEdge edge : loop1) {
            if (!loop2.contains(edge)) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int node, List<List<Integer>> adjList, List<IPath> individualLoops, List<Integer> prevLoops,
                     List<List<List<IPath>>> answer) {
        for (Integer child : adjList.get(node)) {
            if (checkNonTouching(child, prevLoops, individualLoops)) {
                prevLoops.add(child);
                dfs(child, adjList, individualLoops, prevLoops, answer);
                prevLoops.remove((prevLoops.size() - 1));
            }
        }
        int size = prevLoops.size();
        List<IPath> temp = new ArrayList<IPath>();
        for (int i = 0; i < size; i++) {
            temp.add(individualLoops.get(prevLoops.get(i)));
        }
        answer.get(size - 1).add(temp);
    }

    private Boolean checkNonTouching(int currLoop, List<Integer> prevLoops, List<IPath> indvidualLoops) {
        List<INode> loop1 = getNodes(indvidualLoops.get(currLoop));
        for (Integer loopIndex : prevLoops) {
            List<INode> loop2 = getNodes(indvidualLoops.get(loopIndex));
            for (INode node : loop1) {
                if (loop2.contains(node))
                    return false;
            }
        }
        return true;
    }
}
