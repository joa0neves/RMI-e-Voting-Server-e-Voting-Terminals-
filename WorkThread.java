
package sdprojeto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class WorkThread extends Thread {
    BufferedReader in;
    PrintWriter out;
    int numcc;
    boolean blocked,loggedIn;
    String resposta,mgs,user,pwd,dep,titulo,est;
    tcpInterface rmi;
    
    public WorkThread(BufferedReader in,PrintWriter out,int cc,tcpInterface rmi,String dep,String titulo){
        this.in=in;
        this.out=out;
        this.dep=dep;
        this.numcc=cc;
        this.titulo=titulo;
        this.loggedIn=false;
        this.blocked=false;
        this.rmi=rmi;
        this.start();
    }
    
    @Override
    public void run() {
        try{
            comunicar();
        }catch(InterruptedException x){
            this.blocked=true;
        }
    }
    
    public void comunicar()throws InterruptedException{
        while(true){
            try {
                while (!Thread.currentThread().isInterrupted() &&this.blocked==false) {
                    resposta=in.readLine();
                    mgs=tratamento(resposta);
                    out.println(mgs); 
                }
                if(this.blocked==true){
                    resposta="";
                }
            }catch (IOException ex) {
                Logger.getLogger(WorkThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String tratamento(String resposta) throws RemoteException{
        String[] temp,temp1,temp2;
        CopyOnWriteArrayList<String> str;
        String user,pwd,ms,r="";
        temp=resposta.split(";");
        for (int i = 0; i < temp.length; i++) {
            System.out.println(temp[i]);
        }
        switch (temp[0]) {
            case "type|login":{
                if(this.loggedIn==false){
                    if(temp.length!=3){
                        return "type:error;mgs|Operação reconhecida, número de argumentos invalida";
                    }
                    else{
                        temp1=temp[1].split("\\|");
                        user=temp1[1];
                        System.out.println(user);
                        temp2=temp[2].split("\\|");
                        pwd=temp2[1];
                        System.out.println(pwd);
                        try{
                            ms=rmi.login(user, pwd);
                            if(!ms.equals("")){
                                this.loggedIn=true;
                                this.est=ms;
                                return "type|status;login|sucess;mgs|Bem-vindo";
                            }
                            else{
                                return "type|status;login|failed;mgs|Credenciais erradas";
                            }
                        }catch(RemoteException e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    return  "type|status;login|failed;mgs|Já foi feito o login..";
                }
                break;}
            case "type|list":{
                if(this.loggedIn){
                    try{
                        str=rmi.apresentarListas(titulo,est);

                        for (int i = 0; i < str.size(); i++) {
                        r=r+str.get(i)+" ";
                    }
                    return "type|status;list|sucess;mgs|"+r;
                    }catch(RemoteException e){
                        e.printStackTrace();}
                }else{
                    return "type|status;list|failed;mgs|Não fez login..";
                }
            }
            case "type|vote":{
                if(this.loggedIn){
                    if(temp.length>2){
                        rmi.votar(rmi.getTipo(titulo),"",this.dep,""+this.numcc,"Nulo");
                        return "type:status;vote|sucess;mgs|Voto Nulo";
                    }
                    if(temp.length==2){
                        if(temp[1].split("\\|").length==2){
                            if(rmi.votar(rmi.getTipo(titulo),temp[1].split("\\|")[1],this.dep,""+this.numcc,"lista")){
                                this.loggedIn=false;
                                this.blocked=false;
                                return "type|status;vote|sucess;mgs|Voto efetuado.Adeus!";
                            }
                        }else{
                            if(rmi.votar(rmi.getTipo(titulo),"",this.dep,""+this.numcc,"branco")){
                                return "type:status;vote|sucess;mgs|Voto Branco";
                            }
                        }
                    }
                    
                }else{
                    return "type|status;vote|failed;mgs|Não fez login..";
                }
                break;
            }
            default:
                return "type|error;mgs|Operação Invalida";
            
        }
        return "";
    }
    
}
