package cargarsintomas.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;

public class MensajeJPanel extends JPanel {
    private final JLabel mensajeJLabel;
    private final static Color ALERTA = new Color(255, 77, 77);
    private final static Color EXITO = new Color(106, 255, 77);

    public MensajeJPanel() {
        mensajeJLabel = new JLabel("");
        setBorder(BorderFactory.createEtchedBorder());
        this.add(mensajeJLabel);
    }

    public void actualizarMensaje(String mensaje, int tipo) {
        if (tipo == 1) {
            this.setBackground(EXITO);
        } else {
            this.setBackground(ALERTA);
        }
        mensajeJLabel.setText(mensaje);
    }
}
