package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;
import cargarsintomas.GestorPaquete;
import monitor.Sintoma;
import monitor.Sintomas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RegistrarSintomaConsola {
    private Map<Integer, String> tiposDeSintomas;
    private GestorArchivoSintomas gestorArchivoSintomas;
    private Validador validadorNombre;
    private GestorPaquete gestorPaquete;
    public RegistrarSintomaConsola() {
        tiposDeSintomas = new HashMap<>();
        gestorArchivoSintomas = new GestorArchivoSintomas();
        validadorNombre = new Validador();
        gestorPaquete = new GestorPaquete();
        leerTiposDeSintomas();
        ejecutarSintomas();
    }

    public void ejecutarSintomas(){
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        boolean finGuardado= false;
        String nombre="";
        int opcion = 0;
        while(!finGuardado) {
            System.out.println("1-Mostrar sintomas");
            System.out.println("2-Agregar sintomas");
            System.out.println("3-Terminar registro de sintomas \n\n");
            try {
                opcion = Integer.parseInt(bf.readLine());
                if (opcion == 1) {
                    mostrarSintomas();
                } else if (opcion == 2) {
                System.out.println("Nombre del sintoma: ");
                nombre = bf.readLine();
                mostrarTiposSintomas();
                opcion = Integer.parseInt(bf.readLine());
                if (tiposDeSintomas.containsKey(opcion) && validadorNombre.validar(nombre)) {
                    boolean guardado = gestorArchivoSintomas.guardarSintoma(validadorNombre.getValidado(nombre), tiposDeSintomas.get(opcion));
                    if (guardado) {
                        System.out.println("\nEl sintoma se guardo con exito!!!\n");
                    } else {
                        System.out.println("\nLo sentimos, no se pudo guardar el sintoma\n");
                    }
                } else {
                    System.out.println("\nLo sentimos, el sintoma no es valido\n");
                    }
                }else if(opcion == 3){
                    finGuardado = true;
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            opcion = 0;
        }
    }

    private void mostrarSintomas() {
        Sintomas sintomas = gestorArchivoSintomas.getSintomasArchivo();
        System.out.println("Sintomas: ");
        for (Sintoma sintoma : sintomas) {
            System.out.println(" " + sintoma.getClass().getName().split("\\.")[1] + ":      " + sintoma);
        }
        System.out.println("--------------------------\n");
    }

    private void mostrarTiposSintomas(){
        System.out.println("Escoger el tipo de sintoma");
        for(Map.Entry<Integer, String> entry : tiposDeSintomas.entrySet()) {
            System.out.println(entry.getKey()+"-"+entry.getValue());
        }
    }

    private void leerTiposDeSintomas(){
        int ite=1;
        Set<String> tipos = gestorPaquete.getTiposDeSintomas();
        for (String tipo: tipos){
            tiposDeSintomas.put(ite, tipo);
            ite++;
        }
    }
}

