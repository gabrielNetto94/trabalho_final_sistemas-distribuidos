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
    LinkedList<Cliente.Carta> pilhaCartas = new LinkedList<>();
    LinkedList<IMetodosCliente> listaClientes = new LinkedList<>();
    
    public MetodosServidor() throws RemoteException {
        gerarBaralho();
    }
    
    //Pega 7 cartas aleatórias do baralhoe retorna uma lista o jogador que solicitou 
    @Override
    public LinkedList<Cliente.Carta> iniciarMao() throws RemoteException {

        LinkedList<Cliente.Carta> listaMao = new LinkedList<>();

        Random random = new Random();

        for (int i = 0; i < 7; i++) {
            int num = random.nextInt(baralho.size());
            Cliente.Carta c = baralho.get(num);
            baralho.remove(c);
            listaMao.add(c);
        }

        return listaMao;
    }
    
    @Override
    public void registraCliente(IMetodosCliente refCliente, String nome) throws RemoteException {
        
        listaClientes.add(refCliente); 
        System.out.println("Cliente "+nome+" conectado!");
       
        
    }

    //por enquanto está gerando apenas as 4 cores com os 9 números
    public void gerarBaralho() {

        for (int i = 0; i < 9; i++) {
            Cliente.Carta carta = new Cliente.Carta("VERMELHO", i);
            baralho.add(carta);
        }

        for (int i = 0; i < 9; i++) {
            Cliente.Carta carta = new Cliente.Carta("VERDE", i);
            baralho.add(carta);
        }
        for (int i = 0; i < 9; i++) {
            Cliente.Carta carta = new Cliente.Carta("AZUL", i);
            baralho.add(carta);
        }
        for (int i = 0; i < 9; i++) {
            Cliente.Carta carta = new Cliente.Carta("AMARELO", i);
            baralho.add(carta);
        }
    }

    //recebe uma lista como parâmetro e printa as catas
    public void exibirMao(LinkedList<Cliente.Carta> listaMao) {

        //cartas dadas
        System.out.print("Cartas na mão: ");
        for (int i = 0; i < listaMao.size(); i++) {
            System.out.print(listaMao.get(i).cor + " - " + listaMao.get(i).numero + "    ");
        }
        System.out.println("");
    }

    private void iniciar() throws Exception {
        iniciarMao();

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
