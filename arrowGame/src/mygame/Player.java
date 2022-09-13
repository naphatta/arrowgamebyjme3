
package mygame;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Player {
    
    private final Node    model;
    private int     score;
    private int     level;
    private boolean isDead;
    private boolean isWin=false;
    
    public Player(Node model) {
        this.model = model;
    }

    public Node getModel() {
        return model;
    }
    
    public void die() {
        isDead = true;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void win() {
        isWin = true;
    }
    public boolean isWin() {
        return isWin;
    }
    
    public int getScore() {
        return score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
        
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public void moveToBoss() {
        Vector3f playerSpot = model.getLocalTranslation();
        model.localToWorld(playerSpot,new Vector3f(0,0,0));
    }
    
    public void reset() {
        level     = 0;
        score     = 0;
        isDead    = false;
    }
    
}
