package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class SintomasTablaJPanel extends JPanel {
    private final JTable sintomasJTable;
    private final JScrollPane scrollPane;
    private final DefaultTableModel table;

    public SintomasTablaJPanel() {
        table = new DefaultTableModel();
        table.addColumn("Historial de sintomas");
        table.addColumn("Categoria");

        sintomasJTable = new JTable(table);
        this.sintomasJTable.setEnabled(false);
        TableColumnModel columnModel = sintomasJTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(250);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(table);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sorTable.setSortKeys(sortKeys);
        sintomasJTable.setRowSorter(sorTable);
        scrollPane = new JScrollPane(sintomasJTable);
        this.add(scrollPane);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        scrollPane.setBounds(0, 0, 420, 230);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    public void actualizar(Sintomas sintomas) {
        limpiar();
        for (Sintoma sintoma : sintomas) {
            table.addRow(new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
    }

    private void limpiar() {
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            table.removeRow(i);
        }
    }
}