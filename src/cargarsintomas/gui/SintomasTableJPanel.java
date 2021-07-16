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
    private final JTable sintomasTable;
    private final JScrollPane scrollPane;
    private final DefaultTableModel table ;
    private final Sintomas sintomas;
    public SintomasTableJPanel(Sintomas sintomas) {
        table = new DefaultTableModel();
        this.sintomas = sintomas;
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        sintomasTable = new JTable(table);
        this.sintomasTable.setEnabled(false);
        TableColumnModel columnModel = sintomasTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(250);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(table);
        sintomasTable.setRowSorter(sorTable);
        scrollPane = new JScrollPane(sintomasTable);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorTable.setSortKeys(sortKeys);
        this.add(scrollPane);
    }
    public void addRow(String[] row, Sintoma sintoma){
        sintomas.add(sintoma);
        table.insertRow(0,row);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(0, 0, 480,380);
        limpiar();
        llenarTabla();
    }
    private void llenarTabla(){
        for (Sintoma sintoma : sintomas) {
            table.insertRow(0, new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
    }
    private void limpiar(){
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            table.removeRow(i);
        }
    }
}
