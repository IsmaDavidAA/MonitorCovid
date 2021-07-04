package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

public class TablaRegistroSintomas extends JPanel {
    private JTable sintomasTable;
    private JScrollPane scrollPane;
    public TablaRegistroSintomas(){
        sintomasTable = new JTable();
        sintomasTable.setBounds(5,15, 400, 400);
    }

    public void cargarTablaDeSintomas(Sintomas sintomas){
        DefaultTableModel table = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return column==1? true : false;
            }
        };
        table.addColumn("Sintoma");
        table.addColumn("Agregado");
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Sintoma[]{sintoma});
            table.isCellEditable(0, 1);
        }
        sintomasTable =  new JTable(table);
        addCheckBox(1, sintomasTable);
        sintomasTable.setEditingColumn(1);
        scrollPane = new JScrollPane(sintomasTable);
//        scrollPane.setBounds(5,15, 400, 400);
        this.add(scrollPane);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(5,15, 400, 400);
    }
    private void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }

    public Sintomas getSintomasSeleccionados(){
        Sintomas sintomasParaGuardar = new Sintomas();
        for (int i = 0; i < sintomasTable.getRowCount(); i++) {
            if  ( estaMarcado(i, 1, sintomasTable)) {
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
}
