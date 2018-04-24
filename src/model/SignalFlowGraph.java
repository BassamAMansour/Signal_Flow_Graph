package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignalFlowGraph implements ISignalFlowGraph {
	private List<INode> graph;
	private boolean[] visited;
	private List<IPath> forwardPaths;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void getNonTouchingLoops() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Float> getDeltas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Float getOverAllTransferFunction(INode inputNode, INode outputNode) {
		// TODO Auto-generated method stub
		return null;
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

}
