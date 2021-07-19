package cargarregistros.gui;

import cargarregistros.GestorArchivoRegistros;
import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
public class RegistrosJPanel extends JPanel {
    private JPanel addRegistroPanel;
    private RegistrarSintomasJPanel tablePanel;
    private JPanel finalizarPanel;
    private JLabel fechaText;
    private JButton addRegistroButton;
    private JButton finalizarButton;
    private final JPanel mensajeConfirmacionJPanel;
    private final JLabel mensajeDeConfirmacion;
    private GestorArchivoRegistros gestorArchivoRegistros;
    private Sintomas sintomas;
    private Date fechaHoy;
    private HashMap<Date, Registro> registros;
    private SimpleDateFormat formatter;
    private RegistrosTablaJPanel registrosTablaJPanel;
    private RegistrarRegistroGUI registrarRegistroGUI;
    public RegistrosJPanel(RegistrarRegistroGUI registrarRegistroGUI, Sintomas sintomas){
        this.sintomas = sintomas;
        this.registrarRegistroGUI = registrarRegistroGUI;
        this.registros = new HashMap<>();
        fechaHoy = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
        gestorArchivoRegistros = new GestorArchivoRegistros();
        addRegistroPanel = new JPanel();
        tablePanel = new RegistrarSintomasJPanel(sintomas);
        finalizarPanel = new JPanel();
        finalizarButton = new JButton("Finalizar");
        addRegistroButton = new JButton("Agregar registro");
        fechaText = new JLabel("Fecha:"+ new SimpleDateFormat("dd-MM-yyyy").format(fechaHoy.getTime()) +"...");
        registrosTablaJPanel = new RegistrosTablaJPanel(gestorArchivoRegistros.getRegistrosArchivo());
        mensajeConfirmacionJPanel = new JPanel();
        mensajeDeConfirmacion = new JLabel("");
        cargarComponentes();
        eventoOyenteAgregarSintoma();
        finalizar(registrarRegistroGUI);
        mostrarNuevoMensaje("", Color.CYAN);
    }
    private void cargarComponentes(){
        finalizarPanel.add(finalizarButton);
        addRegistroPanel.add(fechaText);
        addRegistroPanel.add(addRegistroButton);
        mensajeConfirmacionJPanel.add(mensajeDeConfirmacion);
        this.add(addRegistroPanel);
        this.add(tablePanel);
        this.add(finalizarPanel);
        this.add(registrosTablaJPanel);
        this.add(mensajeConfirmacionJPanel);

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        fechaText.setBounds(0, 0, 100,30);
        addRegistroButton.setBounds(0, 40, 150,25);
        addRegistroPanel.setBounds(10, 10, 200, 70);
        finalizarButton.setBounds(0, 0, 160, 25);
        finalizarPanel.setBounds(700,550, 170, 40);
        tablePanel.setBounds(10, 120, 500, 420);
        registrosTablaJPanel.setBounds(520, 80, 550,420);
        mensajeConfirmacionJPanel.setBounds(10, 80, 450, 30);
    }
    private void eventoOyenteAgregarSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                    fechaHoy = new Date();
                    boolean agregado = gestorArchivoRegistros.guardarRegistro(tablePanel.getSintomasSeleccionados(),
                            (Date) fechaHoy.clone());
                    Registro registro = new Registro((Date) fechaHoy.clone(),tablePanel.getSintomasSeleccionados());
                    if (agregado) {
                        mostrarNuevoMensaje("Se agrego el registro con la fecha: " +
                                formatter.format(fechaHoy.getTime()), Color.GREEN);
                        registrosTablaJPanel.addRow(registro);
                    } else {
                        mostrarNuevoMensaje("Lo sentimos, no se pudo agregar", Color.RED);
                    }
            }
        };
        addRegistroButton.addActionListener(oyenteAccion);
    }

    private void mostrarNuevoMensaje(String mensaje, Color color){
        mensajeConfirmacionJPanel.setBackground(color);
        mensajeDeConfirmacion.setText(mensaje);
    }

    private void finalizar(JFrame frame){
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                try {
                    synchronized(frame){
                        frame.notify();
                    }
                    frame.dispose();
                } catch (Exception e){
                }
            }
        };
        finalizarButton.addActionListener(oyenteAccion);
    }
}
