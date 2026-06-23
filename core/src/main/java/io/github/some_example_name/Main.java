package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import io.github.some_example_name.model.entidades.Personagem;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont fonte;
    private ShapeRenderer shape;

    private Texture ceu;
    private Texture arvoresFundo;
    private Texture arvoresMeio;
    private Texture arvoresFrente;
    private Texture chao;
    
    private Texture spriteGuerreiro;
    private Texture spriteOrc;
    
    private Texture painel;
    private Texture bolaFogo;

    private Personagem guerreiro;
    private Personagem orc;
    private String mensagem;
    private int estadoTela;
    private int posicaoXGuerreiro;
    private int pontuacao;
    
    private boolean orcAtacando;
    private int bolaFogoX;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        shape = new ShapeRenderer();

        ceu = new Texture("1_ceu.png");
        arvoresFundo = new Texture("2_arvores_fundo.png");
        arvoresMeio = new Texture("3_arvores_meio.png");
        arvoresFrente = new Texture("4_arvores_frente.png");
        chao = new Texture("5_chao.png");
        
        spriteGuerreiro = new Texture("guerreiro.png");
        spriteOrc = new Texture("orc.png");
        
        painel = new Texture("painel.png");
        bolaFogo = new Texture("fogo.png");

        guerreiro = new Personagem("Guerreiro", 100, 20);
        orc = new Personagem("Orc", 80, 15);

        mensagem = "Aguardando comando...";
        estadoTela = 0;
        posicaoXGuerreiro = 200; 
        pontuacao = 0; 
        orcAtacando = false;
    }

    private boolean isClicado(int x, int y, int largura, int altura) {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int mouseX = Gdx.input.getX();
            int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (mouseX >= x && mouseX <= x + largura && mouseY >= y && mouseY <= y + altura) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (posicaoXGuerreiro > 200) {
            posicaoXGuerreiro -= 5; 
        }

        if (orcAtacando) {
            bolaFogoX -= 15; 
            if (bolaFogoX <= 200) { 
                orcAtacando = false;
                orc.atacar(guerreiro);
                mensagem = "O Orc te acertou com magia!";
                if (guerreiro.getVida() <= 0) {
                    mensagem = "DERROTA! Voce foi eliminado.";
                }
            }
        }

        if (estadoTela == 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || isClicado(260, 300, 400, 120)) {
                guerreiro = new Personagem("Guerreiro", 100, 20);
                orc = new Personagem("Orc", 80, 15);
                mensagem = "Aguardando comando...";
                estadoTela = 1;
                pontuacao = 0;
                orcAtacando = false;
            }

            batch.begin();
            batch.draw(ceu, 0, 0, 960, 720);
            batch.draw(arvoresFundo, 0, 0, 960, 720);
            batch.draw(arvoresMeio, 0, 0, 960, 720);
            batch.draw(arvoresFrente, 0, 0, 960, 720);
            batch.draw(chao, 0, 0, 960, 200);
            
            batch.draw(painel, 180, 180, 600, 350); 
            batch.end();

            batch.begin();
            fonte.getData().setScale(2.5f);
            fonte.draw(batch, "ARENA RPG", 350, 400); 
            fonte.getData().setScale(1.2f);
            fonte.draw(batch, "Pressione [ENTER] ou Clique aqui para iniciar", 260, 320); 
            batch.end();

        } else if (estadoTela == 1) {
            if (guerreiro.getVida() > 0 && orc.getVida() > 0 && !orcAtacando) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || isClicado(60, 80, 150, 40)) {
                    guerreiro.atacar(orc);
                    posicaoXGuerreiro = 350; 
                    pontuacao += 10;
                    mensagem = "Voce atacou o Orc! (+10 pts)";

                    if (orc.getVida() <= 0) {
                        pontuacao += 50;
                        mensagem = "VITORIA! O Orc foi derrotado. (+50 pts)";
                    } else {
                        orcAtacando = true;
                        bolaFogoX = 600;
                        mensagem += " Ele esta conjurando magia!";
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || isClicado(250, 80, 250, 40)) {
                    guerreiro.curar(30);
                    mensagem = "Voce recuperou vida!";
                    orcAtacando = true;
                    bolaFogoX = 600;
                    mensagem += " O Orc conjurou magia!";
                }
            } else if (guerreiro.getVida() <= 0 || orc.getVida() <= 0) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.R) || isClicado(60, 80, 300, 40)) {
                    estadoTela = 0;
                }
            }

            batch.begin();
            batch.draw(ceu, 0, 0, 960, 720);
            batch.draw(arvoresFundo, 0, 0, 960, 720);
            batch.draw(arvoresMeio, 0, 0, 960, 720);
            batch.draw(arvoresFrente, 0, 0, 960, 720);
            batch.draw(chao, 0, 0, 960, 200);

            batch.draw(spriteGuerreiro, posicaoXGuerreiro, 150, 150, 150); 
            batch.draw(spriteOrc, 600, 150, 150, 150, 0, 0, spriteOrc.getWidth(), spriteOrc.getHeight(), true, false);

            if (orcAtacando) {
                batch.draw(bolaFogo, bolaFogoX, 180, 60, 60); 
            }

            batch.draw(painel, 10, 480, 280, 300); 
            batch.draw(painel, 670, 480, 280, 300); 
            
            batch.draw(painel, 10, -80, 940, 320); 
            batch.end();

            shape.begin(ShapeRenderer.ShapeType.Filled);
            
            shape.setColor(0.3f, 0.3f, 0.3f, 1f);
            shape.rect(40, 580, 200, 18);
            shape.setColor(0.2f, 0.8f, 0.2f, 1f);
            float hpGuerreiro = Math.max(0, ((float) guerreiro.getVida() / 100) * 200);
            shape.rect(40, 580, hpGuerreiro, 18);

            shape.setColor(0.3f, 0.3f, 0.3f, 1f);
            shape.rect(710, 580, 200, 18);
            shape.setColor(0.8f, 0.2f, 0.2f, 1f);
            float hpOrc = Math.max(0, ((float) orc.getVida() / 80) * 200);
            shape.rect(710, 580, hpOrc, 18);
            
            shape.end();

            batch.begin();
            fonte.getData().setScale(1.1f);

            fonte.draw(batch, guerreiro.getNome(), 40, 660);
            fonte.draw(batch, "Vida: " + guerreiro.getVida(), 40, 625);
            
            fonte.getData().setScale(1.5f);
            fonte.draw(batch, "PONTOS: " + pontuacao, 400, 700);
            fonte.getData().setScale(1.1f);
            
            fonte.draw(batch, "VS", 465, 640);

            fonte.draw(batch, orc.getNome(), 710, 660);
            fonte.draw(batch, "Vida: " + orc.getVida(), 710, 625);

            if (guerreiro.getVida() > 0 && orc.getVida() > 0) {
                if (!orcAtacando) {
                    fonte.draw(batch, "[1] Atacar", 60, 110);
                    fonte.draw(batch, "[2] Curar (+30 HP)", 250, 110);
                } else {
                    fonte.draw(batch, "Aguarde o turno do inimigo...", 60, 110);
                }
            } else {
                fonte.draw(batch, "[R] Voltar ao Menu (Clique aqui)", 60, 110);
            }
            fonte.draw(batch, "Log: " + mensagem, 60, 65);

            batch.end();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        fonte.dispose();
        shape.dispose();
        ceu.dispose();
        arvoresFundo.dispose();
        arvoresMeio.dispose();
        arvoresFrente.dispose();
        chao.dispose();
        spriteGuerreiro.dispose();
        spriteOrc.dispose();
        painel.dispose();
        bolaFogo.dispose();
    }
}