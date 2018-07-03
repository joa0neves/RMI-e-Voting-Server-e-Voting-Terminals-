package sdprojeto;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;


public class BD implements Serializable {
    CopyOnWriteArrayList<Pessoa> pessoas;
    CopyOnWriteArrayList<Eleicao> eleicoes;
    
    public BD(){
        this.pessoas= new CopyOnWriteArrayList<>();
        this.eleicoes = new CopyOnWriteArrayList<>();
    }

    public BD(CopyOnWriteArrayList<Pessoa> pessoas, CopyOnWriteArrayList<Eleicao> eleicoes) {
        this.pessoas = pessoas;
        this.eleicoes = eleicoes;
    }

    public CopyOnWriteArrayList<Pessoa> getPessoas() {
        return pessoas;
    }

    public CopyOnWriteArrayList<Eleicao> getEleicoes() {
        return eleicoes;
    }

    public void setPessoas(CopyOnWriteArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

    public void setEleicoes(CopyOnWriteArrayList<Eleicao> eleicoes) {
        this.eleicoes = eleicoes;
    }
    
    public void addPessoas(Pessoa c){
        this.pessoas.add(c);
    }
    
    public void addEleicoes(Eleicao c){
        this.eleicoes.add(c);
    }
    
}

