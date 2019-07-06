package Cliente;

import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;

public class Carta implements Serializable{

    public int numero;
    public String cor;
    
    public Carta(String cor, int numero){
        this.cor = cor;
        this.numero = numero;
    }
    
}
