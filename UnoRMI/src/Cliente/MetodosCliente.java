package Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;

public class MetodosCliente extends UnicastRemoteObject implements IMetodosCliente {

    public MetodosCliente() throws RemoteException {

    }

    @Override
    public void testeCliente(String msg) {
        System.out.println(msg);
    }
    
    public void mostraMao (LinkedList<Carta> cartas){
        System.out.print("Cartas na mão: ");
            for (int i = 0; i < cartas.size(); i++) {
                System.out.print("("+i+") "+cartas.get(i).cor+"-"+cartas.get(i).numero+"     ");
            }
        System.out.println("");
            
        }
    }
