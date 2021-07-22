package cargarsintomas.gui;

import cargarsintomas.util.GestorArchivoSintomas;
import cargarsintomas.util.GestorPaquete;
import cargarsintomas.util.Validador;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SintomasJPanel extends JPanel implements ActionListener{
    private final JPanel mainPanel;
    private final SintomasTableJPanel sintomasTableJPanel;
    private final JButton finalizarButton;
    private final JComboBox<String> tiposSintomaComboBox;
    private final JTextField nombreSintomaTextField;
    private final JButton agregarSintomaButton;
    private final JLabel subtituloAgregarSintomaJLabel;
    private final GestorArchivoSintomas gestorArchivoSintomas;
    private final RegistrarSintomaGUI registrarSintomasFrame;
    private final MensajeJPanel mensajeJPanel;
    public SintomasJPanel(RegistrarSintomaGUI registrarSintomasFrame){
        this.registrarSintomasFrame = registrarSintomasFrame;
        gestorArchivoSintomas = new GestorArchivoSintomas();
        mainPanel = new JPanel();
        sintomasTableJPanel = new SintomasTableJPanel(gestorArchivoSintomas.getSintomasArchivo());
        finalizarButton = new JButton("Finalizar");
        nombreSintomaTextField = new JTextField();
        tiposSintomaComboBox = new JComboBox<>();
        agregarSintomaButton = new JButton("Agregar");
        finalizarButton.addActionListener(this);
        agregarSintomaButton.addActionListener(this);
        mensajeJPanel = new MensajeJPanel();
        subtituloAgregarSintomaJLabel = new JLabel("Nombre Sintoma:");
        this.add(mensajeJPanel);
        llenarComboBox();
        agregarComponentes();
    }

    private void agregarComponentes(){
        mainPanel.add(subtituloAgregarSintomaJLabel);
        mainPanel.add(tiposSintomaComboBox);
        mainPanel.add(nombreSintomaTextField);
        mainPanel.add(agregarSintomaButton);
        mainPanel.add(finalizarButton);
        mainPanel.add(sintomasTableJPanel);
        mainPanel.add(mensajeJPanel);
        add(mainPanel);
    }
    private void llenarComboBox(){
        GestorPaquete gestorPaquete = new GestorPaquete();
        for(String s: gestorPaquete.getTiposDeSintomas()){
            tiposSintomaComboBox.addItem(s);
        }
    }
    private void finalizar(){
        try {
            synchronized(registrarSintomasFrame){
                registrarSintomasFrame.notify();
            }
            registrarSintomasFrame.dispose();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        mainPanel.setBounds(5,5,600,650);
        subtituloAgregarSintomaJLabel.setBounds(5, 45, 100, 25);
        tiposSintomaComboBox.setBounds(5, 5, 110, 25);
        nombreSintomaTextField.setBounds(105, 45, 190, 25);
        agregarSintomaButton.setBounds(297, 45, 120, 25);
        finalizarButton.setBounds(370, 530, 116, 30);
        mensajeJPanel.setBounds(10, 80, 450, 40);
    }

    private void agregarSintoma() {
        Validador validador = new Validador();
        boolean valido = validador.esValido(nombreSintomaTextField.getText(), gestorArchivoSintomas.getSintomasArchivo());
        String tipoSintoma;
        if (valido) {
            tipoSintoma = (String) tiposSintomaComboBox.getSelectedItem();
            String nombreSintomaValidado = validador.getTextoValidado(nombreSintomaTextField.getText());
            boolean agregado = gestorArchivoSintomas.guardarSintoma(nombreSintomaValidado, tipoSintoma);
            if (agregado) {
                mensajeJPanel.actualizarMensaje("Agregado con exito!!",1);
                sintomasTableJPanel.addRow(new String[]{nombreSintomaValidado, tipoSintoma }, gestorArchivoSintomas.crearSintoma(nombreSintomaValidado, tipoSintoma));
            } else {
                mensajeJPanel.actualizarMensaje("Lo sentimos, no se pudo agregar a la lista de sintomas", 2);
            }
        } else {
            mensajeJPanel.actualizarMensaje("Lo sentimos, no se pudo agregar por que el nombre no es valido", 2);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();
        if (botonPulsado == agregarSintomaButton) {
            agregarSintoma();
        } else if (botonPulsado == finalizarButton){
            finalizar();
        }
    }
}
