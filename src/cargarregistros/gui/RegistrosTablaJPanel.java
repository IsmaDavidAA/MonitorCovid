package cargarregistros.gui;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistrosTablaJPanel extends JPanel {
    private JScrollPane tableScollPanel;
    private SintomasTablaJPanel sintomasTablaJPanel;
    private Map<Date, Registro> registrosMap;
    private JTable registrosTable;
    private DefaultTableModel dataTable;
    private Registros registros;
    private JPanel sintomasRegistradosJPanel;
    private JLabel registrosText;
    public RegistrosTablaJPanel(Registros registros){
        sintomasRegistradosJPanel = new JPanel();
        this.registros = registros;
        registrosText = new JLabel("Registros");
        registrosMap = new HashMap<>();
        for(Registro registro:registros){
            registrosMap.put(registro.getFecha(), registro);
        }
        registrosTable = new JTable();

        dataTable = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return column==1? true : false;
            }
        };
        dataTable.addColumn("Fecha");

        registrosTable = new JTable(dataTable);
        registrosTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e){
                int row=registrosTable.rowAtPoint(e.getPoint());
                int col= registrosTable.columnAtPoint(e.getPoint());
                sintomasTablaJPanel.actualizar(registrosMap.get(registrosTable.getValueAt(row,col)).getSintomas());
            }
        });
        sintomasTablaJPanel = new SintomasTablaJPanel();
        registrosTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tableScollPanel = new JScrollPane(registrosTable);
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(dataTable);
        registrosTable.setRowSorter(sorTable);
        sintomasRegistradosJPanel.add(tableScollPanel);
        sintomasRegistradosJPanel.add(sintomasTablaJPanel);
        this.add(sintomasRegistradosJPanel);
        this.add(registrosText);
    }

    public void addRow(Registro registro){
        registrosMap.put(registro.getFecha(), registro);
        registros.push(registro);
        dataTable.insertRow(0, new Date[]{registro.getFecha()});
        registrosTable.getSelectionBackground();
        repaint();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        registrosText.setBounds(185, 5, 200, 30);
        sintomasRegistradosJPanel.setBounds(0, 40, 500, 420);
        tableScollPanel.setBounds(5, 5, 420,150);
        sintomasTablaJPanel.setBounds(5,155,420,230);
        limpiar();
        llenarTabla();
        vista();
    }

    private void llenarTabla(){
        for(Registro r: registros){
            dataTable.insertRow(0,new Date[]{r.getFecha()});
        }
        if(!registros.isEmpty()) {
            registrosTable.setRowSelectionInterval(0, 0);
        }
    }
    private void limpiar(){
        int rowCount = dataTable.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dataTable.removeRow(i);
        }
    }

    private void vista(){
        if(!registros.isEmpty()){
            Registro registro = registros.peek();
            sintomasTablaJPanel.actualizar(registro.getSintomas());
        }else{
            sintomasTablaJPanel.actualizar(new Sintomas());
        }
    }
}