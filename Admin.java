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
//import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class Admin implements adminInterface {

    public static void main(String[] args) throws IOException {
        int in,in2,in3,d,m,a,min,h,telefone;
        String nome,morada,numcc,valcc,user,pwd,dep,est,tipo,tipo2,desc,titulo;
        Calendar inicio,fim;
        String[] nomes;
        Scanner sc=new Scanner(System.in);
        System.getProperties().put("java.security.policy", "policy.all");
        System.setSecurityManager(new RMISecurityManager());
        
	try {

            adminInterface rmi = (adminInterface) LocateRegistry.getRegistry(args[0],7000).lookup("rmi");
            
            while(true){
            System.out.println("1-Criar\n2-Eliminar\n3-Listar\n4-Editar\n0-Sair");
            in=sc.nextInt();
            switch (in){
                case 1:{
                    System.out.println("Vamos Criar:\n1-Pessoas\n2-Eleições\n3-Lista\n4-Mesa");
                    in2=sc.nextInt();
                    switch (in2){
                        case 1:{
                            sc.nextLine();
                            System.out.println("Nome:");
                            nome=sc.nextLine();
                            do{
                                System.out.println("Número de Telefone:");
                                telefone=sc.nextInt();
                                if(telefone<9999999 && telefone>1000000000){
                                    System.out.println("Número de Telefone Inválido");
                                }
                            }while(telefone<9999999 && telefone>1000000000);
                            sc.nextLine();
                            System.out.println("Morada:");
                            morada=sc.nextLine();
                            System.out.println("Numero de Cartão de cidadão:");
                            numcc=sc.nextLine();
                            System.out.println("Validade do cartão de cidadão(d/m/a)");
                            do {                                
                                System.out.println("Dia:");
                                d=sc.nextInt();
                                if(d<0 || d>31){
                                    System.out.println("Dia Invalido.");
                                }
                            } while (d<0 || d>31);
                            do {                                
                                System.out.println("Mês:");
                                m=sc.nextInt();
                                if(m<0 || m>12){
                                    System.out.println("Mês Invalido.");
                                }
                            } while (m<0 || m>12);
                            System.out.println("Ano:");
                            a=sc.nextInt();
                            valcc=d+"/"+m+"/"+a;
                            sc.nextLine();
                            
                            do {  
                                System.out.println("Username:");
                                user=sc.nextLine();
                                if(!rmi.verificaUser(user)){
                                    break;
                                }
                                System.out.println("Username já esta em uso.\nTente de novo..");
                            } while (true);
                            
                            //funçao para verificar se o username esta disponivel
                            
                            System.out.println("Senha:");
                            pwd=sc.nextLine();
                            do {                                
                                System.out.println("Departamento");
                                dep=sc.nextLine();
                                if(dep.length()<=5){
                                    break;
                                }
                            } while (true);
                            
                            
                            while(true){                                
                                System.out.println("Estatuto");
                                est=sc.nextLine();
                                if(!est.equals("estudante") && !est.equals("docente") && !est.equals("funcionario")){
                                    System.out.println("Estatuto Inválido");
                                }else{
                                    break;
                                }
                            } 
                            rmi.registar(nome,telefone,morada,numcc,valcc,user,pwd,dep,est);
                            break;}
                        case 2:{
                            sc.nextLine();
                            while(true){
                                System.out.println("Titulo da Eleição:");
                                titulo=sc.nextLine();
                                if(!rmi.verificaTitulo(titulo,1)){
                                    break;
                                }else{
                                    System.out.println("Já existe uma Eleição com esse titulo. Tente novamente..");
                                }
                            }
                            System.out.println("Data de Inicio da Eleição");
                            do {                                
                                System.out.println("Hora:");
                                h=sc.nextInt();
                                if(h<0 && h>25){
                                    System.out.println("Hora Invalido.");
                                }
                            } while (h<0 && h>25);
                            do {                                
                                System.out.println("Minuto:");
                                min=sc.nextInt();
                                if(min<0 && min>61){
                                    System.out.println("Minuto Invalido.");
                                }
                            } while (min<0 && min>61);
                            do {                                
                                System.out.println("Dia:");
                                d=sc.nextInt();
                                if(d<0 && d>32){
                                    System.out.println("Dia Invalido.");
                                }
                            } while (d<0 && d>31);
                            do {                                
                                System.out.println("Mês:");
                                m=sc.nextInt();
                                if(d<0 && d>=13){
                                    System.out.println("Mês Invalido.");
                                }
                            } while (m<0 && m>=13);
                            System.out.println("Ano:");
                            a=sc.nextInt();
                            inicio= new GregorianCalendar(a,m,d,h,min);
                            System.out.println("Data do Fim da Eleição");
                            do{
                                do {                                
                                    System.out.println("Hora:");
                                    h=sc.nextInt();
                                    if(h<0 && h>25){
                                        System.out.println("Hora Invalido.");
                                    }
                                } while (h<0 && h>25);
                                do {                                
                                    System.out.println("Minuto:");
                                    min=sc.nextInt();
                                    if(min<0 && min>61){
                                        System.out.println("Minuto Invalido.");
                                    }
                                } while (min<0 && min>61);
                                do {                                
                                    System.out.println("Dia:");
                                    d=sc.nextInt();
                                    if(d<0 && d>32){
                                        System.out.println("Dia Invalido.");
                                    }
                                } while (d<0 && d>31);
                                do {                                
                                    System.out.println("Mês:");
                                    m=sc.nextInt();
                                    if(d<0 && d>=13){
                                        System.out.println("Mês Invalido.");
                                    }
                                } while (m<0 && m>=13);
                                System.out.println("Ano:");
                                a=sc.nextInt();
                                fim=new GregorianCalendar(a,m,d,h,min);
                                if(inicio.after(fim)){
                                    System.out.println("Data de Fim de Eleição Inválida");
                                }
                            }while(inicio.after(fim));
                            sc.nextLine();
                            do {                                
                                System.out.println("Tipo de Eleição:");
                                tipo=sc.nextLine();
                                if(!tipo.equals("nucleo") && !tipo.equals("geral")){
                                    System.out.println("Tipo de eleição invalido");
                                }
                            } while (!tipo.equals("nucleo") && !tipo.equals("geral"));
                            System.out.println("Descrição da eleição:");
                            desc=sc.nextLine();
                            rmi.criarEleicao(inicio,fim,titulo,desc,tipo,new CopyOnWriteArrayList<>(),new CopyOnWriteArrayList<>());
                            break;}
                        case 3:{
                            sc.nextLine();
                            do {
                                System.out.println("Nome da Lista:");
                                nome=sc.nextLine();                           
                                if(!rmi.verificaTitulo(nome,2)){
                                    break;
                                }
                            } while (rmi.verificaTitulo(nome,2));
                            System.out.println("Titulo da Eleição:");
                            titulo=sc.nextLine();
                            
                            do {                                
                                System.out.println("Tipo de Eleição:");
                                tipo=sc.nextLine();
                                if(!tipo.equals("nucleo") && !tipo.equals("geral")){
                                    System.out.println("Tipo de eleição invalido");
                                }
                            } while (!tipo.equals("nucleo") && !tipo.equals("geral"));
                            if(tipo.equals("nucleo")){
                                do {                                
                                    System.out.println("Tipo de Lista:");
                                    tipo2=sc.nextLine();
                                    if(!tipo2.equals("nucleo") && !tipo2.equals("geral")){
                                        System.out.println("Tipo de Lista invalido");
                                    }
                                } while (!tipo2.equals("estudante") && !tipo2.equals("funcionario") && !tipo2.equals("docente"));
                            }else{
                                tipo2=tipo;
                            }
                            System.out.println("Número de pessoas que pertencem a lista:");
                            m=sc.nextInt();
                            nomes=new String[m];
                            sc.nextLine();
                            System.out.println("Nome das pessoas que pertencem a lista:");
                            for (int l = 0; l < m; l++) {
                                nomes[l]=sc.nextLine();
                            }
                            rmi.adicionarListas(nome,titulo,tipo,tipo2,m,nomes);
                            break;
                        }
                        case 4:{
                            sc.nextLine();
                            while(true){
                                System.out.println("Titulo Da eleição");
                                titulo=sc.nextLine();
                                System.out.println("Departamento:");
                                dep=sc.nextLine();
                                if(rmi.criarMesas(titulo, dep)){
                                    System.out.println("Mesa Criada");
                                    break;
                                }else{
                                    System.out.println("Dados incorretos.Tente de novo..");
                                }
                            }
                            break;
                        }   
                        default:{
                            System.out.println("Escolha uma das opções acima listadas..");
                            break;}
                    }
                    break;}
                case 2:{
                    System.out.println("Vamos Eliminar:\n1-Mesa de Voto\n3-Lista");
                    in2=sc.nextInt();
                    switch (in2){
                        case 1:{
                            while(true){
                                System.out.println("Departamento:");
                                dep=sc.nextLine();
                                System.out.println("Titulo da eleição:");
                                titulo=sc.nextLine();
                                if(rmi.removerMesas(dep,titulo)){
                                    break;
                                }
                                if(dep.equals("") && titulo.equals("")){
                                    System.out.println("Operação cancelada.");
                                    break;
                                }else{
                                    System.out.println("Mesa e/ou Eleição não existem.Tente de novo..");
                                }
                            }
                            break;}
                        case 2:{
                            
                                System.out.println("Titulo de eleição:");
                                titulo=sc.nextLine();
                                System.out.println("Nome da lista:");
                                nome=sc.nextLine();
                                if(rmi.removerListas(nome,titulo)){
                                    System.out.println("Lista Removida com Sucesso");
                                    break;
                                }else{
                                    System.out.println("A Eleição e/ou Lista não existem.Tente de novo..");
                                }
                            
                            break;}
                        default:{
                            System.out.println("Escolha uma das opÃ§Ãµes acima listadas..");
                            break;}
                    }
                    break;}
                case 3:{
                    System.out.println("Vamos Listar:\n1-Pessoas\n2-Eleições\n3-Listas");
                    in2=sc.nextInt();
                    switch (in2){
                        case 1:{
                            System.out.println(rmi.listaPessoas());
                            break;}
                        case 2:{
                            System.out.println("1-Todas as eleiçoes\n2-Informações de uma Eleição\n3-Resultados de uma Eleição\n4-Eleições Terminadas");
                            in3=sc.nextInt();
                            switch (in3) {
                                case 1:{
                                    System.out.println(rmi.listaEleicoes());
                                    break;
                                }
                                case 2:{
                                    do {                                        
                                        System.out.println("Titulo da Eleição:");
                                        titulo=sc.next();
                                        if(rmi.verificaTitulo(titulo, 1)){
                                            break;
                                        }
                                    } while (true);
                                    System.out.println(rmi.informacaoEleicao(titulo));
                                    break;
                                }    
                                case 3:{
                                    do {                                        
                                        System.out.println("Titulo da Eleição:");
                                        titulo=sc.next();
                                        if(rmi.verificaTitulo(titulo, 1)){
                                            break;
                                        }
                                    } while (true);
                                    System.out.println(rmi.resultadosEleicao(titulo));
                                    break;
                                }
                                case 4:{
                                    CopyOnWriteArrayList<String> temp=rmi.eleicoesTerminadas();
                                    for (int i = 0; i < temp.size(); i++) {
                                        System.out.println(temp.get(i));
                                        
                                    }
                                }
                                default:
                                    System.out.println("Opção invalida");
                            }
                            break;}
                        case 3:{
                            System.out.println(rmi.listaListas());
                            break;
                        }
                        default:{
                            System.out.println("Escolha uma das opÃ§Ãµes acima listadas..");
                            break;}
                    }
                    break;}
                case 4:{
                    System.out.println("Vamos Editar:\n1-Pessoas\n2-EleiÃ§Ãµes");
                    in2=sc.nextInt();
                    switch (in2){
                        case 1:{
                            
                            break;}
                        case 2:{
                            System.out.println("1-Alterar Data de inicio\n2-Alterar Data de Fim\n3-Alterar Titulo\n4-Alterar Descrição\n5-Alterar Tipo");
                            switch (in) {
                                case 1:{
                                    System.out.println("Titulo da Eleição:");
                                    nome=sc.nextLine();
                                    System.out.println("Nova Data de Inicio da Eleição");
                                        try{
                                            do{
                                            
                                                do {                                
                                                    System.out.println("Hora:");
                                                    h=sc.nextInt();
                                                    if(h>0 && h<25){
                                                        System.out.println("Hora Invalido.");
                                                    }
                                                } while (h>0 && h<25);
                                                do {                                
                                                    System.out.println("Minuto:");
                                                    min=sc.nextInt();
                                                    if(min>=0 && min<61){
                                                        System.out.println("Minuto Invalido.");
                                                    }
                                                } while (min>=0 && min<61);
                                                do {                                
                                                    System.out.println("Dia:");
                                                    d=sc.nextInt();
                                                    if(d>0 && d<32){
                                                        System.out.println("Dia Invalido.");
                                                    }
                                                } while (d>0 && d<32);
                                                do {                                
                                                    System.out.println("Mês:");
                                                    m=sc.nextInt();
                                                    if(d>0 && d<13){
                                                        System.out.println("Mês Invalido.");
                                                    }
                                                } while (m>0 && m<13);
                                                System.out.println("Ano:");
                                                a=sc.nextInt();
                                                inicio= new GregorianCalendar(a,m,d,h,min);
                                                fim=rmi.getFimEleicao(nome);
                                                if(!inicio.after(fim)){
                                                    rmi.alterarFim(nome, inicio);
                                                   break; 
                                                }else
                                                    System.out.println("Data Fornecida Invalida");
                                            }while(inicio.after(fim));
                                        }catch(RemoteException e){
                                            e.printStackTrace();
                                        }
                                        
                                    break;
                                }
                                case 2:{
                                    System.out.println("Titulo da Eleição:");
                                    nome=sc.nextLine();
                                    System.out.println("Nova Data de Inicio da Eleição");
                                    do{
                                        do {                                
                                            System.out.println("Hora:");
                                            h=sc.nextInt();
                                            if(h>0 && h<25){
                                                System.out.println("Hora Invalido.");
                                            }
                                        } while (h>0 && h<25);
                                        do {                                
                                            System.out.println("Minuto:");
                                            min=sc.nextInt();
                                            if(min>=0 && min<61){
                                                System.out.println("Minuto Invalido.");
                                            }
                                        } while (min>=0 && min<61);
                                        do {                                
                                            System.out.println("Dia:");
                                            d=sc.nextInt();
                                            if(d>0 && d<32){
                                                System.out.println("Dia Invalido.");
                                            }
                                        } while (d>0 && d<32);
                                        do {                                
                                            System.out.println("Mês:");
                                            m=sc.nextInt();
                                            if(d>0 && d<13){
                                                System.out.println("Mês Invalido.");
                                            }
                                        } while (m>0 && m<13);
                                        System.out.println("Ano:");
                                        a=sc.nextInt();
                                        fim= new GregorianCalendar(a,m,d,h,min);
                                        inicio=rmi.getInicioEleicao(nome);
                                        if(!fim.before(inicio)){
                                            rmi.alterarFim(nome, fim);
                                                   break; 
                                                }else
                                                    System.out.println("Data Fornecida Invalida");
                                    }while(true);
                                    break;
                                }
                                case 3:{
                                    do {                                        
                                        System.out.println("Titulo da Eleição");
                                        titulo=sc.nextLine();
                                        if(rmi.verificaTitulo(titulo,1)){
                                            break;
                                        }
                                        
                                    } while (true);
                                    do {                                        
                                        System.out.println("Novo Titulo da Eleição");
                                        nome=sc.nextLine();
                                        if(!rmi.verificaTitulo(nome, 1)){
                                            rmi.alterarTitulo(titulo, nome);
                                            break;
                                        }
                                    } while (true);
                                    break;
                                }
                                case 4:{
                                    do {                                        
                                        System.out.println("Titulo da Eleição");
                                        titulo=sc.nextLine();
                                        if(rmi.verificaTitulo(titulo,1)){
                                            break;
                                        }
                                    } while (true);
                                    System.out.println("Nova Descrição");
                                    desc=sc.nextLine();
                                    rmi.alterarDesc(titulo, desc);
                                    break;
                                }
                                case 5:{
                                    do {                                        
                                        System.out.println("Titulo da Eleição:");
                                        titulo=sc.nextLine();
                                        if(rmi.verificaTitulo(titulo,1)){
                                            break;
                                        }    
                                    } while (true);
                                    while(true){                                
                                        System.out.println("Novo tipo:");
                                        tipo=sc.nextLine();
                                        if(!tipo.equals("nucleo") && !tipo.equals("geral")){
                                            System.out.println("tipo Inválido");
                                        }else{
                                            break;
                                        }
                                    } 
                                    break;
                                }
                                default:
                                    System.out.println("Escolha uma das opções acima listadas..");
                            }
                            break;}
                        default:{
                            System.out.println("Escolha uma das opções acima listadas..");
                            break;}
                    }
                    break;}
                case 0:{
                    System.out.println("a desligar..");
                    break;}
                default:{
                   System.out.println("Escolha uma das opÃ§Ãµes acima listadas..");
                    break;}
            }
        }
	} catch (/*MalformedURLException |*/ NotBoundException | RemoteException e) {
            System.out.println("Exception in main: " + e);
	}
        
    }
    
}

