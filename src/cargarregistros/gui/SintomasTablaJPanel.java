package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class SintomasTablaJPanel extends JPanel {
    private JTable sintomasTable;
    private JScrollPane scrollPane;
    private DefaultTableModel table ;
    private Sintomas sintomas;
    public SintomasTablaJPanel() {
        table = new DefaultTableModel();
        sintomasTable = new JTable();
        table.addColumn("Sintomas");
        table.addColumn("Categoria");

        sintomasTable = new JTable(table);
        this.sintomasTable.setEnabled(false);
        TableColumnModel columnModel = sintomasTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(250);

        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(table);
        sintomasTable.setRowSorter(sorTable);
        scrollPane = new JScrollPane(sintomasTable);
        this.add(scrollPane);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(0, 0, 420,230);
        actualizar(sintomas);
    }
    public void actualizar(Sintomas sintomas) {
        limpiar();
        this.sintomas = sintomas;
        for (Sintoma sintoma: sintomas){
            table.addRow(new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }

    }
    private void limpiar(){
        int rowCount = table.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            table.removeRow(i);
        }
    }
}