package Cliente;

import Servidor.IMetodosServidor;
import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    MetodosCliente cliente;
    IMetodosServidor servidor;

    LinkedList<Carta> cartasMao = new LinkedList<>();

    public Cliente() {
        try {
            conectar();
            cartasMao = servidor.iniciarMao();
            mostraMao(cartasMao);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void conectar() {
//
//        try {
//            servidor = (IMetodosServidor) Naming.lookup("rmi://localhost/MetodosServidor");
//            cli = new MetodosCliente();
//            //servidor.registraCliente(cli, nick);
//            
//
//        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
//            System.out.println("erro");
//        }
//    }
    public void mostraMao(LinkedList<Carta> cartas) {
        System.out.print("Cartas: ");
        for (Carta carta : cartas) {
            System.out.print(carta.cor + " - " + carta.numero + "     ");
        }
    }

    public void conectar() {
        try {
            servidor = (IMetodosServidor) Naming.lookup("rmi://localhost/MetodosServidor");
            cliente = new MetodosCliente();

        } catch (NotBoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {

    }

}
