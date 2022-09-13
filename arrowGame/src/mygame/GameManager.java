
package mygame;

import com.jme3.app.SimpleApplication;
import tonegod.gui.core.Screen;

public class GameManager {

    private final SceneManager sceneManager;
    private final PlayerManager playerManager;
    private final GuiManager guiManager;
    private final AudioManager audioManager;

    private Screen screen;

    public GameManager(SimpleApplication app) {

        sceneManager = new SceneManager(app.getRootNode(), app.getAssetManager());
        playerManager = new PlayerManager(app.getRootNode(), app.getInputManager(), app.getAssetManager(), app.getCamera(), sceneManager);
        guiManager = new GuiManager(playerManager.getPlayer(), app.getAssetManager(), app.getGuiNode());
        audioManager = new AudioManager(app.getRootNode(), app.getAssetManager());
        sceneManager.setPlayer(playerManager.getPlayer());
    }

    public void Gamestop() {
        sceneManager.stop();
    }

    public boolean GameIsstop() {
        return sceneManager.isStop();
    }

    public void update(float tpf) {
        sceneManager.update(tpf);
        playerManager.update(tpf);
        guiManager.update(tpf);
        audioManager.update(tpf);
    }

}
