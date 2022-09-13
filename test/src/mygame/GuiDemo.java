/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package mygame;

import com.jme3.app.SimpleApplication;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.Label;
import com.simsilica.lemur.style.BaseStyles;

public class GuiDemo extends SimpleApplication {

    public static void main( String[] args ) {
        GuiDemo main = new GuiDemo();
        main.start();
    }           
    
    @Override
    public void simpleInitApp() {
            
        // Create a simple container for our elements
Container myWindow = new Container();
guiNode.attachChild(myWindow);

// Put it somewhere that we will see it.
// Note: Lemur GUI elements grow down from the upper left corner.
myWindow.setLocalTranslation(300, 300, 0);

// Add some elements
myWindow.addChild(new Label("Hello, World."));
Button clickMe = myWindow.addChild(new Button("Click Me"));
clickMe.addClickCommands(new Command<Button>() {
        @Override
        public void execute( Button source ) {
            System.out.println("The world is yours.");
        }
    });           
    }    
}