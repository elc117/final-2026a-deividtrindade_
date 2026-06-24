package io.github.some_example_name;

import java.util.Random;

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
    private Texture spriteMago;
    private Texture spriteAssassino;
    private Texture spriteDragao;
    private Texture bolaFogo;

    private Personagem grupoHerois;
    private Personagem dragao;
    private String mensagem;
    private int estadoTela;
    
    private int xGuerreiro;
    private int xMago;
    private int xAssassino;
    
    private int pontuacao;
    private final int META_PONTUACAO = 300;
    
    private boolean dragaoAtacando;
    private int bolaFogoX;
    
    private boolean guerreiroAtacando;
    private int bolaFogoGuerreiroX;
    
    private boolean buffAtivo;
    private boolean aguardandoDragao;
    private float timerDelay;
    private int acaoAtual;
    
    private float rDragao, gDragao, bDragao;
    private Random random;

    @Override
    public void create() {
        batch = new SpriteBatch();
        fonte = new BitmapFont(); 
        shape = new ShapeRenderer();
        random = new Random();

        ceu = new Texture("1_ceu.png");
        arvoresFundo = new Texture("2_arvores_fundo.png");
        arvoresMeio = new Texture("3_arvores_meio.png");
        arvoresFrente = new Texture("4_arvores_frente.png");
        chao = new Texture("5_chao.png");
        
        spriteGuerreiro = new Texture("guerreiro.png");
        spriteMago = new Texture("mago.png");
        spriteAssassino = new Texture("assassino.png"); 
        spriteDragao = new Texture("dragao.png");
        bolaFogo = new Texture("fogo.png");

        grupoHerois = new Personagem("Grupo Heróis", 100, 20);
        dragao = new Personagem("Dragão Ancião", 200, 18);
        
        rDragao = gDragao = bDragao = 1f;

        mensagem = "Aguardando comando...";
        estadoTela = 0;
        
        xGuerreiro = 190;
        xMago = 120;
        xAssassino = 50;
        
        pontuacao = 0; 
        dragaoAtacando = false;
        guerreiroAtacando = false;
        buffAtivo = false;
        aguardandoDragao = false;
        acaoAtual = 0;
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

        if (xGuerreiro > 190) xGuerreiro -= 5;

        if (aguardandoDragao) {
            timerDelay -= Gdx.graphics.getDeltaTime();
            if (timerDelay <= 0) {
                aguardandoDragao = false;
                acaoAtual = 0;
                dragaoAtacando = true;
                bolaFogoX = 600;
                mensagem = "O Dragão lança uma bola de fogo!";
            }
        }

        if (dragaoAtacando) {
            bolaFogoX -= 8; 
            if (bolaFogoX <= 200) { 
                dragaoAtacando = false;
                dragao.atacar(grupoHerois);
                if (grupoHerois.getVida() <= 0) {
                    mensagem = "DERROTA! O grupo foi eliminado.";
                } else {
                    mensagem = "Você sobreviveu! Sua vez de agir.";
                }
            }
        }
        
        if (guerreiroAtacando) {
            bolaFogoGuerreiroX += 12; 
            if (bolaFogoGuerreiroX >= 600) { 
                guerreiroAtacando = false;
                grupoHerois.atacar(dragao);
                if (buffAtivo) {
                    grupoHerois.atacar(dragao); 
                    buffAtivo = false;
                    pontuacao += 30;
                    mensagem = "CRÍTICO! O rebote causou Dano Duplo no Dragão!";
                } else {
                    pontuacao += 15;
                    mensagem = "O Guerreiro acertou a magia de volta no Dragão!";
                }

                if (dragao.getVida() <= 0) {
                    pontuacao += 100;
                }

                if (pontuacao >= META_PONTUACAO) {
                    mensagem = "VITÓRIA SUPREMA! Você fez " + pontuacao + " pontos e salvou o reino!";
                } else if (dragao.getVida() <= 0) {
                    mensagem = "Onda concluída! Curando o grupo... O próximo Dragão chega em breve!";
                    grupoHerois.curar(40);
                    dragao = new Personagem("Dragão Ancião", 200, 18);
                    rDragao = 0.5f + random.nextFloat() * 0.5f;
                    gDragao = 0.5f;
                    bDragao = 0.8f + random.nextFloat() * 0.2f;
                    aguardandoDragao = true;
                    timerDelay = 4.0f;
                } else {
                    aguardandoDragao = true;
                    timerDelay = 1.0f; 
                }
            }
        }

        if (estadoTela == 0) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || isClicado(260, 300, 400, 120)) {
                grupoHerois = new Personagem("Grupo Heróis", 100, 20);
                dragao = new Personagem("Dragão Ancião", 200, 18);
                estadoTela = 1;
                pontuacao = 0;
                dragaoAtacando = false;
                guerreiroAtacando = false;
                buffAtivo = false;
                acaoAtual = 0;
                
                aguardandoDragao = true;
                timerDelay = 0.5f;
                mensagem = "ALERTA: O Dragão toma a iniciativa!";
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
            fonte.draw(batch, "Pressione [ENTER] ou Clique aqui para iniciar", 280, 350); 
            batch.end();

        } else if (estadoTela == 1) {
            if (grupoHerois.getVida() > 0 && pontuacao < META_PONTUACAO && !dragaoAtacando && !aguardandoDragao && !guerreiroAtacando) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1) || isClicado(30, 80, 200, 30)) {
                    guerreiroAtacando = true;
                    bolaFogoGuerreiroX = 260; 
                    xGuerreiro = 220; 
                    acaoAtual = 1;
                    mensagem = "Guerreiro usa a espada para rebater o fogo!";
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) || isClicado(260, 80, 200, 30)) {
                    grupoHerois.curar(30);
                    acaoAtual = 2;
                    mensagem = "Mago conjurou cura para todo o grupo!";
                    aguardandoDragao = true;
                    timerDelay = 1.0f;
                } else if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) || isClicado(490, 80, 200, 30)) {
                    if (!buffAtivo) {
                        buffAtivo = true;
                        acaoAtual = 3;
                        mensagem = "BUFF aplicado! Ataque agora (Turno Livre)!";
                    } else {
                        mensagem = "O BUFF já está ativo! Use o Guerreiro para atacar.";
                    }
                }
            } else if (grupoHerois.getVida() <= 0 || pontuacao >= META_PONTUACAO) {
                if (Gdx.input.isKeyJustPressed(Input.Keys.R) || isClicado(30, 80, 300, 30)) {
                    estadoTela = 0;
                }
            }

            batch.begin();
            batch.draw(ceu, 0, 0, 960, 720);
            batch.draw(arvoresFundo, 0, 0, 960, 720);
            batch.draw(arvoresMeio, 0, 0, 960, 720);
            batch.draw(arvoresFrente, 0, 0, 960, 720);
            batch.draw(chao, 0, 0, 960, 200);

            if (buffAtivo) {
                batch.setColor(1f, 0.3f, 0.3f, 1f); 
            } else {
                batch.setColor(1f, 1f, 1f, 1f); 
            }
            batch.draw(spriteAssassino, xAssassino, 150, 150, 150); 
            
            if (acaoAtual == 2 && aguardandoDragao) {
                batch.setColor(0.3f, 1f, 0.3f, 1f); 
            } else {
                batch.setColor(1f, 1f, 1f, 1f); 
            }
            batch.draw(spriteMago, xMago, 150, 150, 150); 
            batch.draw(spriteGuerreiro, xGuerreiro, 150, 150, 150); 
            
            batch.setColor(1f, 1f, 1f, 1f); 

            if (dragao.getVida() <= 0) {
                batch.setColor(0.4f, 0.2f, 0.2f, 1f); 
            } else if (pontuacao >= META_PONTUACAO) {
                batch.setColor(0.4f, 0.2f, 0.2f, 1f);
            } else {
                batch.setColor(rDragao, gDragao, bDragao, 1f);
            }
            batch.draw(spriteDragao, 550, 80, 280, 280, 0, 0, spriteDragao.getWidth(), spriteDragao.getHeight(), true, false);
            batch.setColor(1f, 1f, 1f, 1f); 

            if (dragaoAtacando) {
                batch.draw(bolaFogo, bolaFogoX, 180, 60, 60); 
            }
            
            if (guerreiroAtacando) {
                batch.draw(bolaFogo, bolaFogoGuerreiroX, 180, 60, 60, 0, 0, bolaFogo.getWidth(), bolaFogo.getHeight(), true, false); 
            }
            batch.end();

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(0, 0, 0, 0.6f);
            shape.rect(10, 580, 250, 120); 
            shape.rect(700, 580, 250, 120); 
            shape.rect(10, 10, 940, 120); 
            shape.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            shape.begin(ShapeRenderer.ShapeType.Filled);
            shape.setColor(0.3f, 0.3f, 0.3f, 1f);
            shape.rect(30, 600, 210, 20);
            shape.setColor(0.2f, 0.8f, 0.2f, 1f);
            float hpGuerreiro = Math.max(0, ((float) grupoHerois.getVida() / 100) * 210);
            shape.rect(30, 600, hpGuerreiro, 20);
            shape.setColor(0.3f, 0.3f, 0.3f, 1f);
            shape.rect(720, 600, 210, 20);
            shape.setColor(0.8f, 0.2f, 0.2f, 1f);
            float hpDragao = Math.max(0, ((float) dragao.getVida() / 200) * 210);
            shape.rect(720, 600, hpDragao, 20);
            shape.end();

            batch.begin();
            fonte.getData().setScale(0.8f);
            fonte.draw(batch, "GRUPO DE HERÓIS", 30, 680);
            fonte.draw(batch, "Vida Total: " + grupoHerois.getVida(), 30, 645);
            
            if (buffAtivo) {
                fonte.setColor(1f, 0.4f, 0.4f, 1f);
                fonte.draw(batch, ">> BUFF DE DANO ATIVO <<", 30, 560);
                fonte.setColor(1f, 1f, 1f, 1f);
            }
            
            fonte.getData().setScale(1.2f);
            fonte.draw(batch, "PONTOS: " + pontuacao + " / " + META_PONTUACAO, 400, 700);
            fonte.getData().setScale(0.8f);
            
            fonte.draw(batch, "VS", 465, 640);
            fonte.draw(batch, dragao.getNome().toUpperCase(), 720, 680);
            fonte.draw(batch, "Vida Total: " + dragao.getVida(), 720, 645);

            if (grupoHerois.getVida() > 0 && pontuacao < META_PONTUACAO) {
                if (!dragaoAtacando && !aguardandoDragao && !guerreiroAtacando) {
                    fonte.draw(batch, "[1] Guerreiro (Rebater)", 30, 100);
                    fonte.draw(batch, "[2] Mago (Curar)", 260, 100);
                    fonte.draw(batch, "[3] Assassino (Buff)", 490, 100);
                } else {
                    fonte.draw(batch, "Aguarde a animacao...", 30, 100);
                }
            } else {
                fonte.draw(batch, "[R] Voltar ao Menu (Clique aqui)", 30, 100);
            }
            fonte.draw(batch, "Log: " + mensagem, 30, 50);

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
        spriteMago.dispose();
        spriteAssassino.dispose();
        spriteDragao.dispose();
        bolaFogo.dispose();
    }
}