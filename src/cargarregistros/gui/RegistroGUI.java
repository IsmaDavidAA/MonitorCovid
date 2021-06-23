package cargarregistros.gui;

import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistroGUI extends JDialog{
    private JPanel panel1;
    private JPanel panel4;
    private Registro registro;
    private JTable table1;
    public RegistroGUI(Registro registro) {
        this.setDefaultCloseOperation(2);
        this.registro = registro;
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(410, 300));
        cargarComponentes();
        actualizarTablaDeSintomas();
        this.table1.setEnabled(false);
        this.pack();
        this.setResizable(false);
        this.setModal(true);
        this.setVisible(true);
        this.setTitle("Registro");
    }

    public void cargarComponentes(){
        panel1 = new JPanel();
        panel4 = new JPanel();
        this.setLayout(null);
        table1 = new JTable();
        table1.setBounds(5,5, 400, 400);
        panel1.add(new JLabel("Sintomas presentados"));
        panel4.add(table1);
        panel1.setBounds(20, 5, 140, 30);
        panel4.setBounds(0, 40, 400, 300);
        panel4.setLayout(new BorderLayout());
        this.add(panel1);
        this.add(panel4);
    }

    public void actualizarTablaDeSintomas(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        Sintomas sintomas = registro.getSintomas();
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Object[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
        table1.setModel(table);
    }
}

