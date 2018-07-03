package sdprojeto;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class UDPConnection implements Runnable{
    public DatagramSocket socketUDP,socketUDP2;
    int ownPort,otherPort;
    String otherIp;
    boolean serverType;

    public UDPConnection(boolean primary,int ownPort,String otherIp,int otherPort) throws SocketException, UnknownHostException {
        this.otherIp=otherIp;
        this.ownPort=ownPort;
        this.otherPort=otherPort;
        this.serverType=primary;
    }

    @Override
    public void run() {
        if(serverType){
            try {
                DatagramSocket socketUDP2 = new DatagramSocket(ownPort);
                primaryServer(socketUDP2);
                socketUDP2.close();
            } catch (SocketException | UnknownHostException ex) {
            }
        }
        else{
            try{
                DatagramSocket socketUDP = new DatagramSocket(ownPort);
                secondaryServer(socketUDP);
                socketUDP.close();
            }catch(SocketException | UnknownHostException e){
            }
            
        }
    }
    
    public void primaryServer(DatagramSocket socketUDP2) throws SocketException, UnknownHostException{
        byte[] dados = new byte[1024];
        InetAddress ipSecundario = InetAddress.getByName(otherIp);
        while(true){
            try {
                DatagramPacket receivedPacket = new DatagramPacket(dados,dados.length);
                socketUDP2.receive(receivedPacket);
                DatagramPacket sentPacket = new DatagramPacket(dados,dados.length,ipSecundario,otherPort);
                socketUDP2.send(sentPacket);
            }
            catch(IOException e){    
            }
        }
    }
    
    public void secondaryServer(DatagramSocket socketUDP) throws SocketException, UnknownHostException{
        int counter=0;
        byte[] dados = new byte[1024];
        byte[] pingData = new byte[1024];
        InetAddress ipPrimario = InetAddress.getByName(otherIp);
        while(true){
            try {
                socketUDP.setSoTimeout(1500);
                String sentence = "ACK";
                pingData = sentence.getBytes();
                DatagramPacket sentPacket = new DatagramPacket(pingData, pingData.length, ipPrimario, otherPort);
                socketUDP.send(sentPacket);
                DatagramPacket receivedPacket = new DatagramPacket(dados,dados.length);
                socketUDP.receive(receivedPacket);
                System.out.println("Servidor Principal online");
                long inicio = System.nanoTime();
                while(true){
                    long elapsedTime;
                    if((elapsedTime = System.nanoTime() - inicio)>=500000000){
                        break;
                    }
                }
            } catch (IOException ex) {
                counter++;
                System.out.println("Servidor principal falhou "+String.valueOf(counter)+" pings");
                if(counter>=5){
                    break;
                }
            }
        }
    }
}