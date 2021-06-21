package cargarregistros.gui;

import cargarregistros.GestorArchivoRegistros;
import monitor.Sintoma;
import monitor.Sintomas;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

public class RegistrarRegistroConsola {
    private Map<Sintoma, Boolean> sintomasSeleccionados;
    private Map<Integer, Sintoma> sintomasMostrar;
    private GestorArchivoRegistros gestorArchivoRegistros;
    private Sintomas sintomas;
    private Date fechaHoy;
    public RegistrarRegistroConsola(Sintomas sintomas){
        sintomasSeleccionados = new HashMap<>();
        sintomasMostrar = new HashMap<>();
        gestorArchivoRegistros = new GestorArchivoRegistros();
        this.sintomas = sintomas;
        fechaHoy = new Date();
        leerSintomas();
        ejecutarSintomas();
    }

    public void ejecutarSintomas() {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        boolean finGuardado= false;
        String nombre="";
        int opcion = 0;
        String fechaActual;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy--hh:mm");
        while(!finGuardado){
            fechaActual = formatter.format(fechaHoy.getTime());
            System.out.println("Fecha: "+fechaActual);
            System.out.println("1-Agregar sintoma");
            System.out.println("2-Registrar sintomas\n");
            try {
                opcion = Integer.parseInt(bf.readLine());
                if (opcion == 1) {
                    mostrarTiposSintomas();
                    opcion = Integer.parseInt(bf.readLine());
                    if (sintomasMostrar.containsKey(opcion)) {
                        marcarDescarmar(sintomasMostrar.get(opcion));
                    } else {
                        System.out.println("\nLo sentimos, el sintoma no es valido\n");
                    }
                    opcion = 0;
                } else if (opcion == 2) {
                    guardarRegistro();
                    System.out.println("Se guardo exitosamente el nuevo registro con la fecha: "+ fechaActual);
                    finGuardado = true;
                }
            }catch (Exception e){
            }
        }
    }

    private boolean guardarRegistro(){
        Sintomas sintomasParaGuardar = new Sintomas();
        boolean guardado;
        for(Map.Entry<Sintoma, Boolean> entry : sintomasSeleccionados.entrySet()) {
            if(entry.getValue()){
                sintomasParaGuardar.add(entry.getKey());
            }
        }
        guardado = gestorArchivoRegistros.guardarRegistro(sintomasParaGuardar, fechaHoy);
        limpiarSintomasSeleccionados();
        return guardado;
    }

    private void marcarDescarmar(Sintoma sintoma){
        boolean estado = sintomasSeleccionados.get(sintoma);
        sintomasSeleccionados.replace(sintoma, !estado);
    }

    private void mostrarTiposSintomas(){
        System.out.println("Seleccione un sintoma: ");
        for(Map.Entry<Integer, Sintoma> entry : sintomasMostrar.entrySet()) {
            System.out.println(entry.getKey()+"-"+sintomasSeleccionados.get(entry.getValue())+"-"+entry.getValue());
        }
        System.out.println("--------------------------\n");
    }

    private void leerSintomas(){
        int ite=1;
        for (Sintoma sintoma: sintomas){
            sintomasSeleccionados.put(sintoma, false);
            sintomasMostrar.put(ite, sintoma);
            ite++;
        }
    }

    private void limpiarSintomasSeleccionados(){
        for(Map.Entry<Sintoma, Boolean> entry : sintomasSeleccionados.entrySet()) {
            sintomasSeleccionados.replace(entry.getKey(), false);
        }
    }
}