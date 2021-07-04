package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;
import cargarsintomas.GestorPaquete;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrarSintomaGUI extends JFrame{
//    private JPanel salirPanel;
//    private JPanel addSintomaPanel;
//    private TablaSintomas tablePanel;
//    private JButton finalizarButton;
//    private JComboBox tiposSintomasComboBox;
//    private JTextField nombreSintomaTextField;
//    private JButton addSintomaButton;
//    private GestorArchivoSintomas gestorArchivoSintomas;
//    private GestorPaquete gestorPaquete;
//    private Validador validador;
//    private String tipoSintoma;
    private LabelSintomas labelSintomas;
    public RegistrarSintomaGUI() {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(1000, 660));
//        this.setLayout(null);
//        crearComponentes();
//        agregarComponentes();
//        eventoOyenteAgregarSintoma();
//        eventoOyenteSeleccionarTipoSintoma();
//        tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
        labelSintomas = new LabelSintomas(RegistrarSintomaGUI.this);
        this.add(labelSintomas);
        this.pack();
        this.setResizable(false);
        this.setTitle("Registrar sintomas");
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

//    private void crearComponentes(){
//        gestorArchivoSintomas = new GestorArchivoSintomas();
//        gestorPaquete = new GestorPaquete();
//        validador = new Validador();
//        salirPanel = new JPanel();
//        addSintomaPanel = new JPanel();
//        tablePanel = new TablaSintomas();
//        tablePanel.actualizarTablaDeSintomas(gestorArchivoSintomas.getSintomasArchivo());
//        finalizarButton = new JButton("Finalizar");
//        nombreSintomaTextField = new JTextField();
//        tiposSintomasComboBox = new JComboBox();
//        addSintomaButton = new JButton("Agregar sintoma");
//        nombreSintomaTextField.setPreferredSize(new Dimension(150, 20));
//        tiposSintomasComboBox.setModel(new DefaultComboBoxModel(gestorPaquete.getTiposDeSintomas().toArray()));
//        salirPanel.setBounds( 150, 550, 200, 70);
//        addSintomaPanel.setBounds(200, 10, 200, 120);
//        tablePanel.setBounds(0, 130, 420, 420);
//        tablePanel.setLayout(new BorderLayout());
//    }

//    private void agregarComponentes(){
//        addSintomaPanel.add(tiposSintomasComboBox);
//        addSintomaPanel.add(nombreSintomaTextField);
//        addSintomaPanel.add(addSintomaButton);
//        salirPanel.add(finalizarButton);
//        this.add(salirPanel);
//        this.add(addSintomaPanel);
//        this.add(tablePanel);
//    }

    private void finalizar(JFrame frame){
//        ActionListener oyenteAccion=new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae){
//                closeWindow(frame);
//            }
//
//        };
//        finalizarButton.addActionListener(oyenteAccion);
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
//    private void eventoOyenteSeleccionarTipoSintoma() {
//        ActionListener oyenteAccion=new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae){
//                tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
//            }
//        };
//        tiposSintomasComboBox.addActionListener(oyenteAccion);
//    }

//    private void eventoOyenteAgregarSintoma() {
//        ActionListener oyenteAccion=new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent ae){
//                int confirmado = JOptionPane.showConfirmDialog(RegistrarSintomaGUI.this,
//                        "Seguro que desea registrar este sintoma?",
//                        "",JOptionPane.YES_NO_OPTION);
//                if(confirmado==0) {
//                    boolean valido = validador.validar(nombreSintomaTextField.getText());
//                    if (valido) {
//                        boolean agregado = gestorArchivoSintomas.guardarSintoma(validador.getValidado(nombreSintomaTextField.getText()), tipoSintoma);
//                        if (agregado) {
//                            JOptionPane.showMessageDialog(RegistrarSintomaGUI.this,"Agregado con exito!!");
//                            tablePanel.actualizarTablaDeSintomas(gestorArchivoSintomas.getSintomasArchivo());
//                        } else {                            JOptionPane.showMessageDialog(RegistrarSintomaGUI.this,"Lo sentimos, no se pudo agregar");
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(RegistrarSintomaGUI.this, "Lo sentimos, el sintoma no es valido");
//                    }
//                }
//            }
//        };
//        addSintomaButton.addActionListener(oyenteAccion);
//    }
}

