package diagnosticos;

import monitor.Registro;
import monitor.Registros;
import monitor.Sintoma;

import java.util.*;

public class Periodo{
    private int dias;
    private int cantidadSintomas;
    private Set<Registro> registros;
    public Periodo(int dias, int cantidadSintomas){
        this.dias = dias;
        this.cantidadSintomas = cantidadSintomas;
        registros = new HashSet<>();
    }

    public void addRegistro(Registro registro){
        registros.add(registro);
    }

    public Stack<Boolean> getEstadoRegistroPorFecha(){
        Stack<Boolean> estadoRegistros = new Stack<>();
        for(Registro registro: registros){
            int i=0;
            for(Sintoma sintoma:registro.getSintomas()){
                i++;
            }
            if(i > cantidadSintomas){
                estadoRegistros.add(true);
            }else{
                estadoRegistros.add(false);
            }
        }
        return estadoRegistros;
    }
}
