package model;

import java.util.List;

public interface INode {
	/**
	 * get node name.
	 * @return nodeName.
	 */
	public String getNodeName();
	/**
	 * set the node name.
	 * @param nodeName.
	 */
	public void setNodeName(String nodeName);
	/**
	 * get all outward edges connected to node.
	 * @return edges.
	 */
	public List<IDirectedEdge> getOutwardEdges();
	/**
	 * add edge to node.
	 * @param edge.
	 */
	public void addEdge(IDirectedEdge edge);
	/**
	 * remove all outward edges connected to a node.
	 */
	public void removeEdges();

}
