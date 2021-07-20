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
    private final JScrollPane fechasJScrollPanel;
    private final SintomasTablaJPanel sintomasTablaJPanel;
    private final Map<Date, Registro> registrosMap;
    private final DefaultTableModel tableFechas;
    private final Registros registros;
    private final JPanel sintomasRegistradosJPanel;
    private final JLabel registrosText;
    private final JTable registrosTable;
    public RegistrosTablaJPanel(Registros registros) {
        this.registros = registros;
        sintomasRegistradosJPanel = new JPanel();
        registrosText = new JLabel("Registros");
        registrosMap = new HashMap<>();
        for (Registro registro : registros) {
            registrosMap.put(registro.getFecha(), registro);
        }
        tableFechas = new DefaultTableModel();
        tableFechas.addColumn("Fecha registros");
        registrosTable = registrosTableOrdenado(tableFechas);
        fechasJScrollPanel = new JScrollPane();
        fechasJScrollPanel.setViewportView(registrosTable);
        sintomasTablaJPanel = new SintomasTablaJPanel();
        agregarComponentes();
        llenarTabla();
    }

    private void agregarComponentes(){
        sintomasRegistradosJPanel.add(fechasJScrollPanel);
        sintomasRegistradosJPanel.add(sintomasTablaJPanel);
        this.add(sintomasRegistradosJPanel);
        this.add(registrosText);
    }

    private JTable registrosTableOrdenado(DefaultTableModel defaultTableModel){
        JTable registrosT = new JTable(defaultTableModel);
        registrosT.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = registrosT.rowAtPoint(e.getPoint());
                int col = registrosT.columnAtPoint(e.getPoint());
                Date date = (Date) registrosT.getValueAt(row, col);
                sintomasTablaJPanel.actualizar(registrosMap.get(date).getSintomas());
            }
        });
        TableRowSorter<DefaultTableModel> sorTable = new TableRowSorter<>(defaultTableModel);
        registrosT.setRowSorter(sorTable);
        return registrosT;
    }

    public void addRow(Registro registro) {
        registrosMap.put(registro.getFecha(), registro);
        registros.push(registro);
        tableFechas.insertRow(0, new Date[]{registro.getFecha()});
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        registrosText.setBounds(185, 5, 200, 30);
        sintomasRegistradosJPanel.setBounds(0, 40, 500, 420);
        fechasJScrollPanel.setBounds(5, 5, 420, 150);
        sintomasTablaJPanel.setBounds(5, 155, 420, 230);
        fechasJScrollPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        actualizarAlUltimo();
    }

    private void llenarTabla() {
        for (Registro r : registros) {
            tableFechas.insertRow(0, new Date[]{r.getFecha()});
        }
    }

    private void actualizarAlUltimo() {
        if (!registros.isEmpty()) {
            Registro registro = registros.peek();
            sintomasTablaJPanel.actualizar(registro.getSintomas());
            registrosTable.setRowSelectionInterval(0, 0);
        } else {
            sintomasTablaJPanel.actualizar(new Sintomas());
        }
    }
}