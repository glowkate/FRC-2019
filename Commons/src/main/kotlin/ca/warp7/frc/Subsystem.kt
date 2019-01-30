package ca.warp7.frc

import ca.warp7.actionkt.Action
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer

/**
 * Subsystem defines a robot subsystem. A good example of a subsystem is the drive train or
 * a claw. ISubsystem defines many important callbacks for making a subsystem.
 *
 * All motors should be a part of a subsystem. For instance, all the wheel motors should be a
 * part of some kind of "drive train" subsystem. Every motor should also be only in one subsystem,
 * they should also not be used by multiple subsystems.
 *
 * All sensor devices such as encoders and cameras should be in the subsystem where their
 * measured values have the most direct impact on the motors and/or other output devices of
 * the said subsystem, except in the case of classes extending {@link IComponent}, in which
 * components can be used by multiple subsystems.
 *
 * Each subsystem should have only one instance. They should use objects in Kotlin.
 *
 * This interface defines all the callbacks a subsystem should have, including handlers for
 * input, output, init, disabled, resetting, debugging, and state updating. It is managed by
 * {@link Components} and called periodically by the {@link LoopsManager} class during
 * different phases of robot runtime
 *
 * All the methods except onConstruct are empty default methods. Choose the appropriate one to
 * implement. They will be called properly by {@link Components} regardless which
 * methods are implemented
 *
 * A good implementation strategy is define specific object classes that holds the input state
 * and current state of the subsystem respectively. This interface defines some annotations
 * markers in order to make it clear about usage of this strategy
 *
 * Finally, it is very important that implementations of these methods are <b>synchronized</b>
 * because they are most often called from different threads. It's also important
 * that the periodic functions are not blocking operations as to prevent leaking.
 */

abstract class Subsystem {

    internal var currentState: Action? = null
    private var initialized = false

    /**
     * Called when the robot is disabled
     *
     *
     * This method should reset everything having to do with output so as to put
     * the subsystem in a disabled state
     */
    open fun onDisabled() {}

    /**
     * Called periodically for the subsystem to send outputs to its output device.
     *
     * This method is guaranteed to not be called when the robot is disabled.
     * Any output limits should be applied here for safety reasons.
     */
    open fun onOutput() {}

    /**
     * Sets the current state of the subsystem
     */
    fun <T : Action> set(wantedState: T, block: T.() -> Unit = {}) {
        if (!initialized) {
            initialized = true
            CommonRobot.subsystems.add(this)
        }
        // Check if there is a new wanted state that is not the same as the current state
        if (wantedState != currentState) {
            // Change to the new state
            currentState = wantedState
            // Start the new state
            currentState?.start()
        }
        block(wantedState)
    }


    /**
     *
     * Called periodically for the subsystem to get measurements from its input devices.
     * This method is called from the Input Looper. All sensor reading should be done
     * in this method.
     *
     *
     * When using input/current states, the measured values here should change
     * the subsystem's current state
     *
     *
     * Note that this method may still be called while the robot is disabled, so
     * extra care should be made that it performs no outputting
     */
    open fun onMeasure(dt: Double) {}

    /**
     *
     * Called at the start for the subsystem to zero its sensors.
     * In addition, this method may by called by autonomous actions
     */
    open fun onZeroSensors() {}

    /**
     * Send values to shuffleboard
     */
    open fun onUpdateShuffleboard(container: ShuffleboardContainer) {}
}
