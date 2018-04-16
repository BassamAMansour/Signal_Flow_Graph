package model;

import java.util.HashSet;

public class SignalFlowGraph {

    private HashSet<SourceNode> sourceNodes;
    private SinkNode sinkNode;
    private HashSet<DirectedEdge> edges;
    private HashSet<Node> nodes;

    public SignalFlowGraph() {
        this.sourceNodes = new HashSet<>(5);
        this.sinkNode = null;
        this.edges = new HashSet<>();
        this.nodes = new HashSet<>();
    }

    public boolean addSourceNode(SourceNode sourceNode) {
        return sourceNodes.add(sourceNode);
    }

    public boolean removeSourceNode(SourceNode sourceNode) {
        return sourceNodes.remove(sourceNode);
    }

    public boolean addEdge(DirectedEdge edge) {
        return edges.add(edge);
    }

    public boolean removeEdge(DirectedEdge edge) {
        return edges.remove(edge);
    }

    public boolean addNode(Node node) {
        return nodes.add(node);
    }

    public boolean removeNode(Node node) {
        return nodes.remove(node);
    }

    public HashSet<SourceNode> getSourceNodes() {
        return sourceNodes;
    }

    public void setSourceNodes(HashSet<SourceNode> sourceNodes) {
        this.sourceNodes = sourceNodes;
    }

    public SinkNode getSinkNode() {
        return sinkNode;
    }

    public void setSinkNode(SinkNode sinkNode) {
        this.sinkNode = sinkNode;
    }

    public HashSet<DirectedEdge> getEdges() {
        return edges;
    }

    public void setEdges(HashSet<DirectedEdge> edges) {
        this.edges = edges;
    }

    public HashSet<Node> getNodes() {
        return nodes;
    }

    public void setNodes(HashSet<Node> nodes) {
        this.nodes = nodes;
    }
}
