package cargarregistros.gui;

import cargarregistros.util.GestorArchivoRegistros;
import monitor.Registro;
import monitor.Sintomas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegistrosJPanel extends JPanel implements ActionListener {
    private final JPanel agregarRegistroPanel;
    private final RegistrarSintomasJPanel registroSintomaJPanel;
    private final JLabel fechaJLabel;
    private final JButton agregarRegistroButton;
    private final JButton finalizarButton;
    private final MensajeJPanel mensajeJPanel;
    private final GestorArchivoRegistros gestorArchivoRegistros;
    private final SimpleDateFormat formatter;
    private final RegistrosTablaJPanel registrosTablaJPanel;
    private final RegistrarRegistroGUI registrarRegistroGUI;
    private Date fechaHoy;

    public RegistrosJPanel(RegistrarRegistroGUI registrarRegistroGUI, Sintomas sintomas) {
        this.registrarRegistroGUI = registrarRegistroGUI;
        fechaHoy = new Date();
        formatter = new SimpleDateFormat("dd-MM-yyyy-hh:mm");
        gestorArchivoRegistros = new GestorArchivoRegistros();
        agregarRegistroPanel = new JPanel();
        registroSintomaJPanel = new RegistrarSintomasJPanel(sintomas);
        finalizarButton = new JButton("Finalizar");
        agregarRegistroButton = new JButton("Agregar registro");
        fechaJLabel = new JLabel("Fecha:" + new SimpleDateFormat("dd-MM-yyyy").format(fechaHoy.getTime()) + "...");
        registrosTablaJPanel = new RegistrosTablaJPanel(gestorArchivoRegistros.getRegistrosArchivo());
        mensajeJPanel = new MensajeJPanel();
        agregarRegistroButton.addActionListener(this);
        finalizarButton.addActionListener(this);
        agregarComponentes();
    }

    private void agregarComponentes() {
        agregarRegistroPanel.add(fechaJLabel);
        agregarRegistroPanel.add(agregarRegistroButton);
        this.add(agregarRegistroPanel);
        this.add(registroSintomaJPanel);
        this.add(finalizarButton);
        this.add(registrosTablaJPanel);
        this.add(mensajeJPanel);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        fechaJLabel.setBounds(0, 0, 100, 30);
        agregarRegistroButton.setBounds(0, 40, 150, 25);
        agregarRegistroPanel.setBounds(10, 10, 200, 70);
        finalizarButton.setBounds(700, 550, 160, 25);
        registrosTablaJPanel.setBounds(520, 80, 550, 420);
        mensajeJPanel.setBounds(10, 80, 450, 30);
    }

    private void agregarRegistro() {
        fechaHoy = new Date();
        Registro registro = new Registro((Date) fechaHoy.clone(), registroSintomaJPanel.getSintomasSeleccionados());
        boolean agregado = gestorArchivoRegistros.guardarRegistro(registro);
        if (agregado) {
            mensajeJPanel.actualizarMensaje("Se agrego el registro con la fecha: " + formatter.format(fechaHoy.getTime()), 1);
            registrosTablaJPanel.addRow(registro);
        } else {
            mensajeJPanel.actualizarMensaje("Lo sentimos, no se pudo agregar", 2);
        }
    }

    private void finalizar() {
        try {
            synchronized (registrarRegistroGUI) {
                registrarRegistroGUI.notify();
            }
            registrarRegistroGUI.dispose();
        } catch (Exception e) {
            //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();
        if (botonPulsado == agregarRegistroButton) {
            agregarRegistro();
        } else if (botonPulsado == finalizarButton) {
            finalizar();
        }
    }
}
