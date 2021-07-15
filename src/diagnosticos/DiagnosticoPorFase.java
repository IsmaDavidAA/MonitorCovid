package diagnosticos;

        import diagnosticos.gui.DiagnosticoGUI;
        import monitor.*;

        import java.util.HashMap;
        import java.util.Map;

public class DiagnosticoPorFase extends FuncionDiagnostico {
    private Map<String, Integer> totalPorFases;
    private Fase fase;
    private Sintomas sintomas;
    private DatosFase datosFase;
    public DiagnosticoPorFase(Sintomas sintomas) {
        super(sintomas);
        this.sintomas = sintomas;
        datosFase = new DatosFase();
        totalPorFases = new HashMap<>();
        fase = datosFase.leerDatosFase();
        sacarTotalPorFase(sintomas);
        examinarFases();
    }

    @Override
    public int diagnostico(Registros registros) {
        int cantSintomas = 0;
        int resultado = examinarFases();
        Registro registro = registros.pop();
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
        if(fase.terminada()){
            fase = new Fase("SegundaFase", 5);
        }
        datosFase.guardarDatosFase(fase);
        return resultado;
    }

    @Override
    public void mostrarDiagnostico(int resultadoDiagnostico) {
        new DiagnosticoGUI(resultadoDiagnostico);
    }

    private void sacarTotalPorFase(Sintomas sintomas){
        for (Sintoma s: sintomas) {
            String nombreClass = s.getClass().getName();
            nombreClass = nombreClass.substring(nombreClass.indexOf(".")+1);
            if(totalPorFases.containsKey(nombreClass)) {
                totalPorFases.replace(nombreClass, totalPorFases.get(nombreClass)+1);
            }else{
                totalPorFases.put(nombreClass, 1);
            }
        }
    }

    private Sintomas filtrarPorFase(Sintomas sintomas){
        Sintomas sintomasPorEtapa = new Sintomas();
        for(Sintoma sintoma: sintomas){
            if(sintoma.getClass().getName().contains(fase.getNombre())){
                sintomasPorEtapa.add(sintoma);
            }
        }
        return sintomasPorEtapa;
    }

    public Sintomas getSintomasFase(){
        return filtrarPorFase(sintomas);
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







//DiagParcial - DiagDiario
//registros.dat -> fase.dat
//
//1->Fase:1
//2->Fase:2
//
//
//--------------------------------------
//DiagCompleto - DiagTotal
//registros.dat *> fase.dat
//[x,x,x,x] -> resultado(Fase:x)









