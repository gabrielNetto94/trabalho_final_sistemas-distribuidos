package Cliente;

import Servidor.IMetodosServidor;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Cliente {

    String ip;
    String nick;
    MetodosCliente cli;
    IMetodosServidor servidor;

    public Cliente(){
        conectar();
    }
    private void conectar() {


        try {
            servidor = (IMetodosServidor) Naming.lookup("rmi://localhost/MetodosServidor");
            cli = new MetodosCliente();
            //servidor.registraCliente(cli, nick);
            servidor.iniciarMao();
            
            System.out.println("");

        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            System.out.println("erro");
        }

    }

    public static void main(String[] args) {

    }

}
