package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class RegistrarSintomasJPanel extends JPanel {
    private final JTable sintomasTable;
    private final JScrollPane scrollPane;
    private final Sintomas sintomas;
    private final DefaultTableModel table;
    private final int columnaDeCheck;
    public RegistrarSintomasJPanel(Sintomas sintomas){
        this.sintomas = sintomas;
        columnaDeCheck = 2;
        table = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return column==columnaDeCheck? true : false;
            }
        };
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        table.addColumn("Agregado");
        sintomasTable =  new JTable(table);
        addCheckBox(columnaDeCheck, sintomasTable);
        sintomasTable.setEditingColumn(columnaDeCheck);
        TableColumnModel columnModel = sintomasTable.getColumnModel();
        columnModel.getColumn(2).setPreferredWidth(20);
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(260);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(table);
        sintomasTable.setRowSorter(sorTable);
        scrollPane = new JScrollPane(sintomasTable);
        this.add(scrollPane);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(0,0, 480, 400);
        limpiar();
        llenarTabla();
    }
    private void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public Sintomas getSintomasSeleccionados(){
        Sintomas sintomasParaGuardar = new Sintomas();
        for (int i = 0; i < sintomasTable.getRowCount(); i++) {
            if  ( estaMarcado(i, columnaDeCheck, sintomasTable)) {
                sintomasParaGuardar.add((Sintoma) sintomasTable.getValueAt(i, 0));
            }
        }
        return sintomasParaGuardar;
    }

    private boolean estaMarcado(int row, int column, JTable table) {
        boolean marcado = false;
        if(table.getValueAt(row, column) != null) {
            marcado = (boolean) table.getValueAt(row, column);
        }
        return marcado;
    }
    private void llenarTabla(){
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Object[]{sintoma, sintoma.getClass().getName().split("\\.")[1]});
        }
    }
    private void limpiar(){
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            table.removeRow(i);
        }
    }
}
