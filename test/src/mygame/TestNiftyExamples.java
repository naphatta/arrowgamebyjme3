/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.texture.image.ColorSpace;
import de.lessvoid.nifty.Nifty;
import com.jme3.texture.FrameBuffer;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.texture.image.ColorSpace;
import de.lessvoid.nifty.Nifty;

public class TestNiftyExamples extends SimpleApplication {

    public static void main(String[] args){
        TestNiftyExamples app = new TestNiftyExamples();
        app.setPauseOnLostFocus(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        ColorSpace colorSpace = renderer.isMainFrameBufferSrgb()
                ? ColorSpace.sRGB : ColorSpace.Linear;
        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay(assetManager,
                                                          inputManager,
                                                          audioRenderer,
                                                          guiViewPort,
                                                          colorSpace);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.fromXml("all/intro.xml", "start");

        // attach the nifty display to the gui view port as a processor
        guiViewPort.addProcessor(niftyDisplay);

        // disable the fly cam
        flyCam.setEnabled(false);
    }

}
