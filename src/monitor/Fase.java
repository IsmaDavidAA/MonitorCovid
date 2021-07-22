package monitor;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Fase implements Serializable {
    private int duracionDias;
    private String nombre;
    private int dia;
    private Date fechaUltimoRegistro;

    public Fase(String nombre, int duracionDias) {
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

    public void setFechaUltimoRegistro(Date fechaUltimoRegistro){
        this.fechaUltimoRegistro = fechaUltimoRegistro;
    }

    public Date getFechaUltimoRegistro(){
        return fechaUltimoRegistro;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void reiniciar() {
        dia = 0;
    }

    public boolean termino() {
        return dia >= duracionDias;
    }
}
