
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.Random;

public class SceneManager {
    
    private final Node         scene;
    private final Node         walls;
    private final Node         floor ;
    private final Node         Obstruction ;
    private final Node         Monster ;
    private final AssetManager assetManager;
    private final float        floorWidth;
    private final float        floorLength;
    private final wall         wall11;
    private Player player;
    private final Node rootNode;
    private int ch=1;
    private int chwall;
    private boolean isStop;
    public static final Quaternion PITCH270 = new Quaternion().fromAngleAxis(FastMath.PI*3/2, new Vector3f(1,0,0));
    
    public SceneManager(Node rootNode, AssetManager assetManager) {
        
        this.assetManager = assetManager;
        this.rootNode     = rootNode;
        
        scene             = new Node();
        floor             = new Node("Floor");
        Obstruction       = new Node("Obstruction");
        Monster           = new Node("Monster");
        floorWidth        = 40;
        floorLength       = 40;
        initGround();
        wall11 =new wall(rootNode,assetManager);

        
        Random rand = new Random();
        int r  =  rand.nextInt(20) + 1;
        int r1 =  rand.nextInt(20) + 1;
        int r2 =  rand.nextInt(20) + 1;
        int r3 =  rand.nextInt(20) + 1;
        
        Node o1 = initObstruction(10,  3, -5);
        Node o2 = initObstruction(55,  3, 5);
        Node o3 = initObstruction(100, 3, 0);
        Node o4 = initObstruction(145, 3, 5);
        Node o5 = initObstruction(190,  3, -5);
        Node o6 = initObstruction(235,  3, 5);
        Node o7 = initObstruction(280, 3, 0);
        Node o8 = initObstruction(325, 3, 5);
        
        Node m1 = initMonster(10,  3, r);
        Node m2 = initMonster(55,  3, r1);
        Node m3 = initMonster(100, 3, r2);
        Node m4 = initMonster(145, 3, r3);
        
        walls=wall11.getwalls();
        
        scene.attachChild(floor );
        scene.attachChild(Obstruction);
        scene.attachChild(Monster);
        rootNode.attachChild(scene);

        Obstruction.attachChild(o1); 
        Obstruction.attachChild(o2); 
        Obstruction.attachChild(o3);
        Obstruction.attachChild(o4);
        Obstruction.attachChild(o5); 
        Obstruction.attachChild(o6); 
        Obstruction.attachChild(o7);
        Obstruction.attachChild(o8);
        
        Monster.attachChild(m1); 
        Monster.attachChild(m2); 
        Monster.attachChild(m3);
        Monster.attachChild(m4);
    }
    
    public void setPlayer(Player player) {
        this.player       = player;
    }
    
    private void initGround() {
        
        Box b = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom1 = new Geometry("Box", b);             
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Green);
        geom1.setMaterial(mat1);      
        
