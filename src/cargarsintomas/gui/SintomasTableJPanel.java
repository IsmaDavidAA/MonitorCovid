package cargarsintomas.gui;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SintomasTableJPanel extends JPanel {
    private final JScrollPane scrollPane;
    private final DefaultTableModel table;
    private final Sintomas sintomas;
    public SintomasTableJPanel(Sintomas sintomas) {
        this.sintomas = sintomas;
        table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(tablaSintomasOrdenable(table));
        llenarTabla();
        this.add(scrollPane);
    }
    private JTable tablaSintomasOrdenable(DefaultTableModel defaultTableModel){
        JTable sintomasTable = new JTable(defaultTableModel);
        sintomasTable.setEnabled(false);
        TableColumnModel columnModel = sintomasTable.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(3);
        columnModel.getColumn(1).setPreferredWidth(1);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(defaultTableModel);
        sintomasTable.setRowSorter(sorTable);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorTable.setSortKeys(sortKeys);
        return sintomasTable;
    }
    public void addRow(String[] row, Sintoma sintoma){
        sintomas.add(sintoma);
        table.insertRow(0,row);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBounds(5, 130, 580, 440);
        scrollPane.setBounds(0, 0, 480,380);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }
    private void llenarTabla(){
        for (Sintoma sintoma : sintomas) {
            table.insertRow(0, new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
    }
}
