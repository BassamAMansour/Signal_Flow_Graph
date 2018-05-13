package view;

import model.DirectedEdge;
import model.INode;
import model.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphInput {
    private final HashMap<String, INode> nodesMap;
    private JFormattedTextField tfVertexName;
    private JButton btnAddVertex;
    private JFormattedTextField tfFEdgeSource;
    private JFormattedTextField tfEdgeDestination;
    private JButton btnAddEdge;
    private JFormattedTextField tfEdgeGain;
    private JButton btnStartPlottingButton;
    private JPanel mainPanel;
    private JFormattedTextField tfSourceNode;
    private JFormattedTextField tfSinkNode;
    private JButton btnSetSourceNode;
    private JButton btnSetSink;
    private INode sinkNode;
    private INode sourceNode;

    public GraphInput() {
        this.nodesMap = new HashMap<>();
        setupViews();
    }

    public static void main(String[] args) {
        GraphInput graphInput = new GraphInput();
        JFrame frame = new JFrame();
        frame.setContentPane(graphInput.mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void setupViews() {
        btnAddVertex.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nodeName = tfVertexName.getText();
                if (!nodeName.isEmpty()) {
                    nodesMap.put(nodeName, new Node(nodeName));
                }
                tfVertexName.setText("");
            }
        });

        btnAddEdge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourceNodeName = tfFEdgeSource.getText();
                String destinationNodeName = tfEdgeDestination.getText();
                String gain = tfEdgeGain.getText();

                if (nodesMap.size() < 2 ||
                        sourceNodeName.isEmpty() ||
                        destinationNodeName.isEmpty() ||
                        !nodesMap.containsKey(sourceNodeName) ||
                        !nodesMap.containsKey(destinationNodeName) ||
                        gain.isEmpty()) {
                    //TODO: Show Errors
                } else {
                    nodesMap.get(sourceNodeName).addEdge(new DirectedEdge(nodesMap.get(destinationNodeName), Integer.valueOf(gain)));
                    tfFEdgeSource.setText("");
                    tfEdgeDestination.setText("");
                    tfEdgeGain.setText("");
                }
            }
        });

        btnSetSink.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sinkNodeName = tfSinkNode.getText();
                if (!nodesMap.isEmpty() && nodesMap.containsKey(sinkNodeName)) {
                    sourceNode = nodesMap.get(sinkNodeName);
                }
            }
        });
        btnSetSourceNode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sourceNodeName = tfSourceNode.getText();

                if (!nodesMap.isEmpty() && nodesMap.containsKey(sourceNodeName)) {
                    sourceNode = nodesMap.get(sourceNodeName);
                }

            }
        });

        btnStartPlottingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!nodesMap.isEmpty()) {
                    GraphPlotting graphPlotting = new GraphPlotting(new ArrayList<>(nodesMap.values()), sourceNode, sinkNode);
                    graphPlotting.showGraphInfo();
                    graphPlotting.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    graphPlotting.setVisible(true);
                } else {
                    //TODO: Show Errors
                }
            }
        });
    }
}
