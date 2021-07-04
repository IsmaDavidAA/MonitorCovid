package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GestorArchivoSintomas {
    private String archivoSintomas;
    private String paquete;
    public GestorArchivoSintomas(){
        archivoSintomas = "sintomas.dat";
        paquete = "sintomas";
        verificarExistenciaArchivo();
    }
    private void verificarExistenciaArchivo(){
        File fileSintomas = new File(archivoSintomas);
        if(!fileSintomas.exists()){
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }
    public boolean guardarSintoma(String nombreSintoma, String tipo) {
        boolean guardado=false;
        Sintoma sintoma = null;
        Sintomas sintomas = getSintomasArchivo();
        ObjectOutputStream file = null;
        try {
            Class<?> cl = Class.forName(paquete + "." + tipo);
            Constructor<?> constructor = cl.getConstructor(String.class);
            sintoma = (Sintoma) (constructor.newInstance(new Object[]{nombreSintoma}));
            sintomas.add(sintoma);
            file = new ObjectOutputStream(new FileOutputStream(archivoSintomas));
            file.writeObject(sintomas);
            file.close();
            guardado = true;
        } catch (IllegalAccessException e) {
//            e.printStackTrace();
        } catch (InstantiationException e) {
//            e.printStackTrace();
        } catch (InvocationTargetException e) {
//            e.printStackTrace();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
        return guardado;
    }
    public Sintomas getSintomasArchivo(){
        Sintomas sintomas = new Sintomas();
        ObjectInputStream file = null;
        try {
            file = new ObjectInputStream(new FileInputStream(archivoSintomas));
            sintomas = (Sintomas) file.readObject();
            file.close();
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
        }
        return sintomas;
    }
}
