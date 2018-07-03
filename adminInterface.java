package sdprojeto;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.*;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;


public interface adminInterface extends Remote {
    public void registar(String nome, int telefone, String morada, String numCC, String valCC, String user,String pwd, String dep, String est) throws RemoteException;
    public void criarEleicao(Calendar inicio,Calendar fim,String titulo,String desc,String tipo,CopyOnWriteArrayList<mesaVoto> mesas,CopyOnWriteArrayList<Lista> listas) throws RemoteException, FileNotFoundException, IOException;
    public void adicionarListas(String nomeL,String titulo,String tipo,String tipo2,int num,String[] options) throws RemoteException;
    public boolean removerListas(String nomeL,String titulo) throws RemoteException;
    public boolean criarMesas(String titulo, String dep) throws RemoteException;
    public boolean removerMesas(String dep, String titulo) throws RemoteException;
    public void alterarInicio(String titulo,Calendar inicio) throws RemoteException;
    public void alterarFim(String titulo,Calendar fim) throws RemoteException;
    public void alterarTitulo(String titulo, String novoTitulo) throws RemoteException;
    public void alterarDesc(String titulo, String novaDesc) throws RemoteException;
    public void alterarTipo(String titulo, String novoTipo) throws RemoteException;
    
    
    
    
    
    
    
    public boolean verificaTitulo(String titulo, int i) throws RemoteException;
    public boolean verificaUser(String user) throws RemoteException;
    public CopyOnWriteArrayList<String> apresentarListas(String titulo,String est) throws RemoteException;
    public Calendar getInicioEleicao(String titulo) throws RemoteException;
    public Calendar getFimEleicao(String titulo) throws RemoteException;
    public CopyOnWriteArrayList<String> eleicoesTerminadas() throws RemoteException;
    public String informacaoEleicao(String titulo) throws RemoteException;
    public String resultadosEleicao(String titulo) throws RemoteException;
    public String listaPessoas() throws RemoteException;
    public String listaEleicoes() throws RemoteException;
    public String listaListas() throws RemoteException;
}