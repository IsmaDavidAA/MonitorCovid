package cargarsintomas.util;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GestorArchivoSintomas {
    private static final String ARCHIVO_SINTOMAS = "./IsmaSintomas.dat";
    private static final String PAQUETE = "sintomas";

    public GestorArchivoSintomas() {
        verificarExistenciaArchivo();
    }

    private void verificarExistenciaArchivo() {
        File fileSintomas = new File(ARCHIVO_SINTOMAS);
        if (!fileSintomas.exists()) {
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Sintoma crearSintoma(String nombreSintoma, String tipo) {
        Class<?> cl;
        Sintoma sintoma = null;
        try {
            cl = Class.forName(PAQUETE + "." + tipo);
            Constructor<?> constructor = cl.getConstructor(String.class);
            sintoma = (Sintoma) (constructor.newInstance(new Object[]{nombreSintoma}));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return sintoma;
    }

    public boolean guardarSintoma(String nombreSintoma, String tipo) {
        boolean guardado = false;
        Sintoma sintoma;
        Sintomas sintomas = getSintomasArchivo();
        ObjectOutputStream file;
        try {
            sintoma = crearSintoma(nombreSintoma, tipo);
            sintomas.add(sintoma);
            file = new ObjectOutputStream(new FileOutputStream(ARCHIVO_SINTOMAS));
            file.writeObject(sintomas);
            file.close();
            guardado = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return guardado;
    }

    public Sintomas getSintomasArchivo() {
        Sintomas sintomas = new Sintomas();
        ObjectInputStream file;
        try {
            file = new ObjectInputStream(new FileInputStream(ARCHIVO_SINTOMAS));
            sintomas = (Sintomas) file.readObject();
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return sintomas;
    }
}
