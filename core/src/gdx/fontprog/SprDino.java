package gdx.fontprog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SprDino {

    private float fX, fY, fVx, fVy, fXOrL, fXOrR, fYOr;
    String sFile;
    Texture txImg;
    private Sprite sprImg;

    public SprDino(String _sFile, float _fX, float _fY) {
        sFile = _sFile;
        fX = _fX;
        fY = _fY;
        txImg = new Texture(sFile);
        sprImg = new Sprite(txImg);
    }

    void update(float _fVx, float _fVy) {
        fVx = _fVx;
        fVy = _fVy;
        fX += fVx;
        fY += fVy;
    }
    void HitDetection(){
        fXOrL = (fX-fVx);
        fXOrR = (fX-fVx);
        fYOr = (fY - fVy);
        if(fX + (sprImg.getWidth()*2.4) >= Gdx.graphics.getWidth()){
            fX = fXOrL;  
            
        }else if(fX - (sprImg.getWidth()/2) <= 0){

            fX = fXOrR;
        }
        if (fY <= 20){                       
            fY = fYOr;
        }        
        
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
