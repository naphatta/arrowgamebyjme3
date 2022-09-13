
package mygame;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class InteractionManager implements ActionListener {

    private final InputManager inputManager;
    private boolean left, right, jump, up, down;

    public InteractionManager(InputManager inputManager) {
        this.inputManager = inputManager;
        setUpKeys();
    }

    private void setUpKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(this, "Right");
        inputManager.addListener(this, "Left");
        inputManager.addListener(this, "Up");
        inputManager.addListener(this, "Down");
        inputManager.addListener(this, "Jump");
    }

    public boolean getJump() {
        return jump;
    }

    public boolean getLeft() {
        return left;
    }

    public boolean getRight() {
        return right;
    }

    public boolean getUp() {
        return up;
    }

    public boolean getDown() {
        return down;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {

        if (name.equals("Jump")) {
            jump = isPressed;
        }

        if (name.equals("Left")) {
            left = isPressed;
        }

        if (name.equals("Right")) {
            right = isPressed;
        }

        if (name.equals("Up")) {
            up = isPressed;
        }

        if (name.equals("Down")) {
            down = isPressed;
        }

    }

}
