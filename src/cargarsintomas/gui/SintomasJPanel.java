package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;
import cargarsintomas.GestorPaquete;
import monitor.Sintoma;
import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SintomasJPanel extends JPanel implements ActionListener{
    private final JPanel mainPanel;
    private final SintomasTableJPanel tablePanel;
    private final JButton finalizarButton;
    private final JComboBox<String> tiposSintomasComboBox;
    private final JTextField nombreSintomaTextField;
    private final JButton addSintomaButton;
    private final JLabel mensajeDeConfirmacion;
    private final JPanel mensajeConfirmacionJPanel;
    private final JLabel agregarText;
    private GestorArchivoSintomas gestorArchivoSintomas;
    private GestorPaquete gestorPaquete;
    private Validador validador;
    private String tipoSintoma;
    private RegistrarSintomaGUI registrarSintomasFrame;
    public SintomasJPanel(RegistrarSintomaGUI registrarSintomasFrame){
        this.registrarSintomasFrame = registrarSintomasFrame;
        crearComponentes();
        mainPanel = new JPanel();
        tablePanel = new SintomasTableJPanel(gestorArchivoSintomas.getSintomasArchivo());
        finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(this);
        nombreSintomaTextField = new JTextField();
        tiposSintomasComboBox = new JComboBox<>();
        addSintomaButton = new JButton("Agregar");
        addSintomaButton.addActionListener(this);
        mensajeDeConfirmacion = new JLabel("");
        mensajeConfirmacionJPanel = new JPanel();
        agregarText = new JLabel("Nombre Sintoma:");
        mensajeConfirmacionJPanel.add(mensajeDeConfirmacion);
        for(String s: gestorPaquete.getTiposDeSintomas()){
            tiposSintomasComboBox.addItem(s);
        }
        mainPanel.add(agregarText);
        mainPanel.add(tiposSintomasComboBox);
        mainPanel.add(nombreSintomaTextField);
        mainPanel.add(addSintomaButton);
        mainPanel.add(finalizarButton);
        mainPanel.add(tablePanel);
        mainPanel.add(mensajeConfirmacionJPanel);
        add(mainPanel);
        tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
    }

    private void crearComponentes(){
        gestorArchivoSintomas = new GestorArchivoSintomas();
        gestorPaquete = new GestorPaquete();
        validador = new Validador();
    }
    private void finalizar(JFrame frame){
        try {
            synchronized(frame){
                frame.notify();
            }
                frame.dispose();
            } catch (Exception e){
            }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        mainPanel.setBounds(5,5,600,650);
        agregarText.setBounds(5, 45, 100, 25);
        tiposSintomasComboBox.setBounds(5, 5, 110, 25);
        nombreSintomaTextField.setBounds(105, 45, 190, 25);
        addSintomaButton.setBounds(297, 45, 120, 25);
        finalizarButton.setBounds(300, 560, 100, 30);
        tablePanel.setBounds(5, 130, 580, 440);
        mensajeConfirmacionJPanel.setBounds(10, 80, 450, 30);
    }

    private void agregarSintoma() {
        boolean valido = validador.validar(nombreSintomaTextField.getText(), gestorArchivoSintomas.getSintomasArchivo());
        if (valido) {
            tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
            String sintomaValidado = validador.getValidado(nombreSintomaTextField.getText());
            boolean agregado = gestorArchivoSintomas.guardarSintoma(sintomaValidado, tipoSintoma);
            if (agregado) {
                mostrarNuevoMensaje("Agregado con exito!!", Color.GREEN);
                tablePanel.addRow(new String[]{sintomaValidado, tipoSintoma }, gestorArchivoSintomas.crearSintoma(sintomaValidado, tipoSintoma));
            } else {
                mostrarNuevoMensaje("Lo sentimos, no se pudo agregar a la lista de sintomas", Color.RED);
            }
        } else {
            mostrarNuevoMensaje("Lo sentimos, no se pudo agregar por que el nombre no es valido", Color.RED);
        }
    }

    private void mostrarNuevoMensaje(String mensaje, Color color){
        mensajeConfirmacionJPanel.setBackground(color);
        mensajeDeConfirmacion.setText(mensaje);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();
        if (botonPulsado == addSintomaButton) {
            agregarSintoma();
        } else if (botonPulsado == finalizarButton){
            finalizar(registrarSintomasFrame);
        }
    }
}
