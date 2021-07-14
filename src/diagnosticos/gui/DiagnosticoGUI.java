package diagnosticos.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class DiagnosticoGUI extends JFrame{
    public DiagnosticoGUI(int diagnostico) {
            Toolkit myScreen = Toolkit.getDefaultToolkit();
            Dimension size = myScreen.getScreenSize();
            int WIDTH = 600;
            int HEIGHT = 400;
            int x = (size.width - WIDTH) / 2;
            int y = (size.height - HEIGHT) / 2;
            setTitle("Monitor Covid Diagnostico");
            setBounds(x, y, WIDTH, HEIGHT);
            setResizable(false);
        DiagnosticoJPanel registroJPanel = new DiagnosticoJPanel(diagnostico,this);
            add(registroJPanel);
            setVisible(true);
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