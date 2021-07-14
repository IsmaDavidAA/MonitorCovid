package monitor;

import java.io.Serializable;
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
        fechaUltimoRegistro = null;
    }

    public Fase(String nombre){
        this.nombre = nombre;
        this.duracionDias = duracionDias;
        dia = 0;
        fechaUltimoRegistro = null;
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
    private boolean diaAnterior(Date actual){
        return true;
    }
}
