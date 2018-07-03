
package sdprojeto;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Connection extends Thread {
    int id;
    BufferedReader in;
    PrintWriter out;
    int numcc;
    String dep,titulo;
    long eTime;
    Socket clientSocket;
    private boolean blocked;
    tcpInterface rmi;
    
    public Connection (Socket aClientSocket,int i,tcpInterface rmi,String dep,String titulo) {
        this.blocked=true;
        this.id=i;
        this.dep=dep;
        this.titulo=titulo;
        this.rmi=rmi;
        try{
            clientSocket = aClientSocket;
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            this.start();
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
    }
    //=============================
    public void run(){
        while(true){
            
                if(this.blocked!=false){
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    out.println("Bloqueado");
                }
                while(this.blocked==false){
                        out.println("Desbloqueado\nPor Favor evite usar espaços");
                        WorkThread worker=new WorkThread(this.in,this.out,this.numcc,this.rmi,this.dep,this.titulo);
                        eTime=System.currentTimeMillis()+(120*1000);
                        while(System.currentTimeMillis()!=eTime && this.blocked==false){
                            
                        }
                        worker.interrupt();
                        if(this.blocked==false){
                            this.blocked=true;
                            worker.blocked=true;
                        }
                    
                }
        }
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    

    
    
}