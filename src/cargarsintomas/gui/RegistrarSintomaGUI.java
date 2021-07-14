package cargarsintomas.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegistrarSintomaGUI extends JFrame{
    private SintomasJPanel sintomasJPanel;
    public RegistrarSintomaGUI() {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(600, 660));
        sintomasJPanel = new SintomasJPanel(RegistrarSintomaGUI.this);
        this.add(sintomasJPanel);
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

    private void finalizar(JFrame frame){
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
}

