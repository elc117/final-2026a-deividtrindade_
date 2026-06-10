public class Main {

    public static void main(String[] args) {

        Personagem guerreiro = new Personagem("Guerreiro", 100, 20);

        System.out.println(guerreiro.getNome());
        System.out.println(guerreiro.getVida());
        System.out.println(guerreiro.getAtaque());

        guerreiro.receberDano(15);

        System.out.println(guerreiro.getVida());

    }
}