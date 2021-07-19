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
        recomendaciones.put("SegundaFase", new String[]{"Puede que usted este entrando a una etapa critica", "Usted esta en una etapa critica","Acudir al hospital ya mismo!",
                "Advertencia! no puede esperar mas, debe consultar en el hospital", "Llame a la ambulancia y dirigace a emergencias!!!"});
    }

    public String recomendacion(){
        String recomendacion = "";
        if(fase.getDia() >= recomendaciones.get(fase.getNombre()).length){
            recomendacion = " ALTO RIESGO!! ESTAMOS LLAMANDO A UNA AMBULANCIA POR USTED";
        }else{
            recomendacion = recomendaciones.get(fase.getNombre())[fase.getDia()];
        }
        String mensaje = "---------------------------------------------------- \n";
        mensaje = mensaje + "| Se encuentra en el dia "+ fase.getDia() + " de la " + fase.getNombre()+"\n";
        mensaje = mensaje + "| "+recomendacion + "\n";
        mensaje = mensaje + "| \n| "+"Mantenga distancia, cuidese a usted y a los demas \n";
        mensaje = mensaje +"| ---------------------------------------------------- \n";
        return mensaje;
    }


}
