package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;
import monitor.Sintoma;
import monitor.Sintomas;

import java.util.Set;

public class Validador {
    GestorArchivoSintomas gestorArchivoSintomas;
    public Validador(){
        gestorArchivoSintomas = new GestorArchivoSintomas();
    }

    public boolean validar(String texto){
        boolean valido = true;

        Sintomas listaTexto = gestorArchivoSintomas.getSintomasArchivo();
        texto = texto.replaceAll(" ","");
        texto = texto.toUpperCase();

        if(!tieneSoloLetras(texto) || texto.length() < 3){
            valido = false;
        }else{
            for (Sintoma nombreTexto: listaTexto) {
                if(nombreTexto.toString().replaceAll(" ", "").equals(texto)){
                    valido = false;
                }
            }
        }
        return valido;
    }

    public String getValidado(String nombreSintoma){
        String nombreSintomaValidado = nombreSintoma.toUpperCase();
        nombreSintomaValidado.replaceAll("^\\p{Zs}+|\\p{Zs}+$", "");
        return nombreSintomaValidado;
    }

    private boolean tieneSoloLetras(String cadena) {
        boolean tieneSoloLetras = true;
        int i=0;
        char c;
        while(tieneSoloLetras && i <cadena.length()){
            c = cadena.charAt(i);
            if (!((c >= 'A' && c <= 'Z') || c == ' ')) {
                tieneSoloLetras = false;
            }
            i++;
        }
        return tieneSoloLetras;
    }

}
