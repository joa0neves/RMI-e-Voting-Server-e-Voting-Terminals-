package sdprojeto;

import java.io.Serializable;

public class voto implements Serializable {
    String tipo;
    Lista lista;
    mesaVoto mesa;
    Pessoa eleitor;

    public voto(String tipo, Lista lista, mesaVoto mesa, Pessoa eleitor) {
        this.tipo = tipo;
        this.lista = lista;
        this.mesa = mesa;
        this.eleitor = eleitor;
    }

    public String getTipo() {
        return tipo;
    }

    public Lista getLista() {
        return lista;
    }

    public mesaVoto getMesa() {
        return mesa;
    }

    public Pessoa getEleitor() {
        return eleitor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public void setMesa(mesaVoto mesa) {
        this.mesa = mesa;
    }

    public void setEleitor(Pessoa eleitor) {
        this.eleitor = eleitor;
    }
    
}
