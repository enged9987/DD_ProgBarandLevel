package gdx.fontprog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SprPlatform extends Sprite {

    private float fX, fY, fVx, fVy;
    String sFile;
    Texture txImg;
    private Sprite sprImg;

    public SprPlatform(String _sFile, float _fX, float _fY) {
        sFile = _sFile;
        fX = _fX;
        fY = _fY;
        System.out.print(fY);
        txImg = new Texture(sFile);
        sprImg = new Sprite(txImg);
    }

    void update(float nDinoX, float _fVx) {
        //_fVx = fVx;
        //fX = 1000;
        
        fX --;    

        if (fX <= (0 - sprImg.getWidth())) {
            fX = (nDinoX + (Gdx.graphics.getWidth()));
            fY = (((Gdx.graphics.getHeight() - nDinoX) / 3) * ((int) (Math.random() * 3 + 0)));
        }
    }

    void setY(int _nDinoHei) {
        fY = (((Gdx.graphics.getHeight() - _nDinoHei) / 3) * ((int) (Math.random() * 3 + 0)));

    }

    public Sprite getSprite() {
        return sprImg;
    }

    //@Override
    public float getX() {
        return fX;
    }

    //@Override
    public float getY() {
        return fY;
    }
}
