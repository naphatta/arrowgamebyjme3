package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class MainGUI extends SimpleApplication {
    
    private int winCount;
    private Screen screen;
    
    public static void main(String[] args) {
        MainGUI app = new MainGUI();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
        
        screen = new Screen(this);
        guiNode.addControl(screen);

        // Add window
        Window win = new Window(screen, "Windowmaker", new Vector2f(15, 15));
    
        // create button and add to window
        ButtonAdapter makeWindow = new ButtonAdapter( screen, "Btn1", new Vector2f(15, 55) ) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                createNewWindow("New Window " + winCount);
        }
    };
    makeWindow.setText("windowmaker");
    win.setWindowTitle("Arrow Game Window");
    // Add it to our initial window
    win.addChild(makeWindow);

    // Add window to the screen
    screen.addElement(win);
    inputManager.setCursorVisible(true);
    flyCam.setDragToRotate(true);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    public final void createNewWindow(String someWindowTitle) {
    Window nWin = new Window(
        screen,
        "Window" + winCount,
        new Vector2f( (screen.getWidth()/2)-175, (screen.getHeight()/2)-100 )
    );
    nWin.setWindowTitle(someWindowTitle);
    screen.addElement(nWin);
    winCount++;
    }

}
