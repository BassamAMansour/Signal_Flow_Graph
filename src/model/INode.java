package model;

import java.util.List;

public interface INode {
	/**
	 * get node name.
	 * @return nodeName.
	 */
	String getNodeName();
	/**
	 * set the node name.
	 * @param nodeName
	 */
	void setNodeName(String nodeName);
	/**
	 * get all outward edges connected to node.
	 * @return edges.
	 */
	List<IDirectedEdge> getOutwardEdges();
	/**
	 * add edge to node.
	 * @param edge
	 */
	void addEdge(IDirectedEdge edge);
	/**
	 * remove all outward edges connected to a node.
	 */
	void removeEdges();

}
