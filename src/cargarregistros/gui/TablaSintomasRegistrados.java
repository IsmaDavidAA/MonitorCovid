package cargarregistros.gui;

import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TablaSintomasRegistrados extends JPanel {
    private JTable sintomasTable;
    private JScrollPane scrollPane;
    public TablaSintomasRegistrados(){
        setLayout(null);
        sintomasTable = new JTable();
        sintomasTable.setBounds(5,15, 400, 400);
    }
    public void actualizarTablaDeSintomas(Sintomas sintomas){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        for (Sintoma sintoma : sintomas) {
            table.addRow(new String[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
        sintomasTable= new JTable(table);
        this.sintomasTable.setEnabled(false);
        scrollPane = new JScrollPane(sintomasTable);
//        scrollPane.setBounds(5,15, 400, 400);
        this.add(scrollPane);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        scrollPane.setBounds(5, 15, 400,400);
    }
}
