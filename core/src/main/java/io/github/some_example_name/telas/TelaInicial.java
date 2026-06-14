package io.github.some_example_name.telas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

import io.github.some_example_name.model.entidades.Personagem;

public class TelaInicial implements Screen {

    private Personagem guerreiro;
    private Personagem orc;

    public TelaInicial() {
        guerreiro = new Personagem("Guerreiro", 100, 20);
        orc = new Personagem("Orc", 80, 15);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}