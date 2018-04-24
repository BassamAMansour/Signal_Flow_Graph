package model;

public interface IDirectedEdge {

	/**
	 * get the destination node.
	 * @return destinationNode.
	 */
	public INode getNode();
	/**
	 * get edge gain.
	 * @return gain.
	 */
	public float getEdgeGain();
}
