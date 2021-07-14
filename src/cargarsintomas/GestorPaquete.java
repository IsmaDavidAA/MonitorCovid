package cargarsintomas;

import monitor.Sintoma;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class GestorPaquete {

    public List<String> getPaquete(String nombrePaquete)  {
        List<String> listaClasesPaquete = new ArrayList<>();
        File[] classes = this.archivosPaquete(nombrePaquete);
        if (classes == null) {
            try {
                ZipInputStream zip = new ZipInputStream(new FileInputStream("home.jar"));
                for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) {
                    if (!entry.isDirectory() && entry.getName().endsWith(".class")) {
                        String className = entry.getName().replace('/', '.'); // including ".class"
                        if(className.split("\\.")[0].equals("sintomas")) {
                            listaClasesPaquete.add(className.split("\\.")[1]);
                        }
                    }
                }
            } catch ( IOException e) {
                e.printStackTrace();
            }
        } else {
            Class<Sintoma> sintomaClass = Sintoma.class;
            for (File clase: classes){
                try {
                    String nombreClase = clase.getName().split("\\.")[0];
                    Class.forName(nombrePaquete+"."+nombreClase).asSubclass(sintomaClass);
                    listaClasesPaquete.add(nombreClase);
                } catch ( ClassNotFoundException e) {
                    e.printStackTrace();
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

        } catch (NullPointerException e){

        }
        return archivos;
    }


    public List<String> getTiposDeSintomas() {
        List<String> tiposDeSintomas  = getPaquete("sintomas");
        return tiposDeSintomas;
    }
}