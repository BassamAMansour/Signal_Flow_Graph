package model;

import java.util.HashSet;

public class SourceNode extends Node {
    public SourceNode(HashSet<DirectedEdge> outwardEdges, String nodeName) {
        super(outwardEdges, null, nodeName);
    }

    public SourceNode(String nodeName) {
        this(null, nodeName);
    }

    @Override
    public void setInwardEdges(HashSet<DirectedEdge> inwardEdges) {
        throw new UnsupportedOperationException("Cannot assign inward edge to a source node.");
    }
}
