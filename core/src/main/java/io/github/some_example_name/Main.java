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

        fonte.getData().setScale(1.5f);
        fonte.draw(batch, "RPG por Turno", 100, 620);

        fonte.getData().setScale(1.1f);
        fonte.draw(batch, "Versao inicial publicada para teste web", 100, 560);

        fonte.draw(batch, "Personagem do jogador:", 100, 500);
        fonte.draw(batch, "Guerreiro - Vida: 100 - Ataque: 20", 100, 470);

        fonte.draw(batch, "Inimigo:", 100, 420);
        fonte.draw(batch, "Orc - Vida: 80 - Ataque: 15", 100, 390);

        fonte.draw(batch, "Sistema ja implementado:", 100, 330);
        fonte.draw(batch, "- Criacao de personagem", 100, 300);
        fonte.draw(batch, "- Sistema de dano", 100, 270);
        fonte.draw(batch, "- Ataque entre personagens", 100, 240);
        fonte.draw(batch, "- Inimigo inicial", 100, 210);

        fonte.draw(batch, "Proximos passos: batalha, habilidades e pontuacao", 100, 150);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        fonte.dispose();
    }
}