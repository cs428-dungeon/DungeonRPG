package edu.byu.rpg.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;

/**
 * Abstracts input and consolidates its logic to one place.  For now,
 * we only support Xbox 360 controllers, or keyboard.
 *
 * @see <a href="https://github.com/libgdx/libgdx/wiki/Controllers">LibGDX Wiki - Controllers</a>
 * @see <a href="http://www.gamefromscratch.com/post/2014/09/22/LibGDX-Tutorial-Part-14-Gamepad-support.aspx">Game from scratch tutorial</a>
 */
public class InputManager {

    /** Used to pad our axis input so the controller joystick response isn't janky. */
    private static final float axisPad = 2.0f;

    /** Our private controller instance */
    private static Controller controller;

    /**
     * Checks if an Xbox 360 Controller is connected.  If it is, and
     * {@link InputManager#controller} is null, it assigns the controller
     * for use in this class.
     * @return True if connected, false if not.
     */
    private static boolean controllerConnected() {
        if (Controllers.getControllers().size == 0) {
            return false;
        }

        if (controller == null) {
            for (Controller ctrl : Controllers.getControllers()) {
                if (Xbox360Pad.isValidController(ctrl)) {
                    controller = ctrl;
                    break;
                }
            }
        }

        return controller != null;
    }

    /**
     * Returns the x-value of the left input axis (WASD or left joystick)
     * @return A value from -1.0 to 1.0
     */
    public static float getLeftXAxis() {
        // check for controller input
        if (controllerConnected()) {
            float axis = controller.getAxis(Xbox360Pad.AXIS_LEFT_X);
            return (Math.abs(axis) > axisPad) ? axis : 0;
        }

        // if none, check for keyboard input
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            return -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            return 1;
        }

        return 0;
    }

    /**
     * Returns the y-value of the left input axis (WASD or left joystick)
     * @return A value from -1.0 to 1.0
     */
    public static float getLeftYAxis() {
        if (controllerConnected()) {
            float axis = controller.getAxis(Xbox360Pad.AXIS_LEFT_Y);
            return (Math.abs(axis) > axisPad) ? axis : 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            return -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            return 1;
        }

        return 0;
    }

    /**
     * Returns the x-value of the right input axis (Arrow keys or right joystick)
     * @return A value from -1.0 to 1.0
     */
    public static float getRightXAxis() {
        if (controllerConnected()) {
            float axis = controller.getAxis(Xbox360Pad.AXIS_RIGHT_X);
            return (Math.abs(axis) > axisPad) ? axis : 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            return -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            return 1;
        }

        return 0;
    }

    /**
     * Returns the y-value of the right input axis (Arrow keys or right joystick)
     * @return A value from -1.0 to 1.0
     */
    public static float getRightYAxis() {
        if (controllerConnected()) {
            float axis = controller.getAxis(Xbox360Pad.AXIS_RIGHT_Y);
            return (Math.abs(axis) > axisPad) ? axis : 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            return -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            return 1;
        }

        return 0;
    }



}
