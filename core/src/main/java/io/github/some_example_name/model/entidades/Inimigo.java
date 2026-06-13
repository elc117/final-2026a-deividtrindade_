package io.github.some_example_name.model.entidades;

import java.util.Random;


public class Inimigo {

    private Random gerador = new Random();

    private Personagem inimigo = 
        new Personagem("orc",
                gerador.nextInt(100),
                gerador.nextInt(100));
    
    public Personagem getInimigo() {
        return inimigo;
    }

}
