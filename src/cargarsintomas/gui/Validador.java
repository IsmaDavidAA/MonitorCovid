package cargarsintomas.gui;

import cargarsintomas.GestorArchivoSintomas;

import java.util.Locale;
import java.util.Set;

public class Validador {
    GestorArchivoSintomas gestorArchivoSintomas;
    public Validador(){
        gestorArchivoSintomas = new GestorArchivoSintomas();
    }

    public boolean validar(String texto){
        boolean valido = true;

        Set<String> listaTexto = gestorArchivoSintomas.getNombresSintomas();
        texto = texto.replaceAll(" ","");
        texto = texto.toUpperCase();

        if(!tieneSoloLetras(texto) || texto.length() < 3){
            valido = false;
        }else{
            for (String nombreTexto: listaTexto) {
                if(nombreTexto.replaceAll(" ", "").equals(texto)){
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
