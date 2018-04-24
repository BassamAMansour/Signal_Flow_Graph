package model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HANAN SAMIR
 *
 */
public class Node implements INode {
	private String nodeName;
	private List<IDirectedEdge> edges;

	public Node(String nodeName) {
		this.nodeName = nodeName;
		this.edges = new ArrayList<IDirectedEdge>();
	}

	@Override
	public String getNodeName() {
		return this.nodeName;
	}

	@Override
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;

	}

	@Override
	public List<IDirectedEdge> getOutwardEdges() {
		return this.edges;
	}

	@Override
	public void addEdge(IDirectedEdge edge) {
		edges.add(edge);

	}

	@Override
	public void removeEdges() {
		this.edges.clear();

	}

}
