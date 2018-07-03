package sdprojeto;

import java.io.Serializable;

public class Pessoa implements Serializable {
    String Nome;
    String user;
    int Telefone;
    String Morada;
    String numeroCC;
    String validadeCC;
    String Pwd;
    String Dep;
    String Estatuto;
    
    Pessoa(String nome,int Telefone, String Morada, String numeroCC, String validadeCC,String user, String Pwd, String Dep,String Estatuto){
        this.Nome=nome;
        this.Telefone=Telefone;
        this.Morada=Morada;
        this.numeroCC=numeroCC;
        this.validadeCC=validadeCC;
        this.Pwd=Pwd;
        this.Dep=Dep;
        this.Estatuto=Estatuto;
        this.user=user;
    }

    public String getNome() {
        return Nome;
    }

    public int getTelefone() {
        return Telefone;
    }

    public String getMorada() {
        return Morada;
    }

    public String getNumeroCC() {
        return numeroCC;
    }

    public String getValidadeCC() {
        return validadeCC;
    }

    public String getPwd() {
        return Pwd;
    }

    public String getDep() {
        return Dep;
    }

    public String getEstatuto() {
        return Estatuto;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public void setTelefone(int Telefone) {
        this.Telefone = Telefone;
    }

    public void setMorada(String Morada) {
        this.Morada = Morada;
    }

    public void setNumeroCC(String numeroCC) {
        this.numeroCC = numeroCC;
    }

    public void setValidadeCC(String validadeCC) {
        this.validadeCC = validadeCC;
    }

    public void setPwd(String Pwd) {
        this.Pwd = Pwd;
    }

    public void setDep(String Dep) {
        this.Dep = Dep;
    }

    public void setEstatuto(String Estatuto) {
        this.Estatuto = Estatuto;
    }
    
    public String getUser(){
        return user;
    }
}