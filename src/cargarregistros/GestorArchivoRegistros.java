package cargarregistros;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintomas;
import java.io.*;
import java.util.*;

public class GestorArchivoRegistros {
    private String archivoRegistros;
    public GestorArchivoRegistros() {
        archivoRegistros = "IsmaRegistros.dat";
        verificarExistenciaArchivo();
    }

    private void verificarExistenciaArchivo(){
        File fileSintomas = new File(archivoRegistros);
        if(!fileSintomas.exists()){
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean guardarRegistro(Sintomas sintomas, Date fecha) {
        boolean guardado=false;
        Registro registro = new Registro(fecha, sintomas);
        Registros registros = getRegistrosArchivo();
        ObjectOutputStream file = null;
        try {
            registros.push(registro);
            file = new ObjectOutputStream(new FileOutputStream(archivoRegistros));
            file.writeObject(registros);
            file.close();
            guardado = true;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return guardado;
    }



    public Registros getRegistrosArchivo(){
        Registros registros = new Registros();
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream(archivoRegistros));
            registros = (Registros) file.readObject();
            file.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return registros;
    }


    public Registro getUltimoRegistro(){
        Registros registros = new Registros();
        Registro ultimo = null;
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream(archivoRegistros));
            registros = (Registros) file.readObject();
            ultimo = registros.peek();
            file.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return ultimo;
    }
}
