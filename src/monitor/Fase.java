package monitor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Fase implements Serializable {
    private int duracionDias;
    private String nombre;
    private int dia;
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
        if(diaAnterior(date)){
                dia++;
                fechaUltimoRegistro = date;
        }else{
            fechaUltimoRegistro = date;
                reiniciar();

        }
    }

    public void setUltimoRegistro(Date date){
        fechaUltimoRegistro = date;
    }
    public void reiniciar(){
        dia = 0;
    }
    public boolean terminada(){
        return dia >= duracionDias;
    }
    private boolean diaAnterior(Date hoyDate) {
        boolean res = false;
        Calendar hoy = Calendar.getInstance();
        hoy.setTime(hoyDate);
        Calendar ayer = Calendar.getInstance();
        ayer.setTime(fechaUltimoRegistro);
        hoy.add(Calendar.DATE, -1);
        if (hoy.get(Calendar.DAY_OF_MONTH) == ayer.get(Calendar.DAY_OF_MONTH)) {
            res = true;
        }
    return true;
}
}
