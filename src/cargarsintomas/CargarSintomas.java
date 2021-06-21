package cargarsintomas;

import cargarsintomas.gui.RegistrarSintomaConsola;
import cargarsintomas.gui.RegistrarSintomaGUI;
import monitor.Sintomas;

public class CargarSintomas {
    private GestorArchivoSintomas gestorArchivosSintoma;
    private Sintomas sintomas;
    public CargarSintomas() {
        gestorArchivosSintoma = new GestorArchivoSintomas();
        sintomas = new Sintomas();
        cargarSintomas();
    }

    private void cargarSintomas(){
//        RegistrarSintomaConsola interfaz = new RegistrarSintomaConsola();
        RegistrarSintomaGUI interfaz = new RegistrarSintomaGUI();
    }

    public Sintomas getSintomas(){
        return sintomas = gestorArchivosSintoma.getSintomasArchivo();
    }

}
