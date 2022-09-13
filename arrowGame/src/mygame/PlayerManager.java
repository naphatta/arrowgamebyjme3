
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class PlayerManager {
    
    private final Player             player;
    private final InteractionManager interactionManager;
    private final SceneManager       sceneManager;
    private final Camera             camera;
    private final Node               walls;
    private final Node               floor;
    private final Node               obstruction;
    private final Node               Monster ;
    public static final Quaternion PITCH180 = new Quaternion().fromAngleAxis(FastMath.PI  ,   new Vector3f(1,0,0));
    
    public PlayerManager(Node rootNode, InputManager inputManager, AssetManager assetManager, Camera camera,SceneManager sceneManager) {
        
        this.sceneManager=sceneManager;
        
        interactionManager = new InteractionManager(inputManager);
        player             = new Player(createPlayerModel(assetManager));
        this.camera        = camera;
        walls              = (Node) rootNode.getChild("Walls");
        floor              = (Node) rootNode.getChild("Floor");
        obstruction        = (Node) rootNode.getChild("Obstruction");
        Monster            = (Node) rootNode.getChild("Monster");
        player.getModel().setLocalTranslation(new Vector3f(0,3,21));// 0 -3 0
        rootNode.attachChild(player.getModel());
    }
    
    private Node createPlayerModel(AssetManager assetManager) {  
        
        Node model = new Node("character node");
        Spatial arrow = assetManager.loadModel("Models/ArrowV.2.j3o");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.BlackNoAlpha);
        arrow.setMaterial(mat);
        arrow.rotate(PITCH180);
        
        model.scale(0.15f);
        model.attachChild(arrow);
    
        
        return model;

    }
    
    public Player getPlayer() {
        return player;
    }

    private void movePlayer(float tpf) {
        
        if (player.isDead()) return;
        
        if (interactionManager.getLeft()) {
            player.getModel().move(-20*tpf,0,0);
        }
        
        if (interactionManager.getRight()) {
            player.getModel().move(20*tpf,0,0);
        }
        
        if (interactionManager.getUp()) {
            player.getModel().move(0,10*tpf,0);
        }
        
        if (interactionManager.getDown()) {
            player.getModel().move(0,-10*tpf,0);
        }
        
    }
    
    private void updateCamera() {
        Vector3f playerSpot = player.getModel().getLocalTranslation();
        camera.setLocation(new Vector3f(playerSpot.x,playerSpot.y/2+5,playerSpot.z+15));
        camera.lookAt(playerSpot.clone().setY(1), new Vector3f(0,1,0));
    }
    
    private void checkCollision() {
        CollisionResults results = new CollisionResults();
        obstruction.collideWith(player.getModel().getWorldBound(), results);
        if (results.size() > 0) {
            player.die();
        }
        
        CollisionResults result1 = new CollisionResults();
        Monster.collideWith(player.getModel().getWorldBound(), result1);
        if (result1.size() > 0) {
            player.die();
        }
    }
    
    private void checkFloor(float tpf) {
        Vector3f playerSpot = player.getModel().getLocalTranslation();
        if (playerSpot.x<-19.5f) {
            player.getModel().move(0.3f,0,0);
        }
        if (playerSpot.x>19.5f) {
            player.getModel().move(-0.3f,0,0);
        }
        if (playerSpot.y<-3.5f) {
            player.getModel().move(0,0.3f,0);
        }
        if (playerSpot.y>4.5f) {
            player.getModel().move(0,-0.3f,0);
        }
    }
    
    private void checkReset() {
        
        if (!interactionManager.getJump()) return;
        
        sceneManager.stop();
        
        if (interactionManager.getJump()){
            sceneManager.stop();
        }
        
        player.getModel().setLocalTranslation(new Vector3f(0,3,21));
        player.reset();
        
        int z = -10;
        
        for (int i = 0; i < walls.getChildren().size(); i++) {
            if(i==walls.getChildren().size()/2){
            z=-10;
        }
            Vector3f wallSpot = walls.getChild(i).getLocalTranslation();
            walls.getChild(i).setLocalTranslation(wallSpot.x,wallSpot.y,z);
            z-=45;
        }
        
        z = -10;
        
        for (int i = 0; i < obstruction.getChildren().size(); i++) {
            Vector3f obstructionSpot = obstruction.getChild(i).getLocalTranslation();
            obstruction.getChild(i).setLocalTranslation(obstructionSpot.x,obstructionSpot.y,z);
            z-=45;
        }
        
        z = -10;
        
        for (int i = 0; i < floor.getChildren().size(); i++) {
            floor.getChild(i).setLocalTranslation(0,-5,z);
            z-=45;
        }
        
        z = -10;
        
        for (int i = 0; i <Monster.getChildren().size(); i++) {
            Vector3f MonsterSpot = Monster.getChild(i).getLocalTranslation();
            Monster.getChild(i).setLocalTranslation(MonsterSpot.x,MonsterSpot.y,3+z);
            z-=45;
        }          
        
    }
    
    public void update(float tpf) {
        
        if(player.getScore()<0){
            player.die();
        }
        updateCamera();
        checkCollision();
        checkFloor(tpf);
        movePlayer(tpf);
        if (player.isDead()){ 
            checkReset();
        }
        if (player.isWin()){
            checkReset();
        }
    }
    
}
