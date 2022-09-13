package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

public class MyBaseGame extends SimpleApplication {

    public static void main(String[] args){
        MyBaseGame app = new MyBaseGame();
        app.start();
    }

    @Override
    public void simpleInitApp() {
       /* Initialize the game scene here */
    }

    @Override
    public void simpleUpdate(float tpf) {
       /* Interact with game events in the main loop */
    }

    @Override
    public void simpleRender(RenderManager rm) {
       /* (optional) Make advanced modifications to frameBuffer and scene graph. */
    }
}