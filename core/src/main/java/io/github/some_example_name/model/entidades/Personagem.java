package io.github.some_example_name.model.entidades;

public class Personagem {

    private String nome;
    private int vida;
    private int ataque;

    public Personagem(String nome, int vida, int ataque) {
        this.nome = nome;
        this.vida = vida;
        this.ataque = ataque;

    }

    public void curar(int quantidade) {
        this.vida += quantidade;
        if (this.vida > 100) {
            this.vida = 100;
        }
    }

    public int receberDano(int dano) {
        vida = vida - dano;

        if (vida < 0) {
            vida = 0;
            return vida;
        }
        return vida;
    }

    public boolean estaVivo() {
        return vida > 0;
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
