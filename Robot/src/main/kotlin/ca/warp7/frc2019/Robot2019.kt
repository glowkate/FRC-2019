package ca.warp7.frc2019

import ca.warp7.frckt.*
import edu.wpi.first.wpilibj.TimedRobot

/**
 * The robot class with a default period (0.02 seconds per loop)
 */
class Robot2019 : TimedRobot(kDefaultPeriod) {

    companion object {

        /**
         * The main function that is executed from the `Main-Class` of the jar file.
         * It calls on TimedRobot to initialize system hardware and start the main
         * loop that calls the other functions
         */
        @JvmStatic
        fun main(args: Array<String>) = TimedRobot.startRobot { Robot2019() }
    }

    /**
     * Initializes the robot by registering input devices, then setting all the
     * subsystem objects to their idle state. Finally initialize the runtime
     */
    override fun robotInit() {
        println("Hello me is robit!")
        runRobot()
    }

    /**
     * Disables the robot by disabling each subsystem and not calling output methods.
     * Stops the autonomous routine if there is one
     */
    override fun disabledInit() = disableRobot()

    /**
     * Starts the autonomous mode by providing a mode to execute.
     *
     * With the sandstorm in the 2019 season, a helper method starts the
     * autonomous mode, then interrupts and goes into controller
     */
    override fun autonomousInit() = sandstorm(Autonomous.mode, Autonomous.exitCondition, ControllerMain, 15.0)

    /**
     * Starts the teleop mode by providing a control loop.
     * Stops the autonomous routine if there is one
     */
    override fun teleopInit() = startRobotControls(ControllerMain)

    /**
     * Starts the test mode by providing a potentially different control loop
     */
    override fun testInit() = startRobotControls(ControllerTest)

    /**
     * Runs a periodic loop that collects inputs, update the autonomous
     * routine and controller loop, process subsystem states, send output
     * signals, and send telemetry data
     */
    override fun robotPeriodic() = mainLoop()

    /*
    =====================================================
    The following periodic functions are not used because
    they are all handled by the `robotPeriodic` function
    =====================================================
     */

    override fun disabledPeriodic() = Unit
    override fun autonomousPeriodic() = Unit
    override fun teleopPeriodic() = Unit
    override fun testPeriodic() = Unit
}