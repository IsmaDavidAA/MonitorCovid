package cargarregistros;

import cargarregistros.gui.RegistrarRegistroGUI;
import cargarregistros.util.GestorArchivoRegistros;
import monitor.Registros;
import monitor.Sintomas;

public class CargarRegistros {

    private Sintomas sintomas;
    private final GestorArchivoRegistros gestorArchivoRegistros;
    private RegistrarRegistroGUI registrarRegistroGUI;

    public CargarRegistros(Sintomas sintomas) {
        this.gestorArchivoRegistros = new GestorArchivoRegistros();
        this.sintomas = sintomas;
    }

    private void cargarSintoma() {
        try {
            registrarRegistroGUI = new RegistrarRegistroGUI(sintomas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Registros getRegistros() {
        cargarSintoma();
        return gestorArchivoRegistros.getRegistrosArchivo();
    }
}

