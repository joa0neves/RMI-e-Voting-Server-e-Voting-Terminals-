package sdprojeto;

import java.rmi.*;
import java.rmi.RemoteException;

public interface backupInterface extends Remote {
    public void ping() throws RemoteException;
}
