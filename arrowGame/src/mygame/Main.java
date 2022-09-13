package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.renderer.RenderManager;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.text.LabelElement;
import tonegod.gui.controls.windows.Window;
import tonegod.gui.core.Screen;


public class Main extends SimpleApplication {
    
    public Screen screen;
    private Window win;
    private InteractionManager interactionManager;
    
    GameManager g;
    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
        app.setDisplayStatView(false);
        app.setDisplayFps(false);
    }
    
    @Override
    public void simpleInitApp() {
        g = new GameManager(this);
        g.Gamestop();
        interactionManager = new InteractionManager(inputManager);
        screen = new Screen(this);
        guiNode.addControl(screen);
        
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 5f, 4f));
        
        win = new Window(screen, "Windowmaker", new Vector2f(200, 150));
        ButtonAdapter makeWindow = new ButtonAdapter( screen, new Vector2f(160, 80) ){            
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled){
                screen.removeElement(win);
                starta();
            }
        };
        makeWindow.setText("start.");
        
        ButtonAdapter Window1 = new ButtonAdapter( screen, "Btn1", new Vector2f(25, 80) );
        Window1.setButtonIcon(100,100,"Interface/logo.png");
        
        ButtonAdapter Window2 = new ButtonAdapter( screen, "Btn1", new Vector2f(160, 120) );
        Window2.setText("Restart.");
        
        LabelElement Label1 = new LabelElement(screen, new Vector2f(160, 30));
        Label1.scale(1,1, 1);
        Label1.setText("go to level 10!!");
        
        LabelElement Label2 = new LabelElement(screen, new Vector2f(160, 50));
        Label2.scale(0.85f,1, 1);
        Label2.setText("to save the world");

        win.setWindowTitle("Arrow Java Game.");

        win.addChild(makeWindow);
        win.addChild(Window1);
        win.addChild(Window2);
        win.addChild(Label1);
        win.addChild(Label2);

        screen.addElement(win);
        inputManager.setCursorVisible(true);
        flyCam.setDragToRotate(true);
        
    }

    @Override
    public void simpleUpdate(float tpf) {
        g.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {

    }
    public final void starta() {
        inputManager.setCursorVisible(false);
        flyCam.setDragToRotate(false);
        flyCam.setEnabled(false);
        g.Gamestop();
        FilterPostProcessor processor 
                = (FilterPostProcessor) assetManager.loadAsset("Shaders/Filter.j3f");
        viewPort.addProcessor(processor); 
    }
}
