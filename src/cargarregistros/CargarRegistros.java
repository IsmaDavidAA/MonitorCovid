package cargarregistros;

import cargarregistros.gui.RegistrarRegistroGUI;
import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;

import java.util.Date;

public class CargarRegistros {

    private Sintomas sintomas;
    private GestorArchivoRegistros gestorArchivoRegistros;
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

    public Registro getRegistro() {
        cargarSintoma();
        Registro ultimoRegistro = gestorArchivoRegistros.getUltimoRegistro();
        if(ultimoRegistro == null){
            ultimoRegistro = new Registro(new Date(),new Sintomas());
        }
        return ultimoRegistro;
    }

    public Registros getRegistros(){
        cargarSintoma();
        return gestorArchivoRegistros.getRegistrosArchivo();
    }
}

