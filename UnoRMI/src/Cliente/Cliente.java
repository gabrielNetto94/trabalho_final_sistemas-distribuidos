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
            
            System.out.println("Última carta jogada: "+servidor.getCartaTopo().numero+" - "+servidor.getCartaTopo().cor);
            
            boolean flag = false;
//            int i = 0;
            while (flag == false){                    
                System.out.println("\nDigite o número da carta para jogar: ");
                int numCarta = scan.nextInt();

                if (cartasMao.get(numCarta).numero == servidor.getCartaTopo().numero || cartasMao.get(numCarta).cor.equals(servidor.getCartaTopo().cor)) {
                    System.out.println("Carta Jogada");
                    servidor.setCartaTopo(cartasMao.get(numCarta));
                    cartasMao.remove(numCarta);
                    System.out.println("Última carta jogada: "+servidor.getCartaTopo().numero+" - "+servidor.getCartaTopo().cor);
                    cliente.mostraMao(cartasMao);
                    
                    flag = true;
                }else{
                    System.out.println("Carta inválida");
                }
            }
                

        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
