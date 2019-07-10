package Cliente;

import Servidor.IMetodosServidor;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) {
        LinkedList<Carta> cartasMao = new LinkedList<>();
        Scanner scan = new Scanner(System.in);

        try {
            IMetodosServidor servidor = (IMetodosServidor) Naming.lookup("rmi://localhost/MetodosServidor");
            MetodosCliente cliente = new MetodosCliente();
            servidor.registraCliente(cliente);
            
            cartasMao = servidor.receberCartaServidor();
            cliente.mostraMao(cartasMao);
            
            System.out.println("Última carta jogada: " + servidor.getCartaTopo().numero + " - " + servidor.getCartaTopo().cor);

//            if (cliente.getPodeJogar()) {
                boolean flag = false;

                while (!flag) {

                    int op = 0;
                    System.out.print("1 - Jogar Carta // 2 - Comprar Carta: ");
                    op = scan.nextInt();

                    if (op == 1) {

                        System.out.println("\nDigite o número da carta para jogar ou digite -1 para voltar ao menu anterior");
                        int numCarta = scan.nextInt();
                        if (numCarta == -1) {

                        } else {

                            if (cartasMao.get(numCarta).numero == servidor.getCartaTopo().numero || cartasMao.get(numCarta).cor.equals(servidor.getCartaTopo().cor)) {
                                System.out.println("Carta Jogada");
                                servidor.setCartaTopo(cartasMao.get(numCarta));
                                cartasMao.remove(numCarta);
                                System.out.println("Última carta jogada: " + servidor.getCartaTopo().numero + " - " + servidor.getCartaTopo().cor);
                                cliente.mostraMao(cartasMao);
                                servidor.finalizaJogada();//indica para o servidor que terminou a jogada e passa para o próxima cliente
                                flag = true;
                            } else {
                                System.out.println("Carta inválida");
                            }
                        }

                    }
                    if (op == 2) {
                        cartasMao.addAll(servidor.comprarCarta(1));//comprarCarta retorna uma lista fica escalável para comprar 1, 2 ou 4 cartas =)
                        System.out.println("Carta comprada -> " + cartasMao.getLast().cor + " - " + cartasMao.getLast().numero);
                        
                        cliente.mostraMao(cartasMao);
                        servidor.finalizaJogada();//indica para o servidor que terminou a jogada e passa para o próxima cliente
                        flag = true;
                    }

                }
//            } // fim cliente.getPodeJogar();

        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
