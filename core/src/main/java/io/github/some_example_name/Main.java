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
    private Texture fundo;
    private Personagem guerreiro;
    private Personagem orc;
    private String mensagem;
    private ShapeRenderer shape;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
        fundo = new Texture("floresta.png");
        shape = new ShapeRenderer();

        guerreiro = new Personagem("Guerreiro", 100, 20);
        orc = new Personagem("Orc", 80, 15);

        mensagem = "Aguardando comando...";
    }

    private String barraVida(int vidaAtual, int vidaMaxima) {
        int blocos = (vidaAtual * 10) / vidaMaxima;
        String barra = "[";

        for (int i = 0; i < 10; i++) {
            if (i < blocos) {
                barra += "#";
            } else {
                barra += "-";
            }
        }

        barra += "]";
        return barra;
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (guerreiro.getVida() > 0 && orc.getVida() > 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
                guerreiro.atacar(orc);
                mensagem = "Voce atacou o Orc!";

                if (orc.getVida() <= 0) {
                    mensagem = "VITORIA! O Orc foi derrotado.";
                } else {
                    orc.atacar(guerreiro);
                    mensagem += " O Orc contra-atacou!";
                    if (guerreiro.getVida() <= 0) {
                        mensagem = "DERROTA! Voce foi eliminado.";
                    }
                }
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
                guerreiro.curar(30);
                mensagem = "Voce recuperou vida!";

                orc.atacar(guerreiro);
                mensagem += " O Orc atacou!";
                
                if (guerreiro.getVida() <= 0) {
                    mensagem = "DERROTA! Voce foi eliminado.";
                }
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
                guerreiro = new Personagem("Guerreiro", 100, 20);
                orc = new Personagem("Orc", 80, 15);
                mensagem = "Nova batalha iniciada! Aguardando comando...";
            }
        }

        batch.begin();
        batch.draw(fundo, 0, 0);
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(0, 0, 0, 0.7f);

        shape.rect(100, 380, 250, 180);
        shape.rect(580, 380, 250, 180);
        shape.rect(100, 200, 730, 90);
        shape.rect(100, 90, 730, 90);

        shape.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();

        fonte.getData().setScale(1.5f);
        fonte.draw(batch, "ARENA RPG", 350, 620);

        fonte.getData().setScale(1.1f);

        fonte.draw(batch, "JOGADOR", 120, 540);
        fonte.draw(batch, guerreiro.getNome(), 120, 500);
        fonte.draw(batch, "Vida: " + guerreiro.getVida(), 120, 470);
        fonte.draw(batch, barraVida(guerreiro.getVida(), 100), 120, 440);
        fonte.draw(batch, "Ataque: " + guerreiro.getAtaque(), 120, 410);

        fonte.draw(batch, "VS", 430, 470);

        fonte.draw(batch, "INIMIGO", 600, 540);
        fonte.draw(batch, orc.getNome(), 600, 500);
        fonte.draw(batch, "Vida: " + orc.getVida(), 600, 470);
        fonte.draw(batch, barraVida(orc.getVida(), 80), 600, 440);
        fonte.draw(batch, "Ataque: " + orc.getAtaque(), 600, 410);

        fonte.draw(batch, "ACOES", 120, 270);
        if (guerreiro.getVida() > 0 && orc.getVida() > 0) {
            fonte.draw(batch, "[1] Atacar     [2] Curar (+30 HP)", 120, 240);
        } else {
            fonte.draw(batch, "[R] Reiniciar Jogo", 120, 240);
        }

        fonte.draw(batch, "ULTIMA ACAO", 120, 160);
        fonte.draw(batch, mensagem, 120, 130);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fonte.dispose();
        fundo.dispose();
        shape.dispose();
    }
}
