package monitor;


import cargarregistros.CargarRegistros;
import cargarsintomas.CargarSintomas;
import diagnosticos.DiagnosticoCompleto;
import diagnosticos.Recomendador;

public class Monitor {
    private Fase fase;
    private Sintomas sintomas;
    private Registros registros;
    private FuncionDiagnostico funcion;
    private int resultadoDiagnostico;
    private CargarRegistros cargarRegistros;

    public Monitor() {
        CargarSintomas cargarSintomas = new CargarSintomas();
        sintomas = cargarSintomas.getSintomas();
        funcion = new DiagnosticoCompleto(sintomas);
        registros = new Registros();
        fase = (new DatosFase()).leerDatosFase();
        cargarRegistros = new CargarRegistros(sintomas.getSintomasFase(fase));
    }

    public void monitorear() {
        registros = cargarRegistros.getRegistros();
        resultadoDiagnostico = funcion.diagnostico(registros);
        mostrarDiaFase(resultadoDiagnostico);
    }

    private void mostrarDiaFase(int resultadoDiagnostico){
        fase = (new DatosFase()).leerDatosFase();
        Recomendador recomendador;
        if(resultadoDiagnostico == 3) {
            Fase faseN = new Fase("PrimeraFase", 3);
            faseN.setDia(3);
            recomendador = new Recomendador(faseN);
        }else{
            recomendador = new Recomendador(fase);
        }
        System.out.println(recomendador.recomendacion());
        if(resultadoDiagnostico == 3){
            fase = new Fase("SegundaFase", 4);
            (new DatosFase()).guardarDatosFase(fase);
        }
    }


    public int getResultado() {
        return resultadoDiagnostico;
    }

}

// 0 1 1 1 | dia 3 primera fase ->Pasar segunda Fase
// 0 1 1 1
