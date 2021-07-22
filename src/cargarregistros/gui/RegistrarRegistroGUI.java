package cargarregistros.gui;

import monitor.Sintomas;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegistrarRegistroGUI extends JFrame {
    private final RegistrosJPanel registrosJPanelLabel;

    public RegistrarRegistroGUI(Sintomas sintomas) {
        this.setLocation(200, 50);
        this.setPreferredSize(new Dimension(1000, 620));
        registrosJPanelLabel = new RegistrosJPanel(RegistrarRegistroGUI.this, sintomas);
        this.add(registrosJPanelLabel);
        this.pack();
        this.setResizable(false);
        this.setTitle("Registrar registros");
        this.setVisible(true);
        finalizar();
        sincronizar();
    }

    private void sincronizar() {
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void finalizar() {
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                closeWindow();
            }
        });
    }

    private void closeWindow() {
        try {
            synchronized (this) {
                this.notify();
            }
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