        Box c = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom2 = new Geometry("Box", c);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Cyan);
        geom2.setMaterial(mat2);   
        
        Box d = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom3 = new Geometry("Box", d);
        Material mat3 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat3.setColor("Color", ColorRGBA.Yellow);
        geom3.setMaterial(mat3);  
        
        Box e = new Box(floorWidth/2, 1, floorLength/2);
        Geometry geom4 = new Geometry("Box", e);
        Material mat4 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat4.setColor("Color", ColorRGBA.Magenta);
        geom4.setMaterial(mat4);
        
        geom1.setLocalTranslation(0, -5, -10);//-10 -30 
        geom2.setLocalTranslation(0, -5, -55);//-35 -55
        geom3.setLocalTranslation(0, -5, -100);  
        geom4.setLocalTranslation(0, -5, -145); 
        
        floor.attachChild(geom1);
        floor.attachChild(geom2);
        floor.attachChild(geom3);
        floor.attachChild(geom4);
        
    }
    
    private Node initObstruction(float distance, float gap, float offset) {
        float wallWidth = (floorWidth - gap)/2;
        
        float leftWall  = wallWidth-offset;
        
        Node obstruction1 = new Node();
        
        Box b = new Box(1f, 1, 1);
        Geometry geom1 = new Geometry("Box", b);            
        
        geom1.scale(gap/2,5,1);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        
        geom1.setMaterial(mat);                   
        geom1.setLocalTranslation(-floorWidth/2+leftWall+gap/2, 0, 0);
        obstruction1.setLocalTranslation(0, 0, -distance);      
        obstruction1.attachChild(geom1);
        
        return obstruction1;
        
    }
    
    private Node initMonster(float distance, float gap, float offset) {
        float wallWidth = floorWidth/2 - gap/2;
        
        float leftWall  = wallWidth-offset;
        
        Node monster = new Node();
        
        Spatial monmodel = assetManager.loadModel("Models/scarecrow.j3o");          
        monmodel.scale(1,1,1);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Orange);
        
        monmodel.setMaterial(mat);  
        monmodel.rotate(PITCH270);
        
        monmodel.setLocalTranslation(-floorWidth/2+leftWall+gap/2, 0, 0);      
        monster.setLocalTranslation(0, -4.6f,3.5f-distance);      
        monster.attachChild(monmodel);
        
        return monster;
        
    }
    
    private void moveGround(float tpf) {
        
        float score = player.getScore();
        int level = player.getLevel();
        
        for (int i = 0; i < floor .getChildren().size(); i++) {
            
            Spatial  cg = floor .getChild(i);
            Vector3f cs = cg.getLocalTranslation();
            
            cg.move(0, 0, (10+player.getLevel()*2)*tpf);
            
            if (cs.z > 25) {
                level+=1;
                player.setLevel(level);
                cg.setLocalTranslation(cs.x, cs.y, -155);
            }
        }
    }
    
    private void moveWalls(float tpf) {
        
        int score = player.getScore();
        
        for (int i = 0; i < walls.getChildren().size(); i++) {
            chwall=(player.getLevel())%8;//wall.size
            Spatial  cw = walls.getChild(i);
            Vector3f cs = cw.getLocalTranslation();
            
            cw.move(0, 0, (10+player.getLevel()*2)*tpf);
            if (cs.z > 25 ) {
                Vector3f playerSpot = player.getModel().getLocalTranslation();
                //update score
                if(player.getLevel()%4==0&&playerSpot.x<5) chwall+=8;//wall.size
                if(player.getLevel()%4==1&&playerSpot.x<-5) chwall+=8;
                if(player.getLevel()%4==2&&playerSpot.x<0) chwall+=8;
                if(player.getLevel()%4==3&&playerSpot.x<-5) chwall+=8;
                
                cw.setLocalTranslation(cs.x, cs.y, -335);
                if(wall11.getscore(chwall)==0) 
                    score=score*2;
                else 
                    score=score+wall11.getscore(chwall)/2;
                player.setScore(score);
            }           
        }
    }
    
    private void moveMonster(float tpf) {
        float score = player.getScore();
        
        for (int i = 0; i < Monster.getChildren().size(); i++) {
            Spatial  cm = Monster.getChild(i);
            Vector3f cs = cm.getWorldTranslation();
            
            cm.move(0, 0, (10+player.getLevel()*2)*tpf);
            
            if(ch==1 && cs.x>30){
                ch=0;
                cm.move(-5*tpf, 0, 0);
            }
            else if(ch==1){
                cm.move(5*tpf, 0, 0);
            }
            else if(ch==0 && cs.x< -5){
                cm.move(5*tpf, 0, 0);
                ch=1;
            }
            else{
                cm.move(-5*tpf, 0, 0);
            }
            
            if (cs.z > 25) {
                cm.setLocalTranslation(cs.x, cs.y, -155);
            }
        }
    }
    
    private void moveObstruction(float tpf) {
        float score = player.getScore();
        
        for (int i = 0; i < Obstruction.getChildren().size(); i++) {
            Spatial  co = Obstruction.getChild(i);
            Vector3f cs = co.getLocalTranslation();
            
            co.move(0, 0, (10+player.getLevel()*2)*tpf);
            
            if (cs.z > 25) {
                co.setLocalTranslation(cs.x, cs.y, -335);
            }            
        }
    }
    
    public void GameClear(){
        stop();
        int scoreForSet=player.getScore();
        player.getModel().setLocalTranslation(new Vector3f(0,0,25));
        player.setLevel(10);
        player.setScore(scoreForSet);
        Box b = new Box(1, 1, 1);
        Geometry geom1 = new Geometry("Box", b);                 
        
        geom1.scale(20,10,1);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", 
                assetManager.loadTexture("Textures/complete.jpg"));
        
        geom1.setMaterial(mat);                         
        geom1.setLocalTranslation(0, -2, 5);
        
        Obstruction.attachChild(geom1); 
        scene.attachChild(Obstruction);
        rootNode.attachChild(scene);
        
        player.win();
      
    }
    
    public void GameOver(){
        stop();
        int levelForSet = player.getLevel();
        int scoreForSet = player.getScore();
        
        player.getModel().setLocalTranslation(new Vector3f(0,0,25));
        player.setLevel((levelForSet+1)/2);
        player.setScore(scoreForSet);
        Box b = new Box(1, 1, 1);
        Geometry geom1 = new Geometry("Box", b);                 
        
        geom1.scale(20,10,1);
    
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", 
                assetManager.loadTexture("Textures/game-over.gif"));
        
        geom1.setMaterial(mat);                         
        geom1.setLocalTranslation(0, -2, 5);
        
        moveWalls(15);
        moveObstruction(15);
        moveMonster(15);
        moveGround(15);
        
        Obstruction.attachChild(geom1); 
        scene.attachChild(Obstruction);
        rootNode.attachChild(scene);
        
    }
    
    public void stop(){
        isStop = !isStop; //switch state
    }
    
    public boolean isStop(){
        return isStop;
    }
    
    public void update(float tpf) {
        if (isStop) return;
        if (player.isDead()) GameOver();
        if (player.getLevel()==10) GameClear();
        moveWalls(tpf);
        moveObstruction(tpf);
        moveMonster(tpf);
        moveGround(tpf);
    }
    
}
