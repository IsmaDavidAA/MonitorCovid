package diagnosticos;

import monitor.Fase;

import java.util.List;

public class Recomendaciones {
    private List<Fase> fases;
    private String[] recomendaciones;
    public Recomendaciones(){
        this.fases = null;
        recomendaciones = new String[]{"Esta usted bien","Puede que este incubamdo la enfermedad",
                "Usted esta enfermando", "Se le sugiere acudir al hospital","Debe ir con urgencia al hospital"};
    }

    public String generarRecomendacion(int riesgo){
        String mensaje = "Riesgo extremo de muerte";
        if(recomendaciones.length > riesgo){
            mensaje = recomendaciones[riesgo];
        }
        return mensaje;
    }
}
