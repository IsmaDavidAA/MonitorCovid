package diagnosticos;

import monitor.Sintoma;
import monitor.Sintomas;

import java.util.HashMap;

public class ContadorSintomas {


    public HashMap<String, Integer> sacarTotalPorFase(Sintomas sintomas){
        HashMap<String, Integer> totalPorFases = new HashMap<String, Integer>();
        for (Sintoma s: sintomas) {
            String nombreClass = s.getClass().getName();
            nombreClass = nombreClass.substring(nombreClass.indexOf(".")+1);
            if(totalPorFases.containsKey(nombreClass)) {
                totalPorFases.replace(nombreClass, totalPorFases.get(nombreClass)+1);
            }else{
                totalPorFases.put(nombreClass, 1);
            }
        }
        return totalPorFases;
    }
}
