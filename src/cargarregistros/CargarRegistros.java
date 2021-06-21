package cargarregistros;

import cargarregistros.gui.RegistrarRegistroConsola;
import cargarregistros.gui.RegistrarRegistroGUI;
import monitor.Registro;
import monitor.Sintomas;

public class CargarRegistros {

    private Sintomas sintomas;
    private GestorArchivoRegistros gestorArchivoRegistros;
    private RegistrarRegistroConsola registrarRegistroConsola;
    private RegistrarRegistroGUI registrarRegistroGUI;
    public CargarRegistros(Sintomas sintomas) {
        this.gestorArchivoRegistros = new GestorArchivoRegistros();
        this.sintomas = sintomas;
    }

    private void cargarSintoma() {
        try {
//            registrarRegistroConsola = new RegistrarRegistroConsola(sintomas);
            registrarRegistroGUI = new RegistrarRegistroGUI(sintomas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Registro getRegistro() {
        cargarSintoma();
        Registro ultimoRegistro = gestorArchivoRegistros.getUltimoRegistro();
        return ultimoRegistro;
    }
}

