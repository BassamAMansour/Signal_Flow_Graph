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
    List<IPath> getForwardPaths(INode inputNode, INode outputNode);

	/**
	 * get all loops.
	 * 
	 * @return loops.
	 */
    List<IPath> getIndividualLoops();

	/**
	 * get all non-touching loops.
	 */
    List<List<List<IPath>>> getNonTouchingLoops();

	/**
	 * get delta for all paths.
	 * 
	 * @return deltas.
	 */
    List<Float> getDeltas();

	/**
	 * get OverAll Transfer Function.
     *
     * @param inputNode.
     * @param outputNode.
     * @return OverAllTransferFunction.
	 */
    Float getOverAllTransferFunction(INode inputNode, INode outputNode);

}
