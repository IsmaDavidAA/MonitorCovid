package diagnosticos;

import monitor.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class DiagnosticoCompleto extends FuncionDiagnostico {
    private static Map<String, Integer> totalPorFases;
    private Fase fase;
    private static DatosFase datosFase;
    private static final int DIFFERENCE = 1;

    public DiagnosticoCompleto(Sintomas sintomas) {
        super(sintomas);
        datosFase = new DatosFase();
        fase = new Fase("PrimeraFase", 3);
        totalPorFases = (new ContadorSintomas().sacarTotalPorFase(sintomas));
    }

    @Override
    public int diagnostico(Registros registros) {
        int contadorDias = 0;
        for (Registro registro : registros) {
            if (esMayor(fase.getNombre(), registro.getSintomas())) {
                if (diferenciaEnDias(fase.getFechaUltimoRegistro(), registro.getFecha())) {
                    fase.setDia(fase.getDia() + 1);
                } else {
                    fase.setDia(1);
                }
                fase.setFechaUltimoRegistro(registro.getFecha());
                contadorDias++;
                if (fase.termino() && !fase.getNombre().equals("SegundaFase")) {
                    fase = new Fase("SegundaFase", 4);
                }
            } else {
                fase.reiniciar();
            }
        }
        datosFase.guardarDatosFase(fase);
        return contadorDias;
    }

    private boolean esMayor(String nombreFase, Sintomas sintomas) {
        int contador = 0;
        boolean esMayor = false;
        for (Sintoma sintoma : sintomas) {
            if (sintoma.getClass().getSimpleName().equals(nombreFase)) {
                contador++;
            }
        }
        if (contador >= totalPorFases.get(nombreFase) / 2) {
            esMayor = true;
        }
        return esMayor;
    }

    private boolean diferenciaEnDias(Date f, Date s) {
        boolean resp;
        long dayMilliseconds = 86400000;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(f);
        c2.setTime(s);
        int daysDifference = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
        if (daysDifference == DIFFERENCE) {
            resp = true;
        } else {
            long timeR1 = c1.getTimeInMillis();
            long timeR2 = c2.getTimeInMillis();
            long difference = DIFFERENCE * dayMilliseconds;
            long goodDifference = timeR1 + difference;
            resp = timeR2 <= goodDifference;
        }
        return resp;
    }
}
