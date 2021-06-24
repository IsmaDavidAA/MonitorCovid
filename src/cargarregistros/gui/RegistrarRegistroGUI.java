package cargarregistros.gui;

import cargarregistros.GestorArchivoRegistros;
import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class RegistrarRegistroGUI extends JFrame {
    private JPanel addRegistroPanel;
    private JPanel showRegistrosPanel;
    private JPanel tablePanel;
    private JComboBox registrosComboBox;
    private JButton addRegistroButton;
    private JTable sintomasTable;
    private GestorArchivoRegistros gestorArchivoRegistros;
    private Sintomas sintomas;
    private Date fechaHoy;
    private HashMap<Date, Registro> registros;
    private DefaultComboBoxModel combo;
    private SimpleDateFormat formatter;
    public RegistrarRegistroGUI(Sintomas sintomas) {
        this.setLocation(200, 50);
        formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
        this.setPreferredSize(new Dimension(410, 600));
        this.sintomas = sintomas;
        this.registros = new HashMap<>();
        fechaHoy = new Date();
        gestorArchivoRegistros = new GestorArchivoRegistros();
        registrosComboBox = new JComboBox();
        cargarComponentes();
        this.pack();
        this.setResizable(false);
//        this.setModal(true);
//        this.setVisible(true);
        this.setPreferredSize(new Dimension(420, 700));
        this.setTitle("Registrar registros");

        final RegistrarRegistroGUI frame = this;

        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                try {
                    synchronized(frame){
                        frame.notify();
                    }
                    frame.setVisible(false);
                    frame.dispose();
                } catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Error al guardar");
                }
            }
        });
        setVisible(true);
        synchronized(frame){
            try{
                frame.wait();
            }
            catch(InterruptedException ex){
            }
        }
    }


    public void cargarComponentes(){
        addRegistroPanel = new JPanel();
        showRegistrosPanel = new JPanel();
        tablePanel = new JPanel();
        this.setLayout(null);
        sintomasTable = new JTable();
        addRegistroButton = new JButton("Agregar registro");
        sintomasTable.setBounds(5,5, 400, 400);
        tablePanel.add(sintomasTable);
        cargarCheckBox();
        addRegistroPanel.add(new JLabel("Fecha:"));
        addRegistroPanel.add(new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(fechaHoy.getTime()) +"..."));
        addRegistroPanel.add(addRegistroButton);
        showRegistrosPanel.add(new JLabel("Registros"));
        showRegistrosPanel.add(registrosComboBox);
        addRegistroPanel.setBounds(10, 10, 200, 50);
        showRegistrosPanel.setBounds(200, 10, 200, 50);
        tablePanel.setBounds(0, 130, 420, 420);
        tablePanel.setLayout(new BorderLayout());
        cargarTablaDeSintomas();
        addCheckBox(1, sintomasTable);
        this.add(addRegistroPanel);
        this.add(showRegistrosPanel);
        this.add(tablePanel);
        eventoOyenteAgregarSintoma();
        eventoOyenteShowRegistro();
    }
    public void eventoOyenteShowRegistro() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                RegistroGUI registro = new RegistroGUI(registros.get(registrosComboBox.getSelectedItem()));
            }
        };
        registrosComboBox.addActionListener(oyenteAccion);
    }

    public void cargarCheckBox(){
        this.registros = (HashMap<Date, Registro>) gestorArchivoRegistros.getMapRegistros();
        Date[] fechas = new Date[registros.size()];
        int i=0;
        for(Map.Entry<Date, Registro> entry : registros.entrySet()) {
            fechas[i] = entry.getKey();
            i++;
        }
        combo = new DefaultComboBoxModel(fechas);
        registrosComboBox.setModel(combo);
    }
    public void addCheckBox(int column, JTable table) {
        TableColumn tc = table.getColumnModel().getColumn(column);
        tc.setCellEditor(table.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(table.getDefaultRenderer(Boolean.class));
    }
    public void cargarTablaDeSintomas(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Agregado");
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Object[]{sintoma});
        }
        sintomasTable.setModel(table);
    }

    public void eventoOyenteAgregarSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int confirmado = JOptionPane.showConfirmDialog(RegistrarRegistroGUI.this,
                        "Seguro que desea terminar el registro?",
                        "",JOptionPane.YES_NO_OPTION);

                if(confirmado==0) {
                    fechaHoy = new Date();
                    boolean agregado = guardarRegistro();
                        if (agregado) {
                            cargarCheckBox();
                            mensajeDeAgregado("Se agrego el registro con la fecha: " +
                                    formatter.format(fechaHoy.getTime()));
                        } else {
                            mensajeDeAgregado("Lo sentimos, no se pudo agregar");
                        }
                }
            }
        };
        addRegistroButton.addActionListener(oyenteAccion);
    }
    private void mensajeDeAgregado(String msjagregado){
        JOptionPane.showMessageDialog(RegistrarRegistroGUI.this, msjagregado);
    }

    private boolean guardarRegistro(){
        boolean guardado;
        Sintomas sintomasParaGuardar = new Sintomas();
        for (int i = 0; i < sintomasTable.getRowCount(); i++) {
            if  ( estaMarcado(i, 1, sintomasTable)) {
                sintomasParaGuardar.add((Sintoma) sintomasTable.getValueAt(i, 0));
            }
        }
        guardado = gestorArchivoRegistros.guardarRegistro(sintomasParaGuardar, (Date) fechaHoy.clone());
        return guardado;
    }
    private boolean estaMarcado(int row, int column, JTable table) {
        boolean marcado = false;
        if(table.getValueAt(row, column) != null) {
            marcado = (boolean) table.getValueAt(row, column);
        }
        return marcado;
    }
}
