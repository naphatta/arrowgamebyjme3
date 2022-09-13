
package mygame;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData.DataType;
import com.jme3.audio.AudioNode;
import com.jme3.scene.Node;

public class AudioManager {

    private AudioNode audio_nature;
    private final AssetManager assetManager;
    private final Node rootNode;

    public AudioManager(Node rootNode, AssetManager assetManager) {

        this.assetManager = assetManager;
        this.rootNode = rootNode;
        setUpAudio();
    }

    private void setUpAudio() {

        audio_nature = new AudioNode(assetManager, "Sounds/Ocean Waves.ogg", DataType.Stream);
        audio_nature.setLooping(true); 
        audio_nature.setPositional(true);
        audio_nature.setVolume(5);
        rootNode.attachChild(audio_nature);
        audio_nature.play();

    }

    public void update(float tpf) {

    }
}
