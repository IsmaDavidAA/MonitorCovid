package cargarregistros.util;

import monitor.Registro;
import monitor.Registros;

import java.io.*;

public class GestorArchivoRegistros {
    private static final String ARCHIVO_REGISTROS = "./IsmaRegistros.dat";

    public GestorArchivoRegistros() {
        verificarExistenciaArchivo();
    }

    public void verificarExistenciaArchivo() {
        File fileSintomas = new File(ARCHIVO_REGISTROS);
        if (!fileSintomas.exists()) {
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean guardarRegistro(Registro registro) {
        boolean guardado = false;
        Registros registros = getRegistrosArchivo();
        ObjectOutputStream file;
        try {
            if (registro.getSintomas() != null) {
                registros.push(registro);
                file = new ObjectOutputStream(new FileOutputStream(ARCHIVO_REGISTROS));
                file.writeObject(registros);
                file.close();
                guardado = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return guardado;
    }

    public Registros getRegistrosArchivo() {
        Registros registros = new Registros();
        ObjectInputStream file;
        try {
            file = new ObjectInputStream(new FileInputStream(ARCHIVO_REGISTROS));
            registros = (Registros) file.readObject();
            file.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return registros;
    }
}
