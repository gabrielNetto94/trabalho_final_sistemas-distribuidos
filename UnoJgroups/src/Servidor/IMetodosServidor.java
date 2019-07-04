package Servidor;

import Cliente.IMetodosCliente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface IMetodosServidor extends Remote {

    public LinkedList<Cliente.Carta> iniciarMao() throws RemoteException;
    public void registraCliente(IMetodosCliente refCliente,String nome) throws RemoteException;
    
}