
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import org.lwjgl.opengl.Display;

public class GuiManager {

    private final Player player;
    private final BitmapText scoreDisplay;
    private final BitmapText levelDisplay;
    private final BitmapText display;
    private final Node guiNode;

    public GuiManager(Player player, AssetManager assetManager, Node guiNode) {
        this.player = player;
        this.guiNode = guiNode;

        BitmapFont font = assetManager.loadFont("Interface/Fonts/Default.fnt");
        scoreDisplay = new BitmapText(font, false);
        scoreDisplay.setText("Player Score: " + player.getScore());
        scoreDisplay.setColor(ColorRGBA.Black);
        guiNode.attachChild(scoreDisplay);
        scoreDisplay.setLocalTranslation(Display.getWidth() - scoreDisplay.getLineWidth() * 1.5f, Display.getHeight() - scoreDisplay.getLineHeight(), 100);

        display = new BitmapText(font, false);
        display.setText("ArrowGame");
        display.setColor(ColorRGBA.Black);
        guiNode.attachChild(display);
        display.setLocalTranslation(Display.getWidth() / 2 - display.getLineWidth(), Display.getHeight() - scoreDisplay.getLineHeight(), 0);

        levelDisplay = new BitmapText(font, false);
        levelDisplay.setText("Level: " + player.getLevel());
        levelDisplay.setColor(ColorRGBA.Black);
        guiNode.attachChild(levelDisplay);
        levelDisplay.setLocalTranslation(display.getLineWidth(), Display.getHeight() - scoreDisplay.getLineHeight(), 0);
    }

    public void update(float tpf) {
        scoreDisplay.setText("Player Score: " + player.getScore());
        levelDisplay.setText("Level: " + player.getLevel());

        if (player.isWin()) {
            display.setText("Yeah,you saved the world.");
            guiNode.attachChild(display);
            display.setLocalTranslation(Display.getWidth() / 2 - display.getLineWidth() / 2, Display.getHeight() - scoreDisplay.getLineHeight(), 0);
        } else if (player.isDead()) {
            display.setText("You're Dead.");
            guiNode.attachChild(display);
            display.setLocalTranslation(Display.getWidth() / 2 - display.getLineWidth() / 2, Display.getHeight() - scoreDisplay.getLineHeight(), 0);
        } else {
            display.setText("ArrowGame");
            guiNode.attachChild(display);
            display.setLocalTranslation(Display.getWidth() / 2 - display.getLineWidth() / 2, Display.getHeight() - scoreDisplay.getLineHeight(), 0);
        }
    }
}
