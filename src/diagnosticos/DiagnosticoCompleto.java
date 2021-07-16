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
        if(fase.getNombre().equals("SegundaFase")){
            contadorDias = 3;
        }
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
