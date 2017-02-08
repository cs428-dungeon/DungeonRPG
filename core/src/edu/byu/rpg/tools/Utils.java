package edu.byu.rpg.tools;

/**
 * Useful functions to be used across the project, that don't really fit into any one class.
 */
public class Utils {

    /**
     * Approach a value without going beyond that value.  Useful for things like approaching a
     * maximum velocity, or approaching 0 from either direction (positive or negative).
     *
     * For example:
     * <pre>
     *     // approach a maximum velocity without going over it.
     *     velocity.x = Utils.approach(velocity.x, velocity.xMax, acceleration.x);
     *     // approach 0 without going above/below it.
     *     velocity.x = Utils.approach(velocity.x, 0, friction.x);
     * </pre>
     *
     * @param start The starting value.
     * @param end The target end value.
     * @param interval The interval at which to approach the target end value.
     * @return The result of approaching the target value.
     */
    public static float approach(float start, float end, float interval) {
        float i = Math.abs(interval);
        if (start < end) {
            return Math.min(end, start + i);
        } else {
            return Math.max(end, start - i);
        }
    }

}
