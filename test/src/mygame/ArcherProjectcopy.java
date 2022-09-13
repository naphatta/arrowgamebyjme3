package mygame;


import com.jme3.app.SimpleApplication;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.terrain.heightmap.ImageBasedHeightMap;
import com.jme3.texture.Texture;
import com.jme3.texture.Texture.WrapMode;
import java.util.ArrayList;
import java.util.List;
import jme3tools.converters.ImageToAwt;
import com.jme3.audio.AudioNode;
import static com.jme3.bullet.PhysicsSpace.getPhysicsSpace;
import com.jme3.scene.CameraNode;
import com.jme3.scene.control.CameraControl.ControlDirection;


/**
 * This demo shows a terrain with collision detection,
 * that you can walk around in with a first-person perspective.
 * This code combines HelloCollision and HelloTerrain.
 */
public class ArcherProjectcopy extends SimpleApplication
        implements ActionListener {

  private BulletAppState bulletAppState;
  private RigidBodyControl landscape;
  private CharacterControl player;
  private Vector3f walkDirection = new Vector3f(0,0,0);
  private Vector3f viewDirection = new Vector3f(0,0,0);
  private boolean left = false, right = false, up = false, down = false;
  private TerrainQuad terrain;
  private Material mat_terrain;
  
  private AudioNode audio_nature;

  public static void main(String[] args) {
    ArcherProjectcopy app = new ArcherProjectcopy();
    app.start();
  }

  @Override
  public void simpleInitApp() {
    /** Set up Physics */
    bulletAppState = new BulletAppState();
    stateManager.attach(bulletAppState);
    //Uncomment for debugging.
    //bulletAppState.setDebugEnabled(true);
    
    viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
    setUpKeys();
    setUpAudio();

    /** 1. Create terrain material and load four textures into it. */
    mat_terrain = new Material(assetManager,
            "Common/MatDefs/Terrain/Terrain.j3md");

    /** 1.1) Add ALPHA map (for red-blue-green coded splat textures) */
    mat_terrain.setTexture("Alpha", assetManager.loadTexture(
            "Textures/grass.jpg"));

    /** 1.2) Add GRASS texture into the red layer (Tex1). */
    Texture grass = assetManager.loadTexture(
            "Textures/grass.jpg");
    grass.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex1", grass);
    mat_terrain.setFloat("Tex1Scale", 64f);

    /** 1.3) Add DIRT texture into the green layer (Tex2) */
    Texture dirt = assetManager.loadTexture(
            "Textures/grass.jpg");
    dirt.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex2", dirt);
    mat_terrain.setFloat("Tex2Scale", 32f);

    /** 1.4) Add ROAD texture into the blue layer (Tex3) */
    Texture rock = assetManager.loadTexture(
            "Textures/grass.jpg");
    rock.setWrap(WrapMode.Repeat);
    mat_terrain.setTexture("Tex3", rock);
    mat_terrain.setFloat("Tex3Scale", 128f);

    /** 2. Create the height map */
    HillHeightMap heightmap = null;
    HillHeightMap.NORMALIZE_RANGE = 100; // optional
    try {
            heightmap = new HillHeightMap(100, 100, 50, 51, (byte) 3); // byte 3 is a random seed
    } catch (Exception ex) {
        ex.printStackTrace();
    }
        Box box0 = new Box(10f,1f,2500f); //long high deep
        Spatial runway = new Geometry("Box", box0 );
        Material mat_runway = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_runway.setTexture("ColorMap",
            assetManager.loadTexture("Textures/estrada.jpg"));
        runway.setMaterial(mat_runway);
        runway.setLocalTranslation(10, 0, 0);
        rootNode.attachChild(runway);
    
        Box box = new Box(1f,3f,2500f); //long high deep
        Spatial wall = new Geometry("Box", box );
        Material mat_brick = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick.setTexture("ColorMap",
            assetManager.loadTexture("Textures/rooftiles.jpg"));
        wall.setMaterial(mat_brick);
        wall.setLocalTranslation(21, 0, 0);
        rootNode.attachChild(wall);
        
    CollisionShape sceneShape =
            CollisionShapeFactory.createMeshShape(wall);
    landscape = new RigidBodyControl(sceneShape, 0);
    wall.addControl(landscape);
    
    Box box2 = new Box(1f,3f,2500f); //long high deep
        Spatial wall2 = new Geometry("Box", box2 );
        Material mat_brick2 = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_brick2.setTexture("ColorMap",
            assetManager.loadTexture("Textures/rooftiles.jpg"));
        wall2.setMaterial(mat_brick2);
        wall2.setLocalTranslation(-1, 0, 0);
        rootNode.attachChild(wall2);
        
        CollisionShape sceneShape2 =
            CollisionShapeFactory.createMeshShape(wall2);
    landscape = new RigidBodyControl(sceneShape2, 0);
    wall2.addControl(landscape);
    
    Box box3 = new Box(5,100,0); //long high deep
        Spatial block = new Geometry("Box", box3 );
        Material mat_block = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_block.setColor("Color", ColorRGBA.Red);
        block.setMaterial(mat_block);
        block.setLocalTranslation(5f, 0, 0);
        rootNode.attachChild(block);
        
    Box box4 = new Box(5,100,0); //long high deep
        Spatial block2 = new Geometry("Box", box4 );
        Material mat_block2 = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_block2.setColor("Color", ColorRGBA.Green);
        block2.setMaterial(mat_block2);
        block2.setLocalTranslation(15f, 0, 0);
        rootNode.attachChild(block2);
        
    terrain = new TerrainQuad("my terrain", 65, 513, heightmap.getHeightMap());

    /** 4. We give the terrain its material, position & scale it, and attach it. */
    terrain.setMaterial(mat_terrain);
    terrain.setLocalTranslation(0, 0, 0);
    terrain.setLocalScale(2f, 1f, 2f);
    rootNode.attachChild(terrain);

    /** 5. The LOD (level of detail) depends on were the camera is: */
    List<Camera> cameras = new ArrayList<Camera>();
    cameras.add(getCamera());
    TerrainLodControl control = new TerrainLodControl(terrain, cameras);
    terrain.addControl(control);

    /** 6. Add physics:
     * We set up collision detection for the scene by creating a static
     * RigidBodyControl with mass zero.
     */
    terrain.addControl(new RigidBodyControl(0));

    /**
     * We set up collision detection for the player by creating
     * a capsule collision shape and a CharacterControl.
     * The CharacterControl offers extra settings for
     * size, stepheight, jumping, falling, and gravity.
     * We also put the player in its starting position.
     */

            
    // Add a physics character to the world
    player = new CharacterControl(new CapsuleCollisionShape(0.5f, 1.8f), .1f);
    Node characterNode = new Node("character node");
    Spatial model = assetManager.loadModel("Models/Arrow.j3o");
    Material mat_model = new Material(
            assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat_model.setColor("Color", ColorRGBA.Black);
        model.setMaterial(mat_model);
    model.scale(0.25f);
    characterNode.addControl(player);
    getPhysicsSpace().add(player);
    rootNode.attachChild(characterNode);
    characterNode.attachChild(model);
    player.setPhysicsLocation(new Vector3f(15, 20, -30));
    
    // We attach the scene and the player to the rootnode and the physics space,
    // to make them appear in the game world.
    bulletAppState.getPhysicsSpace().add(terrain);
    bulletAppState.getPhysicsSpace().add(player);
    
    CameraNode camNode = new CameraNode("CamNode", cam);
    camNode.setControlDir(ControlDirection.SpatialToCamera);
    camNode.setLocalTranslation(new Vector3f(0, 20, -30));
    camNode.lookAt(model.getLocalTranslation(), Vector3f.UNIT_Y);
    characterNode.attachChild(camNode);
    
    flyCam.setEnabled(false);
    
    player.setGravity(0);
  }
  private void setUpLight() {
    // We add light so we see the scene
    AmbientLight al = new AmbientLight();
    al.setColor(ColorRGBA.White.mult(1.3f));
    rootNode.addLight(al);

    DirectionalLight dl = new DirectionalLight();
    dl.setColor(ColorRGBA.White);
    dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
    rootNode.addLight(dl);
  }
  
  private void setUpAudio() {

    /* nature sound - keeps playing in a loop. */
 /*   audio_nature = new AudioNode(assetManager, "Sounds/InterstellarOgg.ogg");
    audio_nature.setLooping(true);  // activate continuous playing
    audio_nature.setPositional(true);
    audio_nature.setVolume(3);
    rootNode.attachChild(audio_nature);
    audio_nature.play(); // play continuously!
 */
  }
  
  /** We over-write some navigational key mappings here, so we can
   * add physics-controlled walking and jumping: */
  private void setUpKeys() {
    inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
    inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
    inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
    inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
    inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
    inputManager.addListener(this, "Left");
    inputManager.addListener(this, "Right");
    inputManager.addListener(this, "Up");
    inputManager.addListener(this, "Down");
    inputManager.addListener(this, "Jump");
  }

  /** These are our custom actions triggered by key presses.
   * We do not walk yet, we just keep track of the direction the user pressed. */
  public void onAction(String binding, boolean value, float tpf) {
    if (binding.equals("Left")) {
      if (value) { left = true; } else { left = false; }
    } else if (binding.equals("Right")) {
      if (value) { right = true; } else { right = false; }
    } else if (binding.equals("Up")) {
      if (value) { up = true; } else { up = false; }
    } else if (binding.equals("Down")) {
      if (value) { down = true; } else { down = false; }
    } else if (binding.equals("Jump")) {
      //player.jump(new Vector3f(0,20f,0));
      player.jump();
    }
  }


  @Override
  public void simpleUpdate(float tpf) {
    Vector3f camDir = cam.getDirection().mult(0.2f);
    Vector3f camLeft = cam.getLeft().mult(0.2f);
    camDir.y = 0;
    camLeft.y = 0;
    viewDirection.set(camDir);
    walkDirection.set(0, 0, 0);    
    if (left)  { walkDirection.addLocal(camLeft); }
    if (right) { walkDirection.addLocal(camLeft.negate()); }
    if (up)    { walkDirection.addLocal(camDir); }
    if (down)  { walkDirection.addLocal(camDir.negate()); }
    player.setWalkDirection(walkDirection);
    cam.setLocation(player.getPhysicsLocation());
    listener.setLocation(cam.getLocation());
    listener.setRotation(cam.getRotation());
  }
}
