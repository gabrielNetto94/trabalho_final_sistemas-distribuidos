package Cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMetodosCliente extends Remote {

 public void registraCliente(String msg) throws RemoteException;   
}
    
