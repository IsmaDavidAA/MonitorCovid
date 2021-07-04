package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;
import cargarsintomas.GestorPaquete;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LabelSintomas extends JLabel{
    private JPanel salirPanel;
    private JPanel addSintomaPanel;
    private TablaSintomas tablePanel;
    private JButton finalizarButton;
    private JComboBox tiposSintomasComboBox;
    private JTextField nombreSintomaTextField;
    private JButton addSintomaButton;
    private GestorArchivoSintomas gestorArchivoSintomas;
    private GestorPaquete gestorPaquete;
    private Validador validador;
    private String tipoSintoma;
    private RegistrarSintomaGUI registrarSintomasFrame;
    public LabelSintomas(RegistrarSintomaGUI registrarSintomasFrame){
        this.registrarSintomasFrame = registrarSintomasFrame;
        crearComponentes();
        agregarComponentes();
        eventoOyenteAgregarSintoma();
        tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
        finalizar(registrarSintomasFrame);
    }

    private void crearComponentes(){
        gestorArchivoSintomas = new GestorArchivoSintomas();
        gestorPaquete = new GestorPaquete();
        validador = new Validador();
        salirPanel = new JPanel();
        addSintomaPanel = new JPanel();
        tablePanel = new TablaSintomas(gestorArchivoSintomas.getSintomasArchivo());
        finalizarButton = new JButton("Finalizar");
        nombreSintomaTextField = new JTextField();
        tiposSintomasComboBox = new JComboBox();
        addSintomaButton = new JButton("Agregar");
        tiposSintomasComboBox.setModel(new DefaultComboBoxModel(gestorPaquete.getTiposDeSintomas().toArray()));
        tablePanel.setLayout(new BorderLayout());
    }

    private void agregarComponentes(){
        addSintomaPanel.add(tiposSintomasComboBox);
        addSintomaPanel.add(nombreSintomaTextField);
        addSintomaPanel.add(addSintomaButton);
        salirPanel.add(finalizarButton);
        this.add(salirPanel);
        this.add(addSintomaPanel);
        this.add(tablePanel);
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

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        tiposSintomasComboBox.setBounds(2, 5, 110, 25);
        nombreSintomaTextField.setBounds(2, 45, 190, 25);
        addSintomaButton.setBounds(192, 45, 120, 25);
        finalizarButton.setBounds(50, 0, 100, 30);
        salirPanel.setBounds( 600, 560, 200, 50);
        addSintomaPanel.setBounds(10, 10, 400, 120);
        tablePanel.setBounds(0, 130, 580, 440);
    }

    private void eventoOyenteAgregarSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int confirmado = JOptionPane.showConfirmDialog(LabelSintomas.this,
                        "Seguro que desea registrar este sintoma?",
                        "",JOptionPane.YES_NO_OPTION);
                if(confirmado==0) {
                    boolean valido = validador.validar(nombreSintomaTextField.getText());
                    if (valido) {
                        tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
                        String sintomaValidado = validador.getValidado(nombreSintomaTextField.getText());
                        boolean agregado = gestorArchivoSintomas.guardarSintoma(sintomaValidado, tipoSintoma);
                        if (agregado) {
                            JOptionPane.showMessageDialog(LabelSintomas.this,"Agregado con exito!!");
                            tablePanel.addRow(new String[]{sintomaValidado, tipoSintoma });
                        } else {
                            JOptionPane.showMessageDialog(LabelSintomas.this,"Lo sentimos, no se pudo agregar");
                        }
                    } else {
                        JOptionPane.showMessageDialog(LabelSintomas.this, "Lo sentimos, el sintoma no es valido");
                    }
                }
            }
        };
        addSintomaButton.addActionListener(oyenteAccion);
    }
}
