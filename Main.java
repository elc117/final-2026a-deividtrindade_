import io.github.some_example_name.model.entidades.Personagem;


public class Main {

    public static void main(String[] args) {

        Personagem guerreiro = new Personagem("Guerreiro", 100, 20);
        Personagem orc = new Personagem("Orc", 80, 15);

        System.out.println("Vida do Orc antes do ataque: " + orc.getVida());

        guerreiro.atacar(orc);

        System.out.println("Vida do Orc depois do ataque: " + orc.getVida());


    }
}