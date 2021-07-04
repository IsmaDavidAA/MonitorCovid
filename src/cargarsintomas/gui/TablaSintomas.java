package cargarsintomas.gui;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;

public class TablaSintomas extends JPanel {
    private JTable sintomasTable;
    private JScrollPane scrollPane;
    private DefaultTableModel table ;

    public TablaSintomas(Sintomas sintomas) {
        sintomasTable = new JTable();
        table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        for (Sintoma sintoma : sintomas) {
            table.addRow(new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
        sintomasTable = new JTable(table);
        this.sintomasTable.setEnabled(false);
        TableColumnModel columnModel = sintomasTable.getColumnModel();
        columnModel.getColumn(1).setPreferredWidth(50);
        columnModel.getColumn(0).setPreferredWidth(250);
        scrollPane = new JScrollPane(sintomasTable);
        this.add(scrollPane);
    }
    public void addRow(String[] row){
        table.addRow(row);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(20, 20, 490,390);
    }
}
