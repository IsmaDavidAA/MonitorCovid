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
        Recomendador recomendador = new Recomendador(fase);
        System.out.println(recomendador.recomendacion());
        if(fase.terminada()){
            fase = new Fase("SegundaFase", 4);
            (new DatosFase()).guardarDatosFase(fase);
        }
    }


    public int getResultado() {
        return resultadoDiagnostico;
    }

}

// <PrimeraFase, List[usted esta sano,  puede que este incubando, usted esta empeorando]>
// <SegundaFase, List[usted esta sano,  puede que este incubando, usted esta empeorando]>
// 11 12 13 ->
// 21 22 23
//