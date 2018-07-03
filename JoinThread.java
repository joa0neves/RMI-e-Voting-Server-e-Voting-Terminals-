/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdprojeto;

/**
 *
 * @author João
 */
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public class JoinThread extends Thread {
    int maxMdv;
    private CopyOnWriteArrayList<Connection> conns;
    String dep,titulo;
    Thread t;
    tcpInterface rmi;
    
    JoinThread(int n,tcpInterface rmi,String dep,String titulo) {
        this.maxMdv=n;
        this.dep=dep;
        this.titulo=titulo;
        this.conns=new CopyOnWriteArrayList<>();
        this.rmi=rmi;
        this.start(); // Start the thread
    }
    public void run() {
        int numero=0;
        try{
            int serverPort = 6000;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            //System.out.println("LISTEN SOCKET="+listenSocket);
            while(true){
                while(numero!=maxMdv) {
                    Socket clientSocket; 
                    clientSocket = listenSocket.accept();   //BLOQUEANTE
                    //System.out.println("CLIENT_SOCKET (created at accept())="+clientSocket);
                    Connection newCon=new Connection(clientSocket,numero,this.rmi,this.dep,this.titulo);
                    this.conns.add(newCon);
                    numero ++;
                    System.out.println("Coneção "+numero+" adicionada");
                }
            }
        }catch(IOException e)
        {/*System.out.println("Listen:" + e.getMessage());*/}
    }

    public void unblockTerminal(int cc){
        for (int i = 0; i < this.maxMdv; i++) {
            if(this.conns.get(i).isBlocked()==true){
                this.conns.get(i).setBlocked(false);
                this.conns.get(i).numcc=cc;
                System.out.println("Terminal Desbloqueado");
                break;
            } 
        }
    }

    public CopyOnWriteArrayList<Connection> getConns() {
        return conns;
    }
    
    
}
