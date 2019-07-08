package Servidor;

import Cliente.IMetodosCliente;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import org.jgroups.Message;

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
    public void registraCliente(IMetodosCliente refCliente) throws RemoteException {

        listaClientes.add(refCliente);
        System.out.println("Cliente conectado!");
        System.out.println("Número de clientes: "+ listaClientes.size());
    }
    
    public void mostraBaralho(LinkedList<Cliente.Carta> baralho){
        System.out.print("Cartas => ");
        for(Cliente.Carta c:baralho){
            System.out.print(c.cor+" - "+c.numero+"   ");
        }
    }
    
    //pega uma carta do baralho e coloca na pilha de cartas jogadas
    public void iniciarJogo(){
        Cliente.Carta c = baralho.get(random.nextInt(baralho.size()));
        pilhaCartasJogadas.push(c);
        baralho.remove(c);
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

    private void iniciar() throws Exception {
        

        //jogar recebe 7 cartas
        //Carta resultado = new Carta();
        Scanner scanner = new Scanner(System.in);

        while (true) {
//            Thread.sleep(2000);
            System.out.println("Sua mão é: ");
//            exibirMao();
            System.out.println("jogar ou comprar?");
            String op = scanner.next();

            if (op.toLowerCase().equals("jogar")) {
                System.out.println("digite a carta");
                int numero = scanner.nextInt();
                System.out.println("digite a cor");
                String cor = scanner.next();
                //apos ler o numero, percorrer a lista e ver se o numero existe
//                resultado = percorrerMao(numero,cor);
//                System.out.println("Adicionando a carta " + resultado.numero + " " + resultado.cor);
//                listaPilha.add(resultado);

                //criar um metodo para mandar o a cor e numero
                String mensagem = numero + "," + cor;
                System.out.println("a string ficou: " + mensagem);

                Message msg = new Message(null, mensagem);

            } else if (op.toLowerCase().equals("comprar")) {
                //faz ele comprar
            }

        }

    }

    public static void main(String[] args) throws Exception {

    }

}

