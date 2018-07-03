package sdprojeto;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class RMI extends UnicastRemoteObject implements backupInterface,tcpInterface,adminInterface {
    
    public CopyOnWriteArrayList<Eleicao> Eleicoes = new CopyOnWriteArrayList<>();
    public CopyOnWriteArrayList<Pessoa> Pessoas = new CopyOnWriteArrayList<>();
    public BD bd = new BD();
    static callbackInterface c;
    
    
    public RMI() throws RemoteException{
        super();
    }
    
    public synchronized void atualizaFicheiros(){
        try {
            FileOutputStream fout = new FileOutputStream("BD.bin"); 
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(bd);
            oos.close();
            fout.close();
        }catch(IOException e){
            System.out.println("Exception: "+e);
        }
    }
    
    public synchronized void leFicheiros() throws ClassNotFoundException{
        try {
            FileInputStream fin = new FileInputStream("BD.bin"); 
            ObjectInputStream ois = new ObjectInputStream(fin);
            this.setBD((BD)ois.readObject());
            ois.close();
            fin.close();
        }catch(IOException e){
            System.out.println("Exception: "+e);
        }
    }
    
    public synchronized void setBD(BD bd){
        this.bd=bd;
    }
    
    public synchronized void subscribe(callbackInterface c){
        this.c=c;
    }
    
    @Override
    public synchronized String listaPessoas(){
        String r="";
        for(Pessoa i:Pessoas){
            r=r+"\nNome: "+i.getNome();
            r=r+"\nUsername: "+i.getUser();
            r=r+"\nNúmero de Telefone: "+i.getTelefone();
            r=r+"\nMorada :"+i.getMorada();
            r=r+"\nNúmero de cartão de cidadao:"+i.getNumeroCC();
            r=r+"\nEstatudo: "+i.getEstatuto();
            r=r+"\nDepartamento: "+i.getDep()+"\n";
            r=r+"\n";
            
        }
        return r;
    }
    
    public synchronized void notifications(String titulo,callbackInterface tcp){
        CopyOnWriteArrayList<mesaVoto> mesasTemp = new CopyOnWriteArrayList<>();
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                mesasTemp=eleicoes.getMesas();
                for(mesaVoto mesast : mesasTemp){
                    if(mesast.getDepartamento().equals(tcp.getClass())){
                        
                    }
                }
            }
        }
    }
    
    @Override
    public synchronized String listaEleicoes(){
        String r="";
        for(Eleicao i:Eleicoes){
            r=r+"\nTitulo: "+i.getTitulo();
            r=r+"\nTipo: "+i.getTipo();
            r=r+"\nDescrição: "+i.getDesc();
            r=r+"\n";
        }
        return r;
    }
    
    @Override
    public synchronized String listaListas(){
        String r="";
        for(Eleicao i:Eleicoes){
            r=r+"\nTitulo da Eleição: "+i.getTitulo();
            for(Lista j:i.getListas()){
                r=r+"\nNome Da Lista: "+j.getNome();
                r=r+"\nTipo: "+j.getTipo();
                r=r+"\n";
                for (int k = 0; k < j.getMembros().size(); k++) {
                    r=r+j.getMembros().get(k)+"\n";
                    
                }
            }
        }
        return r;
    }
    
    @Override
    public synchronized void registar(String nome, int telefone, String morada, String numCC, String valCC, String user,String pwd, String dep, String est) throws java.rmi.RemoteException{
        Pessoa eleitor = new Pessoa(nome,telefone,morada,numCC,valCC,user,pwd,dep,est);
        bd.addPessoas(eleitor);
        Pessoas=bd.getPessoas();
        atualizaFicheiros();
        //Escrever eleitor em ficheiro de objectos correspondente.
    }
    
    @Override
    public synchronized void criarEleicao(Calendar inicio,Calendar fim,String titulo,String desc,String tipo,CopyOnWriteArrayList<mesaVoto> mesas,CopyOnWriteArrayList<Lista> listas) throws FileNotFoundException, IOException{
        Eleicao eleicao = new Eleicao(inicio,fim,titulo,desc,tipo,mesas,listas);
        Eleicoes.add(eleicao);
        bd.setEleicoes(Eleicoes);
        atualizaFicheiros();
        //Escrever eleicao em ficheiro de objectos correspondente.
    }
    
    @Override
    public synchronized void adicionarListas(String nomeL,String titulo,String tipo,String tipo2,int num,String[] options){
        Calendar currentT= Calendar.getInstance();
        CopyOnWriteArrayList<String> temp = new CopyOnWriteArrayList<>();
        for (Eleicao eleicoes : Eleicoes) {
            if(currentT.before(eleicoes.getInicio())){
                if(eleicoes.getTitulo().equals(titulo)){
                    for(int i=0;i<num;i++){
                        temp.add(options[i]);
                    }
                    Lista novaLista = new Lista(temp,tipo,nomeL,tipo2);
                    eleicoes.addListas(novaLista);
                    bd.setEleicoes(Eleicoes);
                    atualizaFicheiros();
                    break;
                }
            }
        }
    }
    
    @Override
    public synchronized boolean removerListas(String nomeL,String titulo){
        boolean x=false;
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                CopyOnWriteArrayList<Lista> tempListas = eleicoes.getListas();
                for(Lista temp : tempListas){
                    if(temp.getNome().equals(nomeL)){
                        eleicoes.removeListas(temp);
                        x=true;
                    }
                }
            }
        }
        return x;
    }
    
    @Override
    public synchronized boolean criarMesas(String titulo,String dep){
        boolean done=false;
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                mesaVoto mesa = new mesaVoto(dep);
                eleicoes.addMesas(mesa);
                bd.setEleicoes(Eleicoes);
                atualizaFicheiros();
                done=true;
            }
        }
        return done;
    }
    
    @Override
    
    public synchronized CopyOnWriteArrayList<String> eleicoesTerminadas(){
        Calendar currentT = Calendar.getInstance();
        CopyOnWriteArrayList<String> returnValue = new CopyOnWriteArrayList<>();
        for(Eleicao eleicoes : Eleicoes){
            if(currentT.after(eleicoes.getFim())){
                returnValue.add(eleicoes.getTitulo());
            }
        }
        return returnValue;
    }
    
    @Override
  
    public synchronized String informacaoEleicao(String titulo){
        String returnValue="";
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                returnValue= new String("Inicio da Eleição: "+eleicoes.calendarToString(eleicoes.getInicio())+"\nFim da Eleição: "+eleicoes.calendarToString(eleicoes.getFim())+"\nTitulo: "+eleicoes.getTitulo()+"\nDescrição: "+eleicoes.getDesc()+"\nTipo: "+eleicoes.getTipo());
                break;
            }
        }
        return returnValue;
    }
    
    @Override
    public synchronized String resultadosEleicao(String titulo){
        int votosBrancos=0,votosNulos=0,tempInt;
        boolean escape=false;
        String tempVar,tempVar2;
        Map<String,Integer> votosListas = new HashMap<>();
        String returnValue = "";
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                escape=true;
                for(voto votos : eleicoes.getVotos()){
                    tempVar=votos.getTipo();
                    switch(tempVar){
                        case "branco":
                            votosBrancos++;
                            break;
                        case "nulo":
                            votosNulos++;
                            break;
                        case "lista":
                            tempVar2=votos.getLista().getNome();
                            if(votosListas.containsKey(tempVar2)){
                                tempInt=Integer.valueOf(votosListas.get(tempVar2));
                                tempInt++;
                                votosListas.put(tempVar2,tempInt);
                            }
                            else{
                                votosListas.put(tempVar2,1);
                            }
                            break;
                    }
                }
            }
            if(escape){
                returnValue= "Votos Nulos: "+String.valueOf(votosNulos)+"\nVotos Brancos: "+String.valueOf(votosBrancos)+"\n";
                for(Lista listas :eleicoes.getListas()){
                    returnValue+=listas.getNome()+": "+votosListas.get(listas.getNome()+"\n");
                }
                break;
            }
        }
        return returnValue;
    }
    
    @Override
    public synchronized boolean adicionarMesas(String titulo, String dep, int mesasNum, CopyOnWriteArrayList<Connection> mesas){
        boolean done=false;
        Calendar currentT= Calendar.getInstance();
        CopyOnWriteArrayList<Connection> temp =new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<mesaVoto> mesasTemp = null;
        for(Eleicao eleicoes : Eleicoes){
            if(currentT.before(eleicoes.getInicio())){
                if(eleicoes.getTitulo().equals(titulo)){
                    mesasTemp=eleicoes.getMesas();
                    for(mesaVoto mesast : mesasTemp ){
                        if(mesast.getDepartamento().equals(dep) && !mesast.isConnected()){
                            for(int i=0;i<mesasNum;i++){
                                temp.add(mesas.get(i));
                            }
                            mesaVoto novaMesa = new mesaVoto(dep);
                            novaMesa.setIps(temp);
                            novaMesa.setConnected(true);
                            eleicoes.addMesas(novaMesa);
                            bd.eleicoes=Eleicoes;
                            atualizaFicheiros();
                            done=true;
                        }
                    }
                }
            }
        }
        return done;
    }
    
    public synchronized boolean removerMesas(String dep, String titulo){
        boolean x=false;
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                CopyOnWriteArrayList<mesaVoto> temp = eleicoes.getMesas();
                for(mesaVoto mesasTemp : temp){
                    if(mesasTemp.getDepartamento().equals(dep)){
                        eleicoes.removeMesas(mesasTemp);
                        x=true;
                    }
                }
            }
        }
        return x;
    }
    
    
    @Override
    public synchronized boolean votar(String tipoEleicao, String nomeLista, String dep, String numeroCC, String tipoVoto){
        Calendar currentT= Calendar.getInstance();
        boolean x=false,y=true;
        for(Eleicao eleicoes : Eleicoes){
            CopyOnWriteArrayList<Lista> listas = eleicoes.getListas();
            CopyOnWriteArrayList<mesaVoto> mesas = eleicoes.getMesas();
            for(Lista templista : listas){
                if(templista.getNome().equals(nomeLista)){
                    CopyOnWriteArrayList<voto> votos = eleicoes.getVotos();
                    for(voto votostemp:votos){
                        if(votostemp.getEleitor().getNumeroCC().equals(numeroCC)){
                            y=false;
                        }
                    }
                    if(y){
                        for(Pessoa eleitores : Pessoas){
                            if(eleitores.getNumeroCC().equals(numeroCC) && currentT.before(eleicoes.getFim())){
                                for(mesaVoto mesasV : mesas){
                                    if(mesasV.getDepartamento().equals(dep)){
                                        voto votoN = new voto(tipoVoto,templista,mesasV,eleitores);
                                        eleicoes.addVotos(votoN);
                                        atualizaFicheiros();
                                        x=true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for(voto votos : eleicoes.getVotos()){
                if(votos.getEleitor().getNumeroCC().equals(numeroCC)){
                    y=false;
                }
            }
            if(y && !x && nomeLista.equals("")){
                Lista noLista = new Lista();
                for(mesaVoto mesast : mesas){
                    if(mesast.getDepartamento().equals(dep)){
                        for(Pessoa eleitores : Pessoas){
                            if(eleitores.getNumeroCC().equals(numeroCC)){
                                voto votoN = new voto(tipoVoto,noLista,mesast,eleitores);
                                eleicoes.addVotos(votoN);
                                atualizaFicheiros();
                                x=true;
                            }
                        }
                    }
                }
            }
        
        }
        return x;
    }
    
    @Override
    public synchronized boolean verificar(String numeroCC,String titulo){
        boolean x=false;
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                for(Pessoa eleitores : Pessoas){
                    for(mesaVoto mesasTemp : eleicoes.getMesas()){
                        if(eleicoes.getTipo().equals("nucleo") && eleitores.getDep().equals(mesasTemp.getDepartamento())){
                            x=true;
                        }
                    }
                }
            } 
        }
        return x;
    }
    
    @Override
    public synchronized String login(String user, String pwd){
        String x="";
        for(Pessoa pessoas : Pessoas){
            if(pessoas.getUser().equals(user) && pessoas.getPwd().equals(pwd)){
                x=pessoas.getEstatuto();
            }
        }
        return x;
    }
    // date -> [ano,mes,dia,hora,minutos]
    
    @Override
    public synchronized String getTipo(String titulo){
        String returnValue="";
        for(Eleicao eleicoes: Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                returnValue=eleicoes.getTipo();
            }
        }
        return returnValue;
    }
    
    @Override
    public synchronized void alterarInicio(String titulo,Calendar inicio){
        Calendar currentT = Calendar.getInstance();
        for(Eleicao eleicoes : Eleicoes){
            if(currentT.before(eleicoes.getInicio())){
                eleicoes.setInicio(inicio);
            }
        }
    }
    
    @Override
    public synchronized void alterarFim(String titulo,Calendar fim){
        Calendar currentT = Calendar.getInstance();
        for(Eleicao eleicoes : Eleicoes){
            if(currentT.before(eleicoes.getInicio())){
                eleicoes.setFim(fim);
            }
        }
    }
    
    @Override
    public synchronized void alterarTitulo(String titulo, String novoTitulo){
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                eleicoes.setTitulo(novoTitulo);
            }
        }
    }
    
    @Override
    public synchronized void alterarDesc(String titulo, String novaDesc){
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                eleicoes.setDesc(novaDesc);
            }
        }
    }
    
    @Override
    public synchronized void alterarTipo(String titulo, String novoTipo){
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                eleicoes.setTipo(novoTipo);
            }
        }
    }
    
    //POR NA INTERFACE AS FUNÇÔES A BAIXO
    
    @Override
    public synchronized boolean verificaTitulo(String titulo, int i){
        boolean x=false;
        switch(i){
            case 1:
                for(Eleicao eleicoes : Eleicoes){
                    if(eleicoes.getTitulo().equals(titulo)){
                        x=true;
                    }
                }
                break;
            case 2:
                for(Eleicao eleicoes : Eleicoes){
                    CopyOnWriteArrayList<Lista> listasT = eleicoes.getListas();
                    for(Lista listas : listasT){
                        if(listas.getNome().equals(titulo)){
                            x=true;
                        }
                    }
                }
        }
        return x;
    }
    
    @Override
    public synchronized boolean verificaUser(String user){
        boolean x=false;
        for(Pessoa eleitores : Pessoas){
            if(eleitores.getUser().equals(user)){
                x=true;
            }
        }
        return x;
    }
    
    @Override
    public synchronized CopyOnWriteArrayList<String> apresentarListas(String titulo,String est){
        CopyOnWriteArrayList<String> listas = new CopyOnWriteArrayList<>();
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                if(eleicoes.getTipo().equals("geral")){
                    CopyOnWriteArrayList<Lista> listas2 = eleicoes.getListas();
                    for(Lista l:listas2){
                        listas.add(l.getNome());
                    }
                }else if(eleicoes.getTipo().equals("nucleo")){
                    CopyOnWriteArrayList<Lista> listas2 = eleicoes.getListas();
                    for(Lista l:listas2){
                        if(l.getTipo2().equals(est))
                            listas.add(l.getNome());
                    }
                }
            }
            /*CopyOnWriteArrayList<mesaVoto> mesas = eleicoes.getMesas();
            for(mesaVoto mesasT : mesas){
                if(mesasT.getDepartamento().equals(dep)){
                    CopyOnWriteArrayList<Lista> listasT = eleicoes.getListas();
                    for(Lista listasT2 : listasT){
                        listas.add(listasT2.getNome());
                    }
                }
            }*/
        }
        return listas;
    }
    
    @Override
    public synchronized Calendar getInicioEleicao(String titulo){
        Calendar inicio = Calendar.getInstance();
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                inicio = eleicoes.getInicio();
            }
        }
        return inicio;
    }
    
    @Override
    public synchronized Calendar getFimEleicao(String titulo){
        Calendar fim = Calendar.getInstance();
        for(Eleicao eleicoes : Eleicoes){
            if(eleicoes.getTitulo().equals(titulo)){
                fim = eleicoes.getFim();
            }
        }
        return fim;
    }
    
    
    @Override
    public synchronized void ping(){}
    
    
    
    //Main
    public static void main(String args[]) throws ServerNotActiveException, IOException, ClassNotFoundException {
        
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());
        
        boolean primary=true;
        
        try {
            backupInterface rmi = (backupInterface) LocateRegistry.getRegistry(args[1],7000).lookup("rmi");
            primary=false;
            UDPConnection pingProbe = new UDPConnection(primary,Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]));
            while(true){   
                pingProbe.run();
                rmi.ping();
            }
	} catch (NotBoundException | ConnectException e) {
            System.out.println("Connecting...");
            try {
                primary=true;
                UDPConnection pingProbe = new UDPConnection(primary,Integer.parseInt(args[0]),args[1],Integer.parseInt(args[2]));
                RMI h = new RMI();
                Registry r = LocateRegistry.createRegistry(7000);
                r.rebind("rmi", h);
                h.leFicheiros();
                System.out.println("Primary RMI Server online");
                pingProbe.run();
            }catch (RemoteException re) {
                System.out.println("Exception in Rmi Server: " + re);
            }
	}
    }
}
