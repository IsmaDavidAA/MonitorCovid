package cargarsintomas.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrarSintomaGUI extends JFrame{
    private final SintomasJPanel sintomasJPanel;
    public RegistrarSintomaGUI() {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(520, 640));
        sintomasJPanel = new SintomasJPanel(RegistrarSintomaGUI.this);
        this.add(sintomasJPanel);
        this.pack();
        this.setResizable(false);
        this.setTitle("Registrar sintomas");
        this.setVisible(true);
        finalizar();
        sincronizar();
    }

    private void sincronizar(){
        synchronized(this){
            try{
                this.wait();
            } catch(InterruptedException ex){
                //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
            }
        }
    }

    private void finalizar(){
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent we){
                closeWindow();
            }
        });
    }

    private void closeWindow(){
        try {
            synchronized(this){
                this.notify();
            }
            this.dispose();
        } catch (Exception e){
            //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
        }
    }
}

