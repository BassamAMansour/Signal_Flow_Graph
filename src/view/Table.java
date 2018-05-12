package view;

import model.IPath;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class Table extends JFrame {
    private static final long serialVersionUID = 1L;

    String headers1[] = {"No.", "Forward Path", "Gain"};
    String headers2[] = {"No.", "Individual Loop", "Gain"};
    String headers3[] = {"No.", "Delta"};

    List<IPath> forwardPaths;
    List<IPath> loops;
    List<Float> deltas;

    JTable forwardPathsTable = new JTable();
    JTable loopsTable = new JTable();
    JTable deltasTable = new JTable();

    DefaultTableModel forwardPathsModel = new DefaultTableModel();
    DefaultTableModel loopsModel = new DefaultTableModel();
    DefaultTableModel deltasModel = new DefaultTableModel();

    JScrollPane forwardPathScroll;
    JScrollPane loopScroll;
    JScrollPane textArea;
    JScrollPane deltaScroll;
    StringBuilder info = new StringBuilder();
    private Box theTablesBox = Box.createHorizontalBox();
    private Box loopsBox = Box.createHorizontalBox();
    private Box deltasBox = Box.createVerticalBox();

    public Table(List<IPath> path, List<IPath> loop, List<List<List<IPath>>> lists, List<Float> delta, Float gain) {
        forwardPaths = path;
        forwardPathsModel.setColumnIdentifiers(headers1);
        forwardPathsTable.setModel(forwardPathsModel);
        forwardPathScroll = new JScrollPane(forwardPathsTable);

        loops = loop;
        loopsModel.setColumnIdentifiers(headers2);
        loopsTable.setModel(loopsModel);
        loopScroll = new JScrollPane(loopsTable);

        int ind = 1;
        info.append("All Non-Touching loops");
        for (List<List<IPath>> list1 : lists) {
            info.append("\n-----------------------");
            info.append("  ");
            info.append("\nsize = ").append(ind);
            ind++;
            for (List<IPath> list2 : list1) {
                info.append("\n");
                for (IPath paths : list2) {
                    info.append("  ");
                    info.append(paths.getPathContent()).append(" | ");
                }
                info.append("\n");
            }
        }
        String loops = info.toString();
        JTextArea label = new JTextArea(10, 30);
        textArea = new JScrollPane(label, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        label.setText(loops);
        label.setFont(new Font("Serif", Font.PLAIN, 20));

        deltas = delta;
        deltasModel.setColumnIdentifiers(headers3);
        deltasTable.setModel(deltasModel);
        deltaScroll = new JScrollPane(deltasTable);

        addRows();

        JLabel label1 = new JLabel("OverAll Transfer Function Gain = " + gain);
        label1.setFont(new Font("Serif", Font.PLAIN, 30));

        theTablesBox.add(forwardPathScroll);
        theTablesBox.add(loopScroll);
        loopsBox.add(textArea);
        theTablesBox.add(loopsBox);
        deltasBox.add(deltaScroll);
        deltasBox.add(label1);
        theTablesBox.add(deltasBox);

        add(theTablesBox, BorderLayout.NORTH);
        this.setTitle("Signal Flow Graph info");
        setSize(1700, 500);
        setVisible(true);
    }

    public void addRows() {
        int i = 1;
        for (IPath path : forwardPaths) {
            forwardPathsModel.addRow(new Object[]{String.valueOf(i), String.valueOf(path.getPathContent()),
                    String.valueOf(path.getPathGain())});
            i++;
        }

        int j = 1;
        for (IPath path : loops) {
            loopsModel.addRow(new Object[]{String.valueOf(j), String.valueOf(path.getPathContent()),
                    String.valueOf(path.getPathGain())});
            j++;
        }

        int k = 0;
        for (Float del : deltas) {
            deltasModel.addRow(new Object[]{String.valueOf(k), String.valueOf(del)});
            k++;
        }

    }

}
