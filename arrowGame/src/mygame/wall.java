
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.Random;

public class wall {
    
    public  final Node         walls;
    public  Node[]wall     =   new Node[16];
    private final Node         scene;
    private final AssetManager assetManager;
    private final float        floorWidth;
    private final float        floorLength;
    public int[] scoreBeforeRandom={2,4,6,8,-2,-4,-4,-6,-6,0,2,4,6,8,-2,-4,-4,-6,-6,0};
    public int[] score=new int[20];
    public String[] pathForBlue={"Textures/+2b.png","Textures/+4b.png","Textures/+6b.png","Textures/+8b.png","Textures/-2b.png","Textures/-4b.png","Textures/-4b.png","Textures/-6b.png","Textures/-6b.png","Textures/x4b.png"};
    public String[] pathForRed={"Textures/+2r.png","Textures/+4r.png","Textures/+6r.png","Textures/+8r.png","Textures/-2r.png","Textures/-4r.png","Textures/-4r.png","Textures/-6r.png","Textures/-6r.png","Textures/x4r.png"};

    public wall(Node rootNode,AssetManager assetManager){
        
        this.assetManager = assetManager;
        scene             = new Node();
        walls             = new Node("Walls");
        floorWidth        = 40;
        floorLength       = 40;
        
        Random rand =   new Random();
        int n1      =   rand.nextInt(4);
        wall[0]     =   initRWall(10,  3, -5,pathForBlue[n1]);
        score[0]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[1]     =   initRWall(55,  3, 5,pathForBlue[n1]);
        score[1]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[2]     =   initRWall(100, 3, 0,pathForBlue[n1]);
        score[2]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[3]     =   initRWall(145, 3, 5,pathForBlue[n1]);
        score[3]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[4]     =   initRWall(190, 3, -5,pathForBlue[n1]);
        score[4]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[5]     =   initRWall(235, 3, 5,pathForBlue[n1]);
        score[5]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[6]     =   initRWall(280, 3, 0,pathForBlue[n1]);
        score[6]    =   scoreBeforeRandom[n1];
        
        n1          =   rand.nextInt(10);
        wall[7]     =   initRWall(325, 3, 5,pathForBlue[n1]);
        score[7]    =   scoreBeforeRandom[n1];
        
        //red
        n1          =   rand.nextInt(4);
        wall[8]     =   initLWall(10,  3, -5,pathForRed[n1]);
        score[8]    =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[9]     =   initLWall(55,  3, 5,pathForRed[n1]);
        score[9]    =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[10]     =   initLWall(100, 3, 0,pathForRed[n1]);
        score[10]    =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[11]    =   initLWall(145, 3, 5,pathForRed[n1]);
        score[11]   =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[12]    =   initLWall(190, 3, -5,pathForRed[n1]);
        score[12]   =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[13]    =   initLWall(235, 3, 5,pathForRed[n1]);
        score[13]   =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[14]    =   initLWall(280, 3, 0,pathForRed[n1]);
        score[14]   =   scoreBeforeRandom[n1+10];
        
        n1          =   rand.nextInt(10);
        wall[15]    =   initLWall(325, 3, 5,pathForRed[n1]);
        score[15]   =   scoreBeforeRandom[n1+10];
        
        rootNode.attachChild(scene);
        scene.attachChild(walls);
        
        for(int i=0;i<16;i++){
            walls.attachChild(wall[i]);
        }
        
    }
    
    public Node getwalls() {
        return walls;
    }
    
    public int getscore(int num) {
        return score[num];
    }
    
    private Node initLWall(float distance, float gap, float offset,String path) {
        
        float wallWidth = floorWidth/2 - gap/2;
        float leftWall  = wallWidth-offset;
        
        Node wall0 = new Node();
        
        Box b = new Box(1, 1, 1);
        Geometry geom1 = new Geometry("Box", b);                 
        
        geom1.scale(leftWall/2,5,0.01f);
        
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", 
                assetManager.loadTexture(path));
        
        geom1.setMaterial(mat);                         
        geom1.setLocalTranslation(-floorWidth/2+leftWall/2, 0, 0);  
        
        wall0.setLocalTranslation(0, 0, -distance);      
        wall0.attachChild(geom1);
        
        return wall0;
    }
    
    private Node initRWall(float distance, float gap, float offset,String path) {
        float wallWidth = floorWidth/2 - gap/2;
        float rightWall = wallWidth+offset; 
        
        Node wall1 = new Node();
            
        Box c = new Box(1, 1, 1);
        Geometry geom2 = new Geometry("Box", c);      
        
        geom2.scale(rightWall/2,5,0.01f);
        
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setTexture("ColorMap", 
                assetManager.loadTexture(path));
                   
        geom2.setMaterial(mat1);              
        geom2.setLocalTranslation(floorWidth/2-rightWall/2, 0, 0);        
        
        wall1.setLocalTranslation(0, 0, -distance);      
        wall1.attachChild(geom2);
        
        return wall1;
    }
    
    
}
