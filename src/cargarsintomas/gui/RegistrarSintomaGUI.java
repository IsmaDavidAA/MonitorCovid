package cargarsintomas.gui;

import cargarregistros.gui.RegistrarRegistroGUI;
import cargarsintomas.GestorArchivoSintomas;
import cargarsintomas.GestorPaquete;
import monitor.Sintoma;
import monitor.Sintomas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrarSintomaGUI extends JFrame{
    private JPanel sintomasOpcionesPanel;
    private JPanel addSintomaPanel;
    private JPanel tablePanel;
    private JComboBox tiposSintomasComboBox;
    private JTextField nombreSintomaTextField;
    private JButton addSintomaButton;
    private JTable SintomasTable;
    private GestorArchivoSintomas gestorArchivoSintomas;
    private GestorPaquete gestorPaquete;
    private Validador validador;
    private String tipoSintoma;
    public RegistrarSintomaGUI() {
        this.setDefaultCloseOperation(2);
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(410, 600));
        cargarComponentes();
        gestorArchivoSintomas = new GestorArchivoSintomas();
        gestorPaquete = new GestorPaquete();
        validador = new Validador();
        this.tiposSintomasComboBox.setModel(new DefaultComboBoxModel(gestorPaquete.getTiposDeSintomas().toArray()));
        actualizarTablaDeSintomas();
        this.SintomasTable.setEnabled(false);
        this.pack();
        this.setResizable(false);
//        this.setModal(true);
        this.setVisible(true);
        this.setTitle("Registrar sintomas");

        final RegistrarSintomaGUI frame = this;

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
        sintomasOpcionesPanel = new JPanel();
        addSintomaPanel = new JPanel();
        tablePanel = new JPanel();
        this.setLayout(null);
        nombreSintomaTextField = new JTextField();
        tiposSintomasComboBox = new JComboBox();
        addSintomaButton = new JButton();
        SintomasTable = new JTable();
        addSintomaButton.setText("Agregar sintoma");
        nombreSintomaTextField.setPreferredSize(new Dimension(150, 20));
        sintomasOpcionesPanel.add(tiposSintomasComboBox);
        addSintomaPanel.add(nombreSintomaTextField);
        addSintomaPanel.add(addSintomaButton);
        SintomasTable.setBounds(5,5, 400, 400);
        tablePanel.add(SintomasTable);

        sintomasOpcionesPanel.setBounds( 0, 10, 200, 50);
        addSintomaPanel.setBounds(200, 10, 200, 70);
        tablePanel.setBounds(0, 130, 420, 420);
        tablePanel.setLayout(new BorderLayout());

        this.add(sintomasOpcionesPanel);
        this.add(addSintomaPanel);
        this.add(tablePanel);
        eventoOyenteAgregarSintoma();
        eventoOyenteSeleccionarTipoSintoma();
    }

    public void actualizarTablaDeSintomas(){
        DefaultTableModel table = new DefaultTableModel();
        table.addColumn("Sintoma");
        table.addColumn("Categoria");
        Sintomas sintomas = gestorArchivoSintomas.getSintomasArchivo();
        for (Sintoma sintoma : sintomas) {
            table.addRow(new Object[]{sintoma.toString(), sintoma.getClass().getName().split("\\.")[1]});
        }
        SintomasTable.setModel(table);
        tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
    }

    public void eventoOyenteSeleccionarTipoSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){

                tipoSintoma = (String) tiposSintomasComboBox.getSelectedItem();
            }
        };
        tiposSintomasComboBox.addActionListener(oyenteAccion);
    }

    public void eventoOyenteAgregarSintoma() {
        ActionListener oyenteAccion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae){
                int confirmado = JOptionPane.showConfirmDialog(RegistrarSintomaGUI.this,
                        "Seguro que desea registrar este sintoma?",
                        "",JOptionPane.YES_NO_OPTION);
                if(confirmado==0) {
                    boolean agregado = false;
                    boolean valido = false;
                    valido = validador.validar(nombreSintomaTextField.getText());
                    if (valido) {
                        agregado = gestorArchivoSintomas.guardarSintoma(validador.getValidado(nombreSintomaTextField.getText()), tipoSintoma);
                        if (agregado) {
                            mensajeDeAgregado("Agregado con exito!!");
                            actualizarTablaDeSintomas();
                        } else {
                            mensajeDeAgregado("Lo sentimos, no se pudo agregar");
                        }
                    } else {
                        mensajeDeAgregado("Lo sentimos, el sintoma no es valido");
                    }
                }
            }
        };
        addSintomaButton.addActionListener(oyenteAccion);
    }
    private void mensajeDeAgregado(String msjagregado){
        JOptionPane.showMessageDialog(RegistrarSintomaGUI.this, msjagregado);
    }
}

