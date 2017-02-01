package edu.byu.rpg.input;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;

/**
 * Input code mappings for Xbox 360 game pad.  (For now, this is the only
 * controller we support.)
 *
 * @see <a href="http://www.gamefromscratch.com/post/2014/09/22/LibGDX-Tutorial-Part-14-Gamepad-support.aspx">Game from scratch tutorial</a>
 */
public class Xbox360Pad {

    /**
     * Checks if a controller is an xbox 360 controller.
     * @param controller The controller you want to test.
     * @return True if the controller is a 360 controller, false if no.
     */
    public static boolean isValidController(Controller controller) {
        return controller.getName().toLowerCase().contains("xbox")
                && controller.getName().contains("360");
    }
    public static final int BUTTON_X = 2;
    public static final int BUTTON_Y = 3;
    public static final int BUTTON_A = 0;
    public static final int BUTTON_B = 1;
    public static final int BUTTON_BACK = 6;
    public static final int BUTTON_START = 7;
    public static final PovDirection BUTTON_DPAD_UP = PovDirection.north;
    public static final PovDirection BUTTON_DPAD_DOWN = PovDirection.south;
    public static final PovDirection BUTTON_DPAD_RIGHT = PovDirection.east;
    public static final PovDirection BUTTON_DPAD_LEFT = PovDirection.west;
    public static final int BUTTON_LB = 4;
    public static final int BUTTON_L3 = 8;
    public static final int BUTTON_RB = 5;
    public static final int BUTTON_R3 = 9;
    public static final int AXIS_LEFT_X = 1; //-1 is left | +1 is right
    public static final int AXIS_LEFT_Y = 0; //-1 is up | +1 is down
    public static final int AXIS_LEFT_TRIGGER = 4; //value 0 to 1f
    public static final int AXIS_RIGHT_X = 3; //-1 is left | +1 is right
    public static final int AXIS_RIGHT_Y = 2; //-1 is up | +1 is down
    public static final int AXIS_RIGHT_TRIGGER = 4; //value 0 to -1f
}
