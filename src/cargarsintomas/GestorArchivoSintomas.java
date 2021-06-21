package cargarsintomas;

import monitor.Sintoma;
import monitor.Sintomas;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashSet;
import java.util.Set;

public class GestorArchivoSintomas {
    private String archivoSintomas;
    private String paquete;
    public GestorArchivoSintomas(){
        archivoSintomas = "cargarsintomas/sintomas.dat";
        paquete = "sintomas";
        verificarExistenciaArchivo();
    }
    private void verificarExistenciaArchivo(){
        File fileSintomas = new File(archivoSintomas);
        if(!fileSintomas.exists()){
            try {
                fileSintomas.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
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
        } catch (Exception e) {
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
        } catch (Exception e) {
        }
        return sintomas;
    }
    public Set<String> getNombresSintomas(){
        Set<String> sintomasNombres =  new HashSet<>();
        Sintomas sintomas = getSintomasArchivo();
        for (Sintoma sintoma: sintomas) {
            sintomasNombres.add(sintoma.toString());
        }
        return sintomasNombres;
    }
}
