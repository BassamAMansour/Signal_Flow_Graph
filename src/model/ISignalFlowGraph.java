package model;

import java.util.List;

public interface ISignalFlowGraph {
	/**
	 * get all forward paths from source node and sink node.
	 * 
	 * @param inputNode.
	 * @param outputNode.
	 * @return forwardPaths.
	 */
	public List<IPath> getForwardPaths(INode inputNode, INode outputNode);

	/**
	 * get all loops.
	 * 
	 * @return loops.
	 */
	public List<IPath> getIndividualLoops();

	/**
	 * get all non-touching loops.
	 */
	public void getNonTouchingLoops();

	/**
	 * get delta for all paths.
	 * 
	 * @return deltas.
	 */
	public List<Float> getDeltas();

	/**
	 * get OverAll Transfer Function.
	 * 
	 * @param inputNode.
	 * @param outputNode.
	 * @return OverAllTransferFunction.
	 */
	public Float getOverAllTransferFunction(INode inputNode, INode outputNode);

}
