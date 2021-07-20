package cargarsintomas.util;

import monitor.Sintoma;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GestorPaquete {
    private static final String NOMBRE_PAQUETE = "sintomas";
    private static final String NOMBRE_JAR = "home.jar";
    public List<String> getTiposDeSintomas()  {
        List<String> listaClasesPaquete = new ArrayList<>();
        File[] classes = this.archivosPaquete(NOMBRE_PAQUETE);
        if (classes == null) {
            try {
                ZipInputStream zip = new ZipInputStream(new FileInputStream(NOMBRE_JAR));
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.'); // including ".class"
                        if(className.split("\\.")[0].equals(NOMBRE_PAQUETE)) {
                            listaClasesPaquete.add(className.split("\\.")[1]);
                        }
                    }
                }
            } catch ( IOException e) {
                //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
            }
        } else {
            Class<Sintoma> sintomaClass = Sintoma.class;
            for (File clase: classes){
                try {
                    String nombreClase = clase.getName().split("\\.")[0];
                    Class.forName(NOMBRE_PAQUETE+"."+nombreClase).asSubclass(sintomaClass);
                    listaClasesPaquete.add(nombreClase);
                } catch ( ClassNotFoundException e) {
                    //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
                }
            }
        }
        return listaClasesPaquete;
    }

    private File[] archivosPaquete(String nombrePaquete) {
        File dir = null;
        File[] archivos = null;
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader()
                    .getResources(nombrePaquete);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                dir = new File(url.getFile());
            }
            archivos = dir.listFiles();
        } catch (IOException e) {
            //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
        } catch (NullPointerException e){
            //TRATAMIENTO DE EXCEPCIONES-------------------------------------------------------------------------
        }
        return archivos;
    }
}