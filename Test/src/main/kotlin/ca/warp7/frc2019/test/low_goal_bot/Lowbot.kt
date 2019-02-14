package ca.warp7.frc2019.test.low_goal_bot



import ca.warp7.frc2019.constants.*
import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.can.TalonSRX
import com.ctre.phoenix.motorcontrol.can.VictorSPX
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import kotlin.math.absoluteValue
import kotlin.math.withSign

class Lowbot : TimedRobot() {
    lateinit var controller: XboxController
    lateinit var leftConveyor: VictorSPX
    lateinit var rightConveyor: VictorSPX
    lateinit var intake : VictorSPX
    lateinit var differentialDrive: DifferentialDrive
    lateinit var leftOuttake: VictorSPX
    lateinit var rightOuttake: VictorSPX
    lateinit var liftMaster: TalonSRX

    override fun robotInit() {
        liftMaster = TalonSRX(LiftConstants.kMaster)
        VictorSPX(LiftConstants.kFollower).follow(liftMaster)
        val leftMaster = WPI_TalonSRX(DriveConstants.kLeftMaster).also {
            it.configOpenloopRamp(1.0)
            VictorSPX(DriveConstants.kLeftFollowerA).follow(it)
            VictorSPX(DriveConstants.kLeftFollowerB).follow(it)
        }

        val rightMaster = WPI_TalonSRX(DriveConstants.kRightMaster).also {
            it.configOpenloopRamp(1.0)
            VictorSPX(DriveConstants.kRightFollowerA).follow(it)
            VictorSPX(DriveConstants.kRightFollowerB).follow(it)
        }
        leftOuttake = VictorSPX(OuttakeConstants.kLeft)
        rightOuttake = VictorSPX(OuttakeConstants.kRight)
        differentialDrive = DifferentialDrive(rightMaster,leftMaster )
        leftConveyor = VictorSPX(ConveyorConstants.kLeft)
        rightConveyor = VictorSPX(ConveyorConstants.kRight)
        intake = VictorSPX(IntakeConstants.kVictor)
        controller = XboxController(0)
    }

    override fun disabledInit() {
        leftConveyor.neutralOutput()
        rightConveyor.neutralOutput()
        intake.neutralOutput()
        differentialDrive.stopMotor()
        liftMaster.neutralOutput()

    }

    override fun teleopPeriodic() {
        val y = controller.y
        if (y.absoluteValue > 0.2) liftMaster.set(ControlMode.PercentOutput, (y - 0.2.withSign(y)) / 0.8 * 0.5)
        val left = controller.getTriggerAxis(GenericHID.Hand.kLeft)
        val right = controller.getTriggerAxis(GenericHID.Hand.kRight)
        var speed = 0.0
        if (left > 0.1) {
            speed = left
        } else if (right > 0.1) {
            speed = right * -1
        }
        speed *= 0.7

        rightConveyor.set(ControlMode.PercentOutput, speed / 2 )
        leftConveyor.set(ControlMode.PercentOutput, speed / 2)
        intake.set(ControlMode.PercentOutput, speed)
        differentialDrive.curvatureDrive(
                controller.getY(GenericHID.Hand.kLeft),
                controller.getX(GenericHID.Hand.kRight),
                controller.getBumper(GenericHID.Hand.kLeft)
        )
        val lt = controller.getTriggerAxis(GenericHID.Hand.kLeft)
        val rt = controller.getTriggerAxis(GenericHID.Hand.kRight)
        val outspeed = if (lt > rt) lt else -rt
        leftOuttake.set(ControlMode.PercentOutput, outspeed)
        rightOuttake.set(ControlMode.PercentOutput, outspeed)
    }
}