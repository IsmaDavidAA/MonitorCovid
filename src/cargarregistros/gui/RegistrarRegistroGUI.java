package cargarregistros.gui;

import monitor.Sintomas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrarRegistroGUI extends JFrame {
    private Sintomas sintomas;
    private RegistrosJPanel registrosJPanelLabel;
    public RegistrarRegistroGUI(Sintomas sintomas) {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(1100, 620));
        this.sintomas = sintomas;
        registrosJPanelLabel = new RegistrosJPanel(RegistrarRegistroGUI.this, sintomas);
        this.add(registrosJPanelLabel);
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
