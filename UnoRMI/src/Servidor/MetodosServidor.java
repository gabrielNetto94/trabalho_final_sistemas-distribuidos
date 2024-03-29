package Servidor;

import Cliente.IMetodosCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            listaClientes.getFirst().setPodeJogar(false);
        } catch (RemoteException ex) {
            Logger.getLogger(MetodosServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //parâmetro numCartas, número de cartas para comprar +1, +2 ou +4
    @Override
    public LinkedList<Cliente.Carta> comprarCarta(int numCartas) {

        LinkedList<Cliente.Carta> cartasCompradas = new LinkedList<>();

        for (int i = 0; i < numCartas; i++) {
            Cliente.Carta carta = baralho.get(random.nextInt(baralho.size()));
            cartasCompradas.add(carta);
            baralho.remove(carta);
        }

        return cartasCompradas;
    }

    //pega uma carta do baralho e coloca na pilha de cartas jogadas, e seta a true no "setPordeJogar" do primeiro cliente
    public void iniciarJogo() {

        /*
        boolean flag = false;

        while (!flag) {
            if (listaClientes.isEmpty()) { //se a lista de clientes estiver vazia dorme 1s e testa de novo, se não executa o else
                Thread.sleep(1000);
            } else {
        
        listaClientes.getFirst().setPodeJogar(true);//autoriza o cliente a jogar
 */
        Cliente.Carta c = baralho.get(random.nextInt(baralho.size()));//pega 1 carta do baralho para 
        pilhaCartasJogadas.push(c);
        baralho.remove(c);
//                flag = true;
//            }
//        }
    }

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
}
