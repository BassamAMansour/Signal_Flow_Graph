package view;

import com.mxgraph.layout.mxCircleLayout;
import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import model.*;
import utils.GraphFileReader;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class GraphPlotting extends JFrame {

    private static final int DEFAULT_BORDER = 30;
    private static final String KEY_VERTEX_STYLE = "vertex_style";
    private static final String KEY_EDGE_STYLE = "edge_style";
    private static final String PANEL_TITLE = "Signal Flow Diagram Plotter";
    ISignalFlowGraph signalFlowGraph;
    mxGraph graph;
    Object parent;
    private List<INode> nodes;
    private Map<String, Object> graphVerticesMap;

    public GraphPlotting(String graphFilePath) throws HeadlessException {
        this.nodes = GraphFileReader.getGraphNodes(graphFilePath);
        this.graphVerticesMap = new HashMap<>(nodes.size());
        this.signalFlowGraph = new SignalFlowGraph(nodes);
        this.graph = new mxGraph();
        this.parent = graph.getDefaultParent();

        setSize(Toolkit.getDefaultToolkit().getScreenSize());

        addVerticesStyle();
        addEdgesStyle();
        addGraphComponents();
        setPlotProperties();
        setCircleLayout();
        new mxParallelEdgeLayout(graph).execute(parent);

        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphComponent.setBackground(new Color(255, 58, 101));
        getContentPane().add(graphComponent);
        setTitle(PANEL_TITLE);
    }

    private void setCircleLayout() {
        mxCircleLayout circleLayout = new mxCircleLayout(graph);
        circleLayout.setRadius(getHeight() / 3);
        circleLayout.setMoveCircle(true);
        circleLayout.setX0(getWidth() / 4);
        circleLayout.setResetEdges(false);
        circleLayout.execute(parent);
    }

    private void addVerticesStyle() {
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<>();
        style.put(mxConstants.STYLE_SHAPE, mxConstants.SHAPE_ELLIPSE);
        style.put(mxConstants.STYLE_FONTSIZE, 20);
        style.put(mxConstants.STYLE_FILLCOLOR, "#ce81ab");
        style.put(mxConstants.STYLE_FONTCOLOR, graph.getStylesheet().getDefaultEdgeStyle().get(mxConstants.STYLE_FONTCOLOR));
        stylesheet.putCellStyle(KEY_VERTEX_STYLE, style);
    }

    private void addEdgesStyle() {
        mxStylesheet stylesheet = graph.getStylesheet();
        Hashtable<String, Object> style = new Hashtable<>();
        style.put(mxConstants.STYLE_ROUNDED, true);
        style.put(mxConstants.STYLE_EDGE, mxConstants.EDGESTYLE_SEGMENT);
        style.put(mxConstants.STYLE_ALIGN, mxConstants.ALIGN_TOP);
        style.put(mxConstants.STYLE_FONTSIZE, 15);
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(KEY_EDGE_STYLE, style);
    }

    private void setPlotProperties() {
        graph.setBorder(DEFAULT_BORDER);
        graph.setAllowLoops(true);
        graph.setAutoOrigin(true);
        graph.setAllowDanglingEdges(false);
        graph.setAutoSizeCells(true);
    }

    private void addGraphComponents() {

        int widthIncrement = getWidth() * 2 / nodes.size();
        int heightIncrement = getHeight() * 2 / nodes.size();
        int vertexWidth = widthIncrement / 6;
        int vertexHeight = heightIncrement / 4;

        graph.getModel().beginUpdate();

        try {
            int xBocksIterator = -1;
            int yBlocksIterator = 0;
            for (int i = 0; i < nodes.size(); i++) {

                if (yBlocksIterator == 0) {
                    xBocksIterator++;
                }

                graphVerticesMap.put(nodes.get(i).getNodeName(), graph.insertVertex(parent,
                        nodes.get(i).getNodeName(),
                        nodes.get(i).getNodeName(),
                        (widthIncrement * xBocksIterator) + (widthIncrement / 4),
                        (heightIncrement * yBlocksIterator) + (heightIncrement / 4),
                        vertexWidth,
                        vertexHeight,
                        KEY_VERTEX_STYLE));

                if (yBlocksIterator == (nodes.size() / 2) - 1) {
                    yBlocksIterator = -1;
                }

                yBlocksIterator++;
            }

            for (INode node : nodes) {
                for (IDirectedEdge edge : node.getOutwardEdges()) {
                    graph.insertEdge(parent,
                            String.valueOf(edge.getEdgeGain()),
                            String.valueOf(edge.getEdgeGain()),
                            graphVerticesMap.get(node.getNodeName()),
                            graphVerticesMap.get(edge.getNode().getNodeName()),
                            KEY_EDGE_STYLE);
                }
            }
        } finally {
            graph.getModel().endUpdate();
        }
    }

    public void setWidth(double width) {
        setSize(new Dimension((int) width, getHeight()));
    }

    public void setHeight(double height) {
        setSize(new Dimension(getWidth(), (int) height));
    }

    public void showGraphInfo() {
        JFrame graphInfoFrame = new JFrame();
        graphInfoFrame.setSize(400, 300);
        graphInfoFrame.setTitle("Signal Flow Graph info");
        JTextArea label = new JTextArea(getGraphInfo());
        graphInfoFrame.add(label);
        graphInfoFrame.pack();
        graphInfoFrame.setVisible(true);

    }

    private String getGraphInfo() {
        StringBuilder info = new StringBuilder();
        List<IPath> paths = signalFlowGraph.getForwardPaths(nodes.get(0), nodes.get(nodes.size() - 1));
        info.append("Number of Forward Paths = ").append(paths.size());
        info.append("\nForward Paths :");
        for (IPath path : paths) {
            info.append("\nPath: " + " ").append(path.getPathContent()).append("  ").append("Gain: ").append(" ").append(path.getPathGain());
        }
        System.out.println("\n--------------------------------------------");
        List<IPath> loops = signalFlowGraph.getIndividualLoops();
        info.append("\nNumber of Individual loops = ").append(loops.size());
        info.append("\nIndividual loops :");
        for (IPath path : loops) {
            info.append("\nLoop: " + " ").append(path.getPathContent()).append("  ").append("Gain: ").append(" ").append(path.getPathGain());
        }
        info.append("\n--------------------------------------------");
        info.append("\nAll Non-Touching loops : ");
        List<List<List<IPath>>> lists = signalFlowGraph.getNonTouchingLoops();
        int ind = 1;
        for (List<List<IPath>> list1 : lists) {
            info.append("\n-----------------------");
            info.append("\nsize = ").append(ind);
            ind++;
            for (List<IPath> list2 : list1) {
                for (IPath path : list2) {
                    info.append(path.getPathContent()).append(" | ");
                }
                info.append("\n");
            }
        }
        info.append("\n-------------------------------------------");
        info.append("\nAll Deltas : ");
        List<Float> delta = signalFlowGraph.getDeltas();
        for (Float del : delta) {
            info.append("\n").append(del);
        }
        info.append("\n-------------------------------------------");
        info.append("\nOverAll Transfer Function = ");
        info.append("\n").append(signalFlowGraph.getOverAllTransferFunction(nodes.get(0), nodes.get(nodes.size() - 1)));
        return info.toString();
    }
}
