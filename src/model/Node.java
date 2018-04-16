package model;

import java.util.HashSet;

public class Node {

    private HashSet<DirectedEdge> outwardEdges;
    private HashSet<DirectedEdge> inwardEdges;
    private String nodeName;

    public Node(HashSet<DirectedEdge> outwardEdges, HashSet<DirectedEdge> inwardEdges, String nodeName) {
        this.outwardEdges = outwardEdges;
        this.inwardEdges = inwardEdges;
        this.nodeName = nodeName;
    }

    public Node(String nodeName) {
        this.nodeName = nodeName;
    }

    public void addInwardEdge(DirectedEdge inwardEdge) {
        inwardEdge.setDestinationNode(this);
        inwardEdges.add(inwardEdge);
    }
    public boolean removeInwardEdge(DirectedEdge inwardEdge){
        inwardEdge.setDestinationNode(null);
        return inwardEdges.remove(inwardEdge);
    }

    public void addOutwardEdge(DirectedEdge outwardEdge) {
        outwardEdge.setOriginNode(this);
        outwardEdges.add(outwardEdge);
    }
    public boolean removeOutwardEdge(DirectedEdge outwardEdge){
        outwardEdge.setOriginNode(null);
        return outwardEdges.remove(outwardEdge);
    }

    public HashSet<DirectedEdge> getOutwardEdges() {
        return outwardEdges;
    }

    public void setOutwardEdges(HashSet<DirectedEdge> outwardEdges) {
        this.outwardEdges = outwardEdges;
    }

    public HashSet<DirectedEdge> getInwardEdges() {
        return inwardEdges;
    }

    public void setInwardEdges(HashSet<DirectedEdge> inwardEdges) {
        this.inwardEdges = inwardEdges;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean equals(Node node) {
        return node.getNodeName().equals(getNodeName());
    }
}
