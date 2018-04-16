package model;

import java.util.HashSet;

public class SinkNode extends Node {
    public SinkNode(HashSet<DirectedEdge> inwardEdges, String nodeName) {
        super(null, inwardEdges, nodeName);
    }

    public SinkNode(String nodeName) {
        this(null, nodeName);
    }

    @Override
    public void setOutwardEdges(HashSet<DirectedEdge> outwardEdges) {
        throw new UnsupportedOperationException("Cannot assign outward edge to a sink node.");
    }
}
