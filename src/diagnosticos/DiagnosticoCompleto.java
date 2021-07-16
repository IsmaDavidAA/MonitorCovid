package diagnosticos;

import monitor.*;

import java.util.Calendar;
import java.util.Date;
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
    }


    @Override
    public int diagnostico(Registros registros) {
        int contadorDias = 0;
        fase.reiniciar();

        for(Registro registro: registros){
            if(esMayor(fase.getNombre(), registro.getSintomas())){
                fase.inc(registro.getFecha());
                contadorDias++;
                if(fase.alcanzo()){
                    fase = new Fase("SegundaFase", 4);
                }
            }else{
                fase.reiniciar();
            }
        }
        System.out.println(contadorDias);
        datosFase.guardarDatosFase(fase);
        return contadorDias;
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
}

// registros()->stack
// 1 2 3 4 5 6 7 8 9 10<-
// -> 1 2 3 4 5 6 7 8 9 10
// Primera fase = 3
//    0 1 0 1 1 1 0 0 1 0 1 0 1
// Segundo fase = 5
//    0 0 0 0 0 0 1 1 1 1 1 0 0