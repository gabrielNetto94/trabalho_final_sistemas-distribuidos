package Servidor;

import Cliente.IMetodosCliente;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface IMetodosServidor extends Remote {

    public LinkedList<Cliente.Carta> receberCartaServidor() throws RemoteException;
    public void registraCliente(IMetodosCliente refCliente) throws RemoteException;
    public Cliente.Carta getCartaTopo() throws RemoteException;
    public void setCartaTopo(Cliente.Carta carta) throws RemoteException;
    public void finalizaJogada() throws RemoteException;
    public LinkedList<Cliente.Carta> comprarCarta(int numeroCartas) throws RemoteException;
    
}