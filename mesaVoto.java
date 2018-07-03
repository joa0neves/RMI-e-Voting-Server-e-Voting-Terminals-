package sdprojeto;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class mesaVoto implements Serializable {
    CopyOnWriteArrayList<Connection> ips;
    String departamento;
    boolean connected;

    public mesaVoto(String departamento) {
        this.departamento = departamento;
        this.connected=false;
    }

    public CopyOnWriteArrayList<Connection> getIps() {
        return ips;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setIps(CopyOnWriteArrayList<Connection> ips) {
        this.ips = ips;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public boolean isConnected() {
        return connected;
    }
    
    
}
