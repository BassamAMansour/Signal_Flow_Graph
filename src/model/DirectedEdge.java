package model;

/**
 * @author HANAN SAMIR
 *
 */
public class DirectedEdge implements IDirectedEdge {

	private INode destinationNode;
	private float gain;

	public DirectedEdge(INode destinationNode, float gain) {
		this.destinationNode = destinationNode;
		this.gain = gain;

	}

	@Override
	public INode getNode() {
		return this.destinationNode;
	}

	@Override
	public float getEdgeGain() {
		return this.gain;
	}

}
