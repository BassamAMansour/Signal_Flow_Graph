package utils;

import model.DirectedEdge;
import model.INode;
import model.Node;

import java.util.ArrayList;
import java.util.List;

public class GraphFileReader {
    public static List<INode> getGraphNodes(String filePath) {
        //TODO: delete that rubbish and add real file reader code :DDD
        List<INode> graph = new ArrayList<INode>();
        INode node1 = new Node("y1");
        INode node2 = new Node("y2");
        INode node3 = new Node("y3");
        INode node4 = new Node("y4");
        INode node5 = new Node("y5");
        INode node6 = new Node("y6");
        node1.addEdge(new DirectedEdge(node2, 1));
        node2.addEdge(new DirectedEdge(node3, 5));
        node2.addEdge(new DirectedEdge(node6, 10));
        node3.addEdge(new DirectedEdge(node4, 10));
        node4.addEdge(new DirectedEdge(node3, -1));
        node4.addEdge(new DirectedEdge(node5, 2));
        node5.addEdge(new DirectedEdge(node4, -2));
        node5.addEdge(new DirectedEdge(node2, -1));
        node6.addEdge(new DirectedEdge(node5, 2));
        node6.addEdge(new DirectedEdge(node6, -1));
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);
        return graph;
    }
}
