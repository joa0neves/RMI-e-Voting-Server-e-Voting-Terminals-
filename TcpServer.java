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
import java.rmi.*;
import java.rmi.server.*;
import java.net.*;
import java.util.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public abstract class TcpServer extends UnicastRemoteObject implements tcpInterface{

    public TcpServer () throws RemoteException{
        super();
    }
    @SuppressWarnings("resource")
    public static void main(String[] args) {
        int maxMdv;
        String dep,titulo,cc;
        int i;
        Calendar currentT= Calendar.getInstance();
        Calendar fim,inicio;
        JoinThread conThread;
        ArrayList<String> temp;
        Scanner sc=new Scanner(System.in);
        
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());
        
        try {
            tcpInterface rmi = (tcpInterface) LocateRegistry.getRegistry(args[0],7000).lookup("rmi");
            
            while(true){
                System.out.println("Titulo da eleição");
                titulo=sc.nextLine();
                System.out.println("Departamento:");
                dep=sc.nextLine();
                System.out.println("Número máximo de Terminais:");
                maxMdv=sc.nextInt();
                fim=rmi.getFimEleicao(titulo);
                
                sc.nextLine();

                conThread=new JoinThread(maxMdv,rmi,dep,titulo);
                System.out.println("JoinThread Criada");
                
                if(rmi.adicionarMesas(titulo,dep,maxMdv,conThread.getConns())){
                    System.out.println("Conectado ao RMI");
                    break;
                }else{
                    System.out.println("Não existe uma mesa disponivel");
                }
            }
            
            while(true){
                currentT= Calendar.getInstance();
                if(currentT.after(fim)){
                    System.out.println("A Eleição acabou");
                    break;
                }
                
                System.out.println("Introduza o Numero do Cartão de Cidadão;");
                cc=sc.nextLine();
                //pesquisar o nº do cc na base de dados e caso exista e ainda nÃ£o tenha votado na bd returna um bool = true
                if(!rmi.verificar(cc,titulo)){
                    System.out.println("O Eleitor não existe ou não pode votar nesta mesa de voto");
                } else {
                    conThread.unblockTerminal(Integer.parseInt(cc));
                }
                
                
                
            }
	} catch ( NotBoundException | RemoteException e) {
            System.out.println("Exception in main: " + e);
            e.printStackTrace();
	}
        
        
        
        
    }
}
    

