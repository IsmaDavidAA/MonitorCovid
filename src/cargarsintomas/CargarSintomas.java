package cargarsintomas;

import cargarsintomas.gui.RegistrarSintomaGUI;
import cargarsintomas.util.GestorArchivoSintomas;
import monitor.Sintomas;

public class CargarSintomas {
    private final GestorArchivoSintomas gestorArchivosSintoma;

    public CargarSintomas() {
        gestorArchivosSintoma = new GestorArchivoSintomas();
        cargarSintomas();
    }

    private void cargarSintomas() {
        new RegistrarSintomaGUI();
    }

    public Sintomas getSintomas() {
        return gestorArchivosSintoma.getSintomasArchivo();
    }

}
