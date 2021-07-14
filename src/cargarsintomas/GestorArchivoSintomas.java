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
        archivoSintomas = "IsmaSintomas.dat";
        paquete = "sintomas";
        verificarExistenciaArchivo();
    }
    private void verificarExistenciaArchivo(){
        File fileSintomas = new File(archivoSintomas);
        if(!fileSintomas.exists()){
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
            }
        }
    }
    public Sintoma crearSintoma(String nombreSintoma, String tipo){
        Class<?> cl = null;
        Sintoma sintoma=null;
        try {
            cl = Class.forName(paquete + "." + tipo);
            Constructor<?> constructor = cl.getConstructor(String.class);
            sintoma = (Sintoma) (constructor.newInstance(new Object[]{nombreSintoma}));
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e) {
        } catch (IllegalAccessException e) {
        } catch (InstantiationException e) {
        } catch (InvocationTargetException e) {
        }
        return sintoma;
    }

    public boolean guardarSintoma(String nombreSintoma, String tipo) {
        boolean guardado=false;
        Sintoma sintoma = null;
        Sintomas sintomas = getSintomasArchivo();
        ObjectOutputStream file = null;
        try {
//            Class<?> cl = Class.forName(paquete + "." + tipo);
//            Constructor<?> constructor = cl.getConstructor(String.class);
            sintoma = crearSintoma(nombreSintoma, tipo);//(Sintoma) (constructor.newInstance(new Object[]{nombreSintoma}));
            sintomas.add(sintoma);
            file = new ObjectOutputStream(new FileOutputStream(archivoSintomas));
            file.writeObject(sintomas);
            file.close();
            guardado = true;
        } catch (IOException e) {
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
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }
        return sintomas;
    }
}
