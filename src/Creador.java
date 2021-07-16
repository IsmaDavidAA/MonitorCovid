import cargarregistros.GestorArchivoRegistros;
import monitor.*;
import sintomas.PrimeraFase;
import sintomas.SegundaFase;

import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Creador {
    static Sintoma[] sintomasFase1 = new Sintoma[]{
            new PrimeraFase("dolor corporal")
            ,new PrimeraFase("dolor de ojos")
            ,new PrimeraFase("dolor de cabeza")
            ,new PrimeraFase("Vomitos")
            ,new PrimeraFase("diarrrea")
            ,new PrimeraFase("secrecion nasal")
            ,new PrimeraFase("sentirse febril")
            ,new PrimeraFase("Decaimiento")
            ,new PrimeraFase("Ojos ardientes")
            ,new PrimeraFase("sentirse febril")
            ,new PrimeraFase("garganta raspada")
    };

    static Sintoma[] sintomasFase2 = new Sintoma[]{
            new SegundaFase("Perdida del gusto")
            ,new SegundaFase("Dolor en el pecho")
            ,new SegundaFase("Tos seca")
            ,new SegundaFase("Alta temperatura")
            ,new SegundaFase("Cansancio a los miimos esfuerzos")
            ,new SegundaFase("Dolor de espalda baja")
            ,new SegundaFase("sequedad en la garganta")
            ,new SegundaFase("picazon de garganta")
    };


    public void llenador(){

//        Sintomas sintomasFase2 = llenaDia2Fase(5);

        Calendar myCalendar = new GregorianCalendar(2021, 6, 15, 17, 30);
        Date dr1 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2021, 6, 14, 16, 10);
        Date dr2 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2021, 6, 13, 16, 10);
        Date dr3 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2021, 6, 12, 16, 10);
        Date dr4 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2021, 6, 11, 16, 10);
        Date dr5 = myCalendar.getTime();

        myCalendar = new GregorianCalendar(2021, 6, 15, 13, 10);
        Date ds1 = myCalendar.getTime();
        myCalendar = new GregorianCalendar(2021, 6, 14, 10, 10);
        Date ds2 = myCalendar.getTime();

        Registro registroD1 = new Registro(dr1, llenaDia1Fase(8));
        Registro registroD2 = new Registro(dr2, llenaDia1Fase(7));
        Registro registroD3 = new Registro(dr3, llenaDia1Fase(8));
        Registro registroD4 = new Registro(dr4, llenaDia1Fase(7));
        Registro registroD5 = new Registro(dr5, llenaDia1Fase(8));


        Registro registroS1 = new Registro(ds1, llenaDia2Fase(5));
        Registro registroS2 = new Registro(ds2, llenaDia2Fase(5));



        Registros registros = new Registros();
        //CASO 1
//        registros.push(registroD1);
        //CASO 2
        registros.push(registroD2);
        registros.push(registroD1);
        //CASO 3
//        registros.push(registroD5);
//        registros.push(registroD4);
//        registros.push(registroD3);
//        registros.push(registroS2);
//        registros.push(registroS1);



        funcionParaSerializar(registros, "");
    }

    public Sintomas llenaDia1Fase(int sint){
        Sintomas sintomas= new Sintomas();
        ArrayList<Integer> visitados = new ArrayList<>();
        for(int i=0; i <= sint;i++){
            int indice = (int) (Math.random()*11);
            if(!visitados.contains(indice)){
                visitados.add(indice);
                sintomas.add(sintomasFase1[indice]);
            }else{
                i--;
            }
        }
        return sintomas;
    }
    public Sintomas llenaDia2Fase(int sint){
        Sintomas sintomas= new Sintomas();
        ArrayList<Integer> visitados = new ArrayList<>();
        for(int i=0; i <= sint;i++){
            int indice = (int) (Math.random()*8);
            if(!visitados.contains(indice)){
                visitados.add(indice);
                sintomas.add(sintomasFase2[indice]);
            }else{
                i--;
            }
        }
        return sintomas;
    }


    public void funcionParaSerializar(Registros registros, String fase){
        //USAR SU PRIPIO GESTOR
        GestorArchivoRegistros gestorArchivoRegistros = new GestorArchivoRegistros();
        //CREAR REGISTRO
        gestorArchivoRegistros.verificarExistenciaArchivo();

        if(fase == "SegundaFase") {
            DatosFase siguiente = new DatosFase();
            siguiente.guardarDatosFase(new Fase("SegundaFase", 4));
        }
        for(Registro registro: registros) {
            //CAMBIAR AQUI
            gestorArchivoRegistros.guardarRegistro(registro.getSintomas(), registro.getFecha());

        }
    }

}
