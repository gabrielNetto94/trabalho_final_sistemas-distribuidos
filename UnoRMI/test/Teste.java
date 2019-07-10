
import java.util.LinkedList;

public class Teste {

    public static void main(String[] args) {
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        
        l1.add(1);
        l1.add(2);
        l2.add(3);
        l2.add(4);
        
        l1.addAll(l2);
        System.out.println(l1);
        System.out.println(l2);
        
        
    }
}
