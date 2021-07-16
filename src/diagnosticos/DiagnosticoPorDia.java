package diagnosticos;

import monitor.*;

import java.util.HashMap;
import java.util.Map;

public class DiagnosticoPorDia extends FuncionDiagnostico {
    private Map<String, Integer> totalPorFases;
    private Fase fase;
    private Sintomas sintomas;
    private DatosFase datosFase;
    public DiagnosticoPorDia(Sintomas sintomas) {
        super(sintomas);
        this.sintomas = sintomas;
        datosFase = new DatosFase();
        totalPorFases = new HashMap<>();
        fase = datosFase.leerDatosFase();
        totalPorFases  = (new ContadorSintomas().sacarTotalPorFase(sintomas));
        examinarFases();
    }

    @Override
    public int diagnostico(Registros registros) {
        int cantSintomas = 0;
        int resultado = examinarFases();
        Registro registro = registros.peek();
        for(Sintoma sintoma:registro.getSintomas()){
            if(sintoma.getClass().getName().contains(fase.getNombre())){
                cantSintomas++;
            }
        }
        if(cantSintomas >= totalPorFases.get(fase.getNombre())/2){
            fase.inc(registro.getFecha());
        }else{
            fase.reiniciar();
        }

        if(fase.alcanzo()){
            fase = new Fase("SegundaFase", 5);
        }
        datosFase.guardarDatosFase(fase);
        return resultado;
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
// 5 ->Primera fase
// >50%? 1:0
// 0 1 1 1
// 4 ->Segunda fase
//          0 1 1 ....
//  p1p2p3  0 1 2 ....