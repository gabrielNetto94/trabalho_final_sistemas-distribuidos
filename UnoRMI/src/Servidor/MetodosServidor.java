package Servidor;

import Cliente.IMetodosCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Random;

public class MetodosServidor extends UnicastRemoteObject implements IMetodosServidor {
    
    LinkedList<Cliente.Carta> baralho = new LinkedList<>();
    LinkedList<Cliente.Carta> pilhaCartasJogadas = new LinkedList<>();
    LinkedList<IMetodosCliente> listaClientes = new LinkedList<>();

    Random random = new Random();

    public MetodosServidor() throws RemoteException {
        gerarBaralho();
        iniciarJogo();
    }

    @Override
    public void registraCliente(IMetodosCliente refCliente) throws RemoteException {

        listaClientes.add(refCliente);
        System.out.println("Cliente conectado!");
        System.out.println("Número de clientes: " + listaClientes.size());
    }
    
    @Override
    public LinkedList<Cliente.Carta> receberCartaServidor() throws RemoteException {

        LinkedList<Cliente.Carta> listaMao = new LinkedList<>();

        for (int i = 0; i < 7; i++) {
            Cliente.Carta c = baralho.get(random.nextInt(baralho.size()));
            baralho.remove(c);
            listaMao.add(c);
        }
        return listaMao;
    }

    @Override
    public Cliente.Carta getCartaTopo() throws RemoteException {
        return pilhaCartasJogadas.getFirst();
    }

    @Override
    public void setCartaTopo(Cliente.Carta carta) throws RemoteException {
        pilhaCartasJogadas.push(carta);
    }
    
    @Override
    public void finalizaJogada() {
        //
    }
    
    @Override
    public Cliente.Carta comprarCarta(){
        Cliente.Carta carta = baralho.get(random.nextInt(baralho.size()));
        baralho.remove(carta);
        return carta;
    }

//    public void mostraBaralho(LinkedList<Cliente.Carta> baralho){
//        System.out.print("Cartas => ");
//        for(Cliente.Carta c:baralho){
//            System.out.print(c.cor+" - "+c.numero+"   ");
//        }
//    }
    
    //por enquanto está gerando apenas as 4 cores com os 9 números
    public void gerarBaralho() {

        for (int i = 0; i < 10; i++) {
            Cliente.Carta carta = new Cliente.Carta("VERMELHO", i);
            baralho.add(carta);
        }

        for (int i = 0; i < 10; i++) {
            Cliente.Carta carta = new Cliente.Carta("VERDE", i);
            baralho.add(carta);
        }
        for (int i = 0; i < 10; i++) {
            Cliente.Carta carta = new Cliente.Carta("AZUL", i);
            baralho.add(carta);
        }
        for (int i = 0; i < 10; i++) {
            Cliente.Carta carta = new Cliente.Carta("AMARELO", i);
            baralho.add(carta);
        }
    }

    //pega uma carta do baralho e coloca na pilha de cartas jogadas
    public void iniciarJogo() {
        Cliente.Carta c = baralho.get(random.nextInt(baralho.size()));
        pilhaCartasJogadas.push(c);
        baralho.remove(c);
    }

}
