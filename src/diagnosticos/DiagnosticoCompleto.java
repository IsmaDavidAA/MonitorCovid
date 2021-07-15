package diagnosticos;

import monitor.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DiagnosticoCompleto extends FuncionDiagnostico {
    private Map<String, Integer> totalPorFases;
    private Fase fase;
    private Sintomas sintomas;
    private DatosFase datosFase;
    public DiagnosticoCompleto(Sintomas sintomas) {
        super(sintomas);
        this.sintomas = sintomas;
        datosFase = new DatosFase();
        fase = datosFase.leerDatosFase();
        totalPorFases  = (new ContadorSintomas().sacarTotalPorFase(sintomas));
        examinarFases();
    }


    @Override
    public int diagnostico(Registros registros) {
        int cantSintomas = 0;
        for(Registro registro: registros){
            if(cantSintomas < 1){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(registro.getFecha());
                calendar.add(Calendar.DATE, -1);
                Date yesterday = calendar.getTime();
                fase.setUltimoRegistro(yesterday);
                cantSintomas++;
                System.out.println(yesterday + "origen");
                break;
            }
        }
        for(Registro registro: registros){
            System.out.println(registro.getFecha()+"--------------------------");
            if(esMayor(fase.getNombre(), registro.getSintomas())){ //si supera el 50%
                fase.inc(registro.getFecha()); //incrementar fase || reiniciar
            }else{
                fase.reiniciar();
            }
            if(fase.terminada()){
                System.out.println("Pasando a segunda fase");
                fase = new Fase("SegundaFase", 4);
            }
        }
//        int resultado = examinarFases();
        datosFase.guardarDatosFase(fase);
        return 0;
    }

    private boolean esMayor(String nombreFase, Sintomas sintomas){
        int contador=0;
        boolean esMayor=false;
        for(Sintoma sintoma: sintomas){
            if(sintoma.getClass().getSimpleName().equals(nombreFase)){
                contador++;
            }
        }
        if(contador >= totalPorFases.get(nombreFase)/2){
            esMayor = true;
        }
        return esMayor;
    }

    private int examinarFases(){
        int estado = 0;
        if(fase.getNombre().equals("PrimeraFase")){
           estado = fase.getDia();
        }else{
            estado = fase.getDuracionDias() + 1;
        }
        return estado;
    }
}

// registros()->stack
// 1 2 3 4 5 6 7 8 9 10<-
// -> 1 2 3 4 5 6 7 8 9 10
// Primera fase = 3
//    0 1 0 1 1 1 0 0 1 0 1 0 1
// Segundo fase = 5
//    0 0 0 0 0 0 1 1 1 1 1 0 0
//
//
//
//
//
