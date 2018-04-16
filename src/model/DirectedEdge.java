package model;

public class DirectedEdge {

    private Node originNode;
    private Node destinationNode;
    private double gain;

    public DirectedEdge(Node originNode, Node destinationNode, double gain) {
        this.originNode = originNode;
        this.destinationNode = destinationNode;
        this.gain = gain;
    }

    public DirectedEdge(double gain) {
        new DirectedEdge(null, null, gain);
    }

    public Node getOriginNode() {
        return originNode;
    }

    public void setOriginNode(Node originNode) {
        this.originNode = originNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }
}
