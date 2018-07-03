package sdprojeto;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class Lista implements Serializable {
    String nome;
    CopyOnWriteArrayList<String> membros;
    String tipo;
    String tipo2;
    
    Lista(){
        this.nome="";
    }
    
    Lista(CopyOnWriteArrayList<String> membros,String tipo,String nome,String tipo2){
        this.membros=membros;
        this.tipo=tipo;
        this.nome=nome;
        this.tipo2=tipo2;
    }

    public CopyOnWriteArrayList<String> getMembros() {
        return membros;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }
    
    

    public String getTipo() {
        return tipo;
    }

    public void setMembros(CopyOnWriteArrayList<String> membros) {
        this.membros = membros;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getNome(){
        return nome;
    }
}