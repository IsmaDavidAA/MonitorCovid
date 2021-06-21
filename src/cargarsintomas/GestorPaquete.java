package cargarsintomas;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GestorPaquete {

    public Class[] getClasses(String packageName){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration resources = null;
        try {
            resources = classLoader.getResources(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List dirs = new ArrayList();
        while (resources.hasMoreElements()) {
            URL resource = (URL) resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList classes = new ArrayList();
        for (Object directory : dirs) {
            classes.addAll(findClasses((File) directory, packageName));
        }

        return (Class[]) classes.toArray(new Class[classes.size()]);
    }



    public List findClasses(File directorio, String nombrePaquete){
        List classes = new ArrayList();
        if (directorio.exists()) {
            File[] files = directorio.listFiles();
            for (File file : files) {
                if (!file.isDirectory() && file.getName().endsWith(".class")) {
                    try {
                        classes.add(Class.forName(nombrePaquete + '.' + file.getName().substring(0, file.getName().indexOf("."))));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return classes;
    }


    public Set<String> getTiposDeSintomas() {
        Set<String> tiposDeSintomas = new HashSet<>();
        Class[] clases = getClasses("sintomas");
        for (Class x:clases) {
            if(x.getGenericSuperclass() != null && x.getModifiers() == 1){
                if(x.getGenericSuperclass().getTypeName().endsWith(".Sintoma")){
                    String nombre = x.getName().substring(x.getName().indexOf(".") +1);
                    tiposDeSintomas.add(nombre);
                }
            }
        }
        return tiposDeSintomas;
    }
}