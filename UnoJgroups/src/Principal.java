import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class Principal extends ReceiverAdapter {
    
    /* possíveis métodos com base na análise do jogo
    
    1 - distribuir cartas para jogadores 
        gerarBaralho();
        darCartasParaJogadores() return LinkedList;
    
    2 - pegar uma carta do baralho para iniciar o jogo
        inicarJogo();
    
    3 - jogador 1 começa
        
        4 - se jogador 1 tiver carta da mesma cor ou mesmo número, a carta é aceita no monte de cartas jogadas
        5 - se não tiver carta compatível, compra uma carta (aleatória, pq as cartas vão estar ordenadas no baralho, por isso compra aletório)
    6 - jogador 2, passo 4 novamente.
    7 - o primeiro jogador que descartar todas as cartas primeiro, ganha a partida.
    
    
    lista de cartas do baralho
    lista de cartas jogadas 
    lista de cartas dos jogadores
    
    */
    
    JChannel channel;
    String props; // set by application
    String prop; // set by application    
    
    LinkedList<Carta> baralho = new LinkedList<>();
    LinkedList<Carta> pilhaCartas = new LinkedList<>();
       
    
    public Principal(){
        //gera o baralho no construtor
        gerarBaralho();
    }
    
    //
    /*
    por enquanto está gerando apenas as 4 cores com os 9 números
    */
    public void gerarBaralho(){
        
        for (int i = 0; i < 9; i++) {
            Carta carta = new Carta("VERMELHO",i);
            baralho.add(carta);
        }
        
        for (int i = 0; i < 9; i++) {
            Carta carta = new Carta("VERDE",i);
            baralho.add(carta);
        }
        for (int i = 0; i < 9; i++) {
            Carta carta = new Carta("AZUL",i);
            baralho.add(carta); 
        }
        for (int i = 0; i < 9; i++) {
            Carta carta = new Carta("AMARELO",i);
            baralho.add(carta);
        }
    }
  
    
    /*
    Pega 7 cartas aleatórias do baralho
    e retorna uma lista o jogador que solicitou
    */
    public LinkedList<Carta> iniciarMao() {
        
        LinkedList<Carta> listaMao = new LinkedList<>();
        
        Random random = new Random();
        
        for (int i = 0; i < 7; i++) {    
            int num = random.nextInt(baralho.size());
            Carta c = baralho.get(num);
            baralho.remove(c);
            listaMao.add(c);
        }
        
        return listaMao;
    }

    /*
    recebe uma lista como parâmetro e printa as catas
    */
    public void exibirMao(LinkedList<Carta> listaMao) {
        
        //cartas dadas
        System.out.print("Cartas na mão: ");
        for (int i = 0; i < listaMao.size(); i++) {
            System.out.print(listaMao.get(i).cor +" - "+ listaMao.get(i).numero + "    ");   
        }
        System.out.println("");
    }

    private void iniciar() throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");//desabilita ipv6, para que só sejam aceitas conexões via ipv4
        channel = new JChannel();
        channel.setReceiver(this);
        channel.connect("JogoUno");
        iniciarMao();   
        
        //jogar recebe 7 cartas
        //Carta resultado = new Carta();

        Scanner scanner = new Scanner(System.in);
        
        while (true) {
//            Thread.sleep(2000);
            System.out.println("Sua mão é: ");
//            exibirMao();
            System.out.println("jogar ou comprar?");
            String op = scanner.next();
            
            if (op.toLowerCase().equals("jogar")) {
                System.out.println("digite a carta");
                int numero = scanner.nextInt();
                System.out.println("digite a cor");
                String cor = scanner.next();
                //apos ler o numero, percorrer a lista e ver se o numero existe
//                resultado = percorrerMao(numero,cor);
//                System.out.println("Adicionando a carta " + resultado.numero + " " + resultado.cor);
//                listaPilha.add(resultado);
                
                
                //criar um metodo para mandar o a cor e numero
                String mensagem = numero+","+cor;
                System.out.println("a string ficou: "+ mensagem);
        
                Message msg = new Message(null, mensagem);
                channel.send(msg);
                
                
            }
            else if(op.toLowerCase().equals("comprar")){
                //faz ele comprar
            }

        }

    }

    public static void main(String[] args) throws Exception {
        new Principal().iniciar();

    }

    @Override
    public void receive(Message msg) {
        //metodo para descompactar a string 
        String temporario = msg.getObject();
        String mensagem[] = temporario.split(",");
        System.out.println("TOPO DA PILHA:");
        System.out.println("carta é "+mensagem[0]+" cor: "+ mensagem[1]);    
    }

    @Override
    public void viewAccepted(View view_atual) {
//        System.out.println("---VISÃO DO GRUPO ATUALIZADA---");
//        System.out.println("ID da view: " + view_atual.getViewId().getId());
//        System.out.println("Coordenador: " + view_atual.getCreator());
        System.out.print("Membros: ");
        
        List<Address> membros = view_atual.getMembers();
        for (int i = 0; i < membros.size(); i++) {
            System.out.print(membros.get(i) + ", ");
        }
        System.out.println();

    }

    @Override
    public void suspect(Address mbr) {
        System.out.println("PROCESSO SUSPEITO DE FALHA: " + mbr);
    }

}
