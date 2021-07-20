package cargarsintomas.util;

import monitor.Sintoma;
import monitor.Sintomas;

public class Validador {

    public boolean esValido(String texto, Sintomas sintomas){
        boolean valido = true;
        texto = texto.replaceAll(" ","");
        texto = texto.toUpperCase();

        if(!tieneSoloLetras(texto) || texto.length() < 3){
            valido = false;
        }else{
            for (Sintoma nombreTexto: sintomas) {
                if(nombreTexto.toString().replaceAll(" ", "").equals(texto)){
                    valido = false;
                }
            }
        }
        return valido;
    }

    public String getTextoValidado(String nombreSintoma){
        String nombreSintomaValidado = nombreSintoma.toUpperCase();
        nombreSintomaValidado = nombreSintomaValidado.replaceAll("^\\p{Zs}+|\\p{Zs}+$", "");
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
