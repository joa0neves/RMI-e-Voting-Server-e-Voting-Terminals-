
package sdprojeto;

import java.rmi.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.CopyOnWriteArrayList;

public interface tcpInterface extends Remote {
    public boolean verificar(String numeroCC,String titulo) throws RemoteException;
    public boolean votar(String tipoEleicao, String nomeLista, String dep, String numeroCC, String tipoVoto) throws RemoteException;
    public boolean adicionarMesas(String titulo, String dep, int mesasNum, CopyOnWriteArrayList<Connection> mesas) throws RemoteException;
    public String login(String user, String pwd) throws RemoteException;
    public CopyOnWriteArrayList<String> apresentarListas(String titulo,String est) throws RemoteException;
    public String getTipo(String titulo) throws RemoteException;
    public Calendar getInicioEleicao(String titulo) throws RemoteException;
    public Calendar getFimEleicao(String titulo) throws RemoteException;
}
