package diagnosticos;

import monitor.Registro;
import monitor.Sintoma;
import monitor.Sintomas;

import java.util.*;

public class Periodos {
    private Map<String, Periodo> periodos;
    public Periodos(){
        periodos=new HashMap<>();
    }
    public void addPeriodo(String nombrePeriodo, Periodo periodo){
        periodos.put(nombrePeriodo, periodo);
    }

    public void addRegistro(Registro registro){
        descomponerRegistro(registro.getSintomas(), registro.getFecha());
    }
    private void descomponerRegistro(Sintomas sintomas, Date fecha){
        Map<String, Registro> nuevosRegistros = crearRegistrosPorPeriodo(fecha);
        for(Sintoma sintoma: sintomas){
            String nombreClass =  sintoma.getClass().getName();
            nombreClass = nombreClass.substring(nombreClass.indexOf(".")+1);
            nuevosRegistros.get(nombreClass).getSintomas().add(sintoma);
        }
        agregarRegistros(nuevosRegistros);
    }

    private Map<String, Registro> crearRegistrosPorPeriodo(Date fecha){
        Map<String, Registro> nuevosRegistros = new HashMap<>();
        for(Map.Entry<String, Periodo> entry : periodos.entrySet()){
            nuevosRegistros.put(entry.getKey(), new Registro(fecha, new Sintomas()));
        }
        return nuevosRegistros;
    }

    private void agregarRegistros(Map<String, Registro> nuevosRegistros){
        for(Map.Entry<String, Registro> entry : nuevosRegistros.entrySet()){
            periodos.get(entry.getKey()).addRegistro(entry.getValue());
        }
    }

    public int evaluar(){
        int dia=0;
        boolean continuo=true;
        Stack<Stack<Boolean>> estadoPeriodos = new Stack<>();
        for(Map.Entry<String, Periodo> entry : periodos.entrySet()){
            estadoPeriodos.add(periodos.get(entry.getKey()).getEstadoRegistroPorFecha());
            Stack<Boolean> estado = new Stack<>();
        }
        return dia;
    }
}
