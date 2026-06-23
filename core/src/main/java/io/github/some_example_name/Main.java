package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
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

    private Personagem guerreiro;
    private Personagem orc;
    private String mensagem;
    private int estadoTela;
    
    private int posicaoXGuerreiro;

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

        guerreiro = new Personagem("Guerreiro", 100, 20);
        orc = new Personagem("Orc", 80, 15);

        mensagem = "Aguardando comando...";
        estadoTela = 0;
        posicaoXGuerreiro = 200;
    }

    private String barraVida(int vidaAtual, int vidaMaxima) {
        int blocos = (vidaAtual * 10) / vidaMaxima;
        String barra = "[";
        for (int i = 0; i < 10; i++) {
            if (i < blocos) barra += "#";
            else barra += "-";
        }
        barra += "]";
        return barra;
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

        if (estadoTela == 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || isClicado(280, 260, 400, 60)) {
                guerreiro = new Personagem("Guerreiro", 100, 20);
                orc = new Personagem("Orc", 80, 15);
                mensagem = "Aguardando comando...";
                estadoTela = 1;
            }

            batch.begin();
            batch.draw(ceu, 0, 0, 960, 720);
            batch.draw(arvoresFundo, 0, 0, 960, 720);
            batch.draw(arvoresMeio, 0, 0, 960, 720);
            batch.draw(arvoresFrente, 0, 0, 960, 720);
            batch.draw(chao, 0, 0, 960, 200);
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(0, 0, 0, 0.7f);
            shape.rect(0, 0, 960, 720);
            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
            fonte.getData().setScale(2.5f);
            fonte.draw(batch, "ARENA RPG", 350, 450);
            fonte.getData().setScale(1.2f);
            fonte.draw(batch, "Pressione [ENTER] ou Clique aqui para iniciar", 280, 300);
            batch.end();

        } else if (estadoTela == 1) {
            if (guerreiro.getVida() > 0 && orc.getVida() > 0) {

                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || isClicado(60, 50, 150, 40)) {
                    guerreiro.atacar(orc);
                    posicaoXGuerreiro = 350;
                    mensagem = "Voce atacou o Orc!";

                    if (orc.getVida() <= 0) {
                        mensagem = "VITORIA! O Orc foi derrotado.";
                    } else {
                        orc.atacar(guerreiro);
                        mensagem += " O Orc contra-atacou!";
                        if (guerreiro.getVida() <= 0) mensagem = "DERROTA! Voce foi eliminado.";
                    }
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || isClicado(250, 50, 250, 40)) {
                    guerreiro.curar(30);
                    mensagem = "Voce recuperou vida!";
                    orc.atacar(guerreiro);
                    mensagem += " O Orc atacou!";
                    if (guerreiro.getVida() <= 0) mensagem = "DERROTA! Voce foi eliminado.";
                }
            } else {
                if (Gdx.input.isKeyJustPressed(Input.Keys.R) || isClicado(60, 50, 300, 40)) {
                    estadoTela = 0;
                }
            }

            batch.begin();
            batch.draw(ceu, 0, 0, 960, 720);
            batch.draw(arvoresFundo, 0, 0, 960, 720);
            batch.draw(arvoresMeio, 0, 0, 960, 720);
            batch.draw(arvoresFrente, 0, 0, 960, 720);
            batch.draw(chao, 0, 0, 960, 200);
            batch.end();

            batch.begin();
            batch.draw(spriteGuerreiro, posicaoXGuerreiro, 150, 150, 150); 
            batch.draw(spriteOrc, 600, 150, 150, 150, 0, 0, spriteOrc.getWidth(), spriteOrc.getHeight(), true, false);
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(0, 0, 0, 0.7f);

            shape.rect(20, 580, 250, 120); // Jogador
            shape.rect(690, 580, 250, 120); // Inimigo
            
            shape.rect(20, 20, 920, 100);

            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            batch.begin();
            fonte.getData().setScale(1.1f);

            fonte.draw(batch, guerreiro.getNome(), 40, 680);
            fonte.draw(batch, "Vida: " + guerreiro.getVida(), 40, 650);
            fonte.draw(batch, barraVida(guerreiro.getVida(), 100), 40, 620);
            
            fonte.draw(batch, "VS", 465, 640);

            fonte.draw(batch, orc.getNome(), 710, 680);
            fonte.draw(batch, "Vida: " + orc.getVida(), 710, 650);
            fonte.draw(batch, barraVida(orc.getVida(), 80), 710, 620);

            if (guerreiro.getVida() > 0 && orc.getVida() > 0) {
                fonte.draw(batch, "[1] Atacar", 60, 80);
                fonte.draw(batch, "[2] Curar (+30 HP)", 250, 80);
            } else {
                fonte.draw(batch, "[R] Voltar ao Menu (Clique aqui)", 60, 80);
            }
            fonte.draw(batch, "Log: " + mensagem, 60, 50);

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
    }
}