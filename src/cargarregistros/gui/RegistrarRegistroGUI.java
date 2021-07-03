package cargarregistros.gui;

import cargarregistros.GestorArchivoRegistros;
import monitor.Registro;
import monitor.Sintomas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.*;

public class RegistrarRegistroGUI extends JFrame {
    private JPanel addRegistroPanel;
    private JPanel showRegistrosPanel;
    private TablaRegistroSintomas tablePanel;
    private JPanel finalizarPanel;
    private JComboBox registrosComboBox;
    private JButton addRegistroButton;
    private JButton finalizarButton;
    private JPanel fechaRegistroPanel;
    private TablaSintomasRegistrados tablaSintomasPanel;
    private JLabel fechaLabel;
    private GestorArchivoRegistros gestorArchivoRegistros;
    private Sintomas sintomas;
    private Date fechaHoy;
    private HashMap<Date, Registro> registros;
    private DefaultComboBoxModel combo;
    private SimpleDateFormat formatter;
    public RegistrarRegistroGUI(Sintomas sintomas) {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(1000, 620));
        this.setLayout(null);
        this.sintomas = sintomas;
        crearComponentes();
        cargarComponentes();
        cargarCheckBox();
        tablePanel.cargarTablaDeSintomas(sintomas);
        eventoOyenteAgregarSintoma();
        eventoOyenteShowRegistro();
        this.pack();
        this.setResizable(false);
        this.setTitle("Registrar registros");
        this.setVisible(true);
        finalizar(this);
        detenerHilo(this);
    }

    private void detenerHilo(JFrame frame){
        synchronized(frame){
            try{
                frame.wait();
            }
            catch(InterruptedException ex){
            }
        }
    }

    private void crearComponentes(){
        this.registros = new HashMap<>();
        fechaHoy = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
        gestorArchivoRegistros = new GestorArchivoRegistros();
        registrosComboBox = new JComboBox();
        addRegistroPanel = new JPanel();
        showRegistrosPanel = new JPanel();
        tablePanel = new TablaRegistroSintomas();
        finalizarPanel = new JPanel();
        finalizarButton = new JButton("Finalizar");
        addRegistroButton = new JButton("Agregar registro");
        fechaRegistroPanel = new JPanel();
        tablaSintomasPanel = new TablaSintomasRegistrados();
        fechaLabel = new JLabel("");
    }
    private void cargarComponentes(){
        fechaRegistroPanel.add(new JLabel("Registro"));
        fechaRegistroPanel.add(fechaLabel);
        finalizarPanel.add(finalizarButton);
        addRegistroPanel.add(new JLabel("Fecha:"));
        addRegistroPanel.add(new JLabel(new SimpleDateFormat("dd-MM-yyyy").format(fechaHoy.getTime()) +"..."));
        addRegistroPanel.add(addRegistroButton);
        showRegistrosPanel.add(new JLabel("Registros"));
        showRegistrosPanel.add(registrosComboBox);
        tablaSintomasPanel.setBounds(510, 110, 420, 420);
        tablaSintomasPanel.setLayout(new BorderLayout());
        fechaRegistroPanel.setBounds(600, 80, 240, 60);
        finalizarPanel.setBounds(700,550, 200, 40);
        addRegistroPanel.setBounds(10, 10, 200, 50);
        showRegistrosPanel.setBounds(600, 10, 200, 50);
        tablePanel.setBounds(20, 110, 420, 420);
        tablePanel.setLayout(new BorderLayout());

        this.add(addRegistroPanel);
        this.add(showRegistrosPanel);
        this.add(tablePanel);
        this.add(finalizarPanel);
        this.add(tablaSintomasPanel);
        this.add(fechaRegistroPanel);

    }
    private void eventoOyenteShowRegistro() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                fechaLabel.setText((registros.get(registrosComboBox.getSelectedItem()).getFecha()).toString());
                tablaSintomasPanel.actualizarTablaDeSintomas(registros.get(registrosComboBox.getSelectedItem()).getSintomas());
            }
        };
        registrosComboBox.addActionListener(oyenteAccion);
    }

    private void finalizar(JFrame frame){
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                closeWindow(frame);
            }
        };
        finalizarButton.addActionListener(oyenteAccion);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                closeWindow(frame);
            }
        });
    }

    private void closeWindow(JFrame frame){
        try {
            synchronized(frame){
                frame.notify();
            }
            frame.dispose();
        } catch (Exception e){
        }
    }
    private void cargarCheckBox(){
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

    private void eventoOyenteAgregarSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int confirmado = JOptionPane.showConfirmDialog(RegistrarRegistroGUI.this,
                        "Seguro que desea terminar el registro?",
                        "",JOptionPane.YES_NO_OPTION);
                if(confirmado==0) {
                    fechaHoy = new Date();
                    boolean agregado = gestorArchivoRegistros.guardarRegistro(tablePanel.getSintomasSeleccionados(),
                            (Date) fechaHoy.clone());
                    if (agregado) {
                            cargarCheckBox();
                            JOptionPane.showMessageDialog(RegistrarRegistroGUI.this,"Se agrego el registro con la fecha: " +
                                    formatter.format(fechaHoy.getTime()));
                        } else {
                            JOptionPane.showMessageDialog(RegistrarRegistroGUI.this,"Lo sentimos, no se pudo agregar");
                        }
                }
            }
        };
        addRegistroButton.addActionListener(oyenteAccion);
    }
}
