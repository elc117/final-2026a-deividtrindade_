package io.github.some_example_name;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private BitmapFont fonte;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont();
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        batch.begin();

        fonte.draw(batch, "RPG por Turno", 100, 400);
        fonte.draw(batch, "Guerreiro - Vida: 100 - Ataque: 20", 100, 350);
        fonte.draw(batch, "Orc - Vida: 80 - Ataque: 15", 100, 320);
        fonte.draw(batch, "Versao inicial do projeto", 100, 270);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fonte.dispose();
    }
}