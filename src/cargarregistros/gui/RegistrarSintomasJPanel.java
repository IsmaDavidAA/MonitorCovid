package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class RegistrarSintomasJPanel extends JPanel {
    private final JTable sintomasTable;
    private final JScrollPane scrollPane;
    private final Sintomas sintomas;
    private final DefaultTableModel table;
    private final int columnaDeCheck;

    public RegistrarSintomasJPanel(Sintomas sintomas) {
        this.sintomas = sintomas;
        columnaDeCheck = 2;
        table = defaultTableModelLastColumnEditable();
        sintomasTable = tablaSintSelecOrdenable(table);
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(sintomasTable);
        this.add(scrollPane);
        llenarTabla();
    }

    private DefaultTableModel defaultTableModelLastColumnEditable() {
        DefaultTableModel table = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == columnaDeCheck;
            }
        };
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        table.addColumn("Agregado");
        return table;
    }

    private JTable tablaSintSelecOrdenable(DefaultTableModel defaultTableModel) {
        JTable sintomasT = new JTable(defaultTableModel);
        addCheckBox(columnaDeCheck, sintomasT);
        sintomasT.setEditingColumn(columnaDeCheck);
        TableColumnModel columnModel = sintomasT.getColumnModel();
        columnModel.getColumn(2).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(260);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(defaultTableModel);
        sintomasT.setRowSorter(sorTable);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(25);
        sortKeys.add(new RowSorter.SortKey(1, SortOrder.DESCENDING));
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorTable.setSortKeys(sortKeys);
        return sintomasT;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBounds(10, 120, 500, 420);
        scrollPane.setBounds(0, 0, 480, 400);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
    }

    private void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public Sintomas getSintomasSeleccionados() {
        Sintomas sintomasParaGuardar = new Sintomas();
        boolean existenSintomas = false;
        for (int i = 0; i < sintomasTable.getRowCount(); i++) {
            if (estaMarcado(i, columnaDeCheck, sintomasTable)) {
                sintomasParaGuardar.add((Sintoma) sintomasTable.getValueAt(i, 0));
                existenSintomas = true;
            }
        }
        if (!existenSintomas) {
            sintomasParaGuardar = null;
        }
        return sintomasParaGuardar;
    }

    private boolean estaMarcado(int row, int column, JTable table) {
        boolean marcado = false;
        if (table.getValueAt(row, column) != null) {
            marcado = (boolean) table.getValueAt(row, column);
        }
        return marcado;
    }

    private void llenarTabla() {
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Object[]{sintoma, sintoma.getClass().getName().split("\\.")[1]});
        }
    }
}
