package sdprojeto;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Calendar;

public class Eleicao implements Serializable {
    Calendar inicio;
    Calendar fim;
    String titulo;
    String desc;
    CopyOnWriteArrayList<mesaVoto> mesas;
    CopyOnWriteArrayList<Lista> listas;
    CopyOnWriteArrayList<voto> votos;
    String tipo;
    
    Eleicao(Calendar inicio,Calendar fim,String titulo,String desc,String tipo,CopyOnWriteArrayList<mesaVoto> mesas,CopyOnWriteArrayList<Lista> listas){
        this.inicio=inicio;
        this.fim=fim;
        this.titulo=titulo;
        this.desc=desc;
        this.mesas=mesas;
        this.listas=listas;
        this.tipo=tipo;
    }

    public Calendar getInicio() {
        return inicio;
    }
    
    public String getTipo(){
        return tipo;
    }

    public Calendar getFim() {
        return fim;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDesc() {
        return desc;
    }

    public CopyOnWriteArrayList<mesaVoto> getMesas() {
        return mesas;
    }

    public CopyOnWriteArrayList<Lista> getListas() {
        return listas;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public void setFim(Calendar fim) {
        this.fim = fim;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDep(CopyOnWriteArrayList<mesaVoto> mesas) {
        this.mesas = mesas;
    }
    
    public void addListas(Lista c) {
        this.listas.add(c);
    }
    
    public void addMesas(mesaVoto c){
        this.mesas.add(c);
    }

    public CopyOnWriteArrayList<voto> getVotos() {
        return votos;
    }

    public void setVotos(CopyOnWriteArrayList<voto> votos) {
        this.votos = votos;
    }
    
    public void addVotos(voto c){
        this.votos.add(c);
    }
    
    public void removeListas(Lista c){
        this.listas.remove(c);
    }
    
    public void removeMesas(mesaVoto c){
        this.mesas.remove(c);
    }
    
    public void setTipo(String tipo){
        this.tipo=tipo;
    }
    
    public String calendarToString(Calendar data){
        String returnValue;
        returnValue = new String(String.valueOf(data.DAY_OF_MONTH)+"/"+String.valueOf(data.MONTH)+"/"+String.valueOf(data.YEAR)+" "+String.valueOf(data.HOUR_OF_DAY)+":"+String.valueOf(data.MINUTE));
        return returnValue;
    }
}