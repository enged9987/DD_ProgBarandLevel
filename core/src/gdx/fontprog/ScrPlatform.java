package gdx.fontprog;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class ScrPlatform implements Screen, InputProcessor {

    Game game;
    SpriteBatch batch;
    Texture txDino, txPlat;
    SprDino sprDino;
    Sprite sprBack;
    SprPlatform sprPlatform;
    int nScreenWid = Gdx.graphics.getWidth(), nDinoHei, nScreenX, nLevelCount = 1;
    float fScreenWidth = Gdx.graphics.getWidth(), fScreenHei = Gdx.graphics.getHeight(), fDist, fVBackX, fProgBar = 0;
    private float fVy;
    private float fVx;
    OrthographicCamera camBack;
    ShapeRenderer shape = new ShapeRenderer();
    BitmapFont textFont, textFontLevel;
    String sLevel = "Level " + nLevelCount;

    public ScrPlatform(Game _game) {
        SetFont();
        game = _game;
        batch = new SpriteBatch();
        txDino = new Texture("Dinosaur.png");
        txPlat = new Texture("Platform.png");
        sprPlatform = new SprPlatform("Platform.png", 0, 0);
        sprDino = new SprDino("Dinosaur.png", (fScreenWidth / 2) - (txDino.getWidth() / 2), 20);
        sprBack = new Sprite(new Texture(Gdx.files.internal("world.jpg")));
        sprBack.setSize(fScreenWidth, fScreenHei);
        Gdx.input.setInputProcessor((this));
        Gdx.graphics.setWindowedMode(800, 500);
        float aspectratio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        camBack = new OrthographicCamera(fScreenWidth * aspectratio, fScreenHei);
        camBack.position.set(fScreenWidth / 2, fScreenHei / 2, 0);
        nDinoHei = txDino.getHeight();
        sprPlatform.setX(nScreenWid);        
        sprPlatform.setY(nDinoHei);
    }

    public void SetFont() {
        FileHandle fontFile = Gdx.files.internal("Woods.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.color = Color.BLACK;
        textFont = generator.generateFont(parameter);
        parameter.size = 18;
        textFontLevel = generator.generateFont(parameter);

        generator.dispose();
    }

    @Override
    public void show() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camBack.update();
        sprDino.update(fVx, fVy);
        sprPlatform.update(sprDino.getX(), fVx);
        sprDino.HitDetection();
        batch.begin();
        if ((nScreenX < -Gdx.graphics.getWidth() || nScreenX > Gdx.graphics.getWidth())) {
            nScreenX = 0;
        }
        batch.setProjectionMatrix(camBack.combined);
        batch.draw(sprBack, nScreenX, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(sprBack, nScreenX - Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(sprBack, nScreenX + Gdx.graphics.getWidth(), 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(sprDino.getSprite(), sprDino.getX(), sprDino.getY());
        batch.draw(sprPlatform.getSprite(), sprPlatform.getX(), sprPlatform.getY());
        textFontLevel.draw(batch, sLevel, fScreenWidth / 3, (fScreenHei / 10) * 9);

        if (sprBack.getX() > 0) {
            nScreenX += fVx;
        }
        nScreenX -= fVx;
        batch.end();

        shape.begin(ShapeType.Filled);
        shape.setColor(Color.BLACK);
        shape.rect((fScreenWidth - (fScreenWidth / 3) * 2) / 2, fScreenHei / 120, (fScreenWidth / 3) * 2, fScreenWidth / 40);
        shape.setColor(Color.WHITE);
        shape.rect((fScreenWidth - (fScreenWidth / 3) * 2) / 2, fScreenHei / 120, fProgBar, fScreenWidth / 40);
        shape.end();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (fProgBar <= 0) {
                fProgBar = 0;

            } else {
                fProgBar -= .7;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            fProgBar += .7;
        }
        if (fProgBar >= ((fScreenWidth / 3) * 2)){
            nLevelCount ++;
            fProgBar = 0;
            sLevel = "Level " + nLevelCount;
        }
    }

    @Override
    public void resize(int i, int i1) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dispose() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E) {
            System.exit(3);
        } else if (keycode == Input.Keys.UP) {
            fVy = 2;

        } else if (keycode == Input.Keys.DOWN) {
            fVy = -2;
        } else if (keycode == Input.Keys.LEFT) {

            fVx = -2;
        } else if (keycode == Input.Keys.RIGHT) {
            fVx = 2;
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.UP) {
            fVy = 0;
        } else if (keycode == Input.Keys.DOWN) {
            fVy = 0;
        } else if (keycode == Input.Keys.LEFT) {
            fVx = 0;
        } else if (keycode == Input.Keys.RIGHT) {
            fVx = 0;
        }
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean scrolled(int i) {
        return false;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
