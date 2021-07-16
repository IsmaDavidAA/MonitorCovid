package monitor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Fase implements Serializable {
    private int duracionDias;
    private String nombre;
    private int dia;
    private int DIFFERENCE = 1;
    private Date fechaUltimoRegistro;
    public Fase(String nombre, int duracionDias){
        this.nombre = nombre;
        this.duracionDias = duracionDias;
        dia = 0;
        fechaUltimoRegistro = new Date();
    }

    public Fase(String nombre){
        this.nombre = nombre;
        this.duracionDias = duracionDias;
        dia = 0;
        fechaUltimoRegistro = new Date();
    }

    public String getNombre() {
        return nombre;
    }

    public int getDia() {
        return dia;
    }
    public int getDuracionDias(){
        return duracionDias;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void inc(Date date){
        if(differenceDays(fechaUltimoRegistro,date)){
                dia++;
                fechaUltimoRegistro = date;
        }else{
            dia=1;
            fechaUltimoRegistro = date;
        }
    }

    public void setUltimoRegistro(Date date){
        fechaUltimoRegistro = date;
    }
    public void reiniciar(){
        dia = 0;
    }
    public boolean alcanzo(){
        return dia >= duracionDias;
    }
    private boolean differenceDays(Date f, Date s) {
        boolean resp;
        long dayMilliseconds = 86400000;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(f);
        c2.setTime(s);
        int daysDifference = c2.get(Calendar.DAY_OF_MONTH) - c1.get(Calendar.DAY_OF_MONTH);
        if(daysDifference == DIFFERENCE) {
            resp = true;
        } else {
            long timeR1 = c1.getTimeInMillis();
            long timeR2 = c2.getTimeInMillis();
            long difference = DIFFERENCE * dayMilliseconds;
            long goodDifference = timeR1 + difference;
            resp = timeR2 <= goodDifference;
        }
        return true; // for test development
// return resp for production
    }
}
