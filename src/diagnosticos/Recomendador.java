package diagnosticos;

import monitor.Fase;

import java.util.HashMap;

public class Recomendador {
    private Fase fase;
    private HashMap<String,String[]> recomendaciones;
    public Recomendador(Fase fase){
        this.fase = fase;
        recomendaciones = new HashMap<>();
        recomendaciones.put("PrimeraFase", new String[]{"Usted se encuentra en buen estado",
                "Puede que este incubando la enfermedad","Usted tiene demasiados sintomas",
                "Se le recomienda ir al medico"});
        recomendaciones.put("SegundaFase", new String[]{"Usted esta entrando a una etapa critica","Acudir al hospital ya mismo o se puede morir!",
                "Advertencia! no puede esperar mas, debe ir al hospital", "Llame a la ambulancia!!!"});
    }

    public String recomendacion(){
        String mensaje = "---------------------------------------------------- \n";
        mensaje = mensaje + "Se encuentra en el dia "+ fase.getDia() + " de la " + fase.getNombre()+"\n";
        mensaje = mensaje + recomendaciones.get(fase.getNombre())[fase.getDia()] + "\n";
        mensaje = mensaje + "Mantenga distancia, cuidese a usted y a los demas \n";
        mensaje = mensaje +"---------------------------------------------------- \n";
        return mensaje;
    }


}
