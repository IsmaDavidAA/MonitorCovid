package diagnosticos.gui;
import diagnosticos.Recomendaciones;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DiagnosticoJPanel extends JPanel implements ActionListener {
    private final JLabel labelFecha;
    private final JPanel jPanel1;
    private final JButton finalizarButton;
    private DiagnosticoGUI frameFase;

    public DiagnosticoJPanel(int diagnostico, DiagnosticoGUI frameFase){
        this.frameFase = frameFase;
        Recomendaciones recomendaciones = new Recomendaciones();
        jPanel1 = new JPanel();
        Border bordejpanel = new TitledBorder(new EtchedBorder(), "Recomendacion de diagnostico");

        jPanel1.setBorder(bordejpanel);
        add(jPanel1);
        labelFecha = new JLabel(recomendaciones.generarRecomendacion(diagnostico));

        jPanel1.add(labelFecha);

        finalizarButton = new JButton("Finalizar");
        finalizarButton.addActionListener(this);
        add(finalizarButton);

    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        jPanel1.setBounds(40, 70, 500,200);
        labelFecha.setBounds(40, 55, 500,30);
        finalizarButton.setBounds(100, 300, 100,30);

    }

    public void actionPerformed(ActionEvent e) {
        Object botonPulsado = e.getSource();

        if ( botonPulsado == finalizarButton){
            finalizar(frameFase);
        }
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

}
