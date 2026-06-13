public class Personagem {

    private String nome;
    private int vida;
    private int ataque;

    public Personagem(String nome, int vida, int ataque) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;
    }

    public void receberDano(int dano) {
        vida = vida - dano;

        if(vida < 0) {
            vida = 0;
        }
    }
    
    public void atacar(Personagem inimigo) {
        inimigo.receberDano(ataque);
    }

    public String getNome() {
        return nome;
    }

    public int getVida() {
        return vida;
    }

    public int getAtaque() {
        return ataque;
    }
}