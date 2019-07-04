package Cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MetodosCliente extends UnicastRemoteObject implements IMetodosCliente {

    public MetodosCliente() throws RemoteException {

    }

}
