package ca.warp7.frc2019.constants

import ca.warp7.frc.PID
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration


object DriveConstants {

    // Dimension Constants

    const val kWheelRadius = 5.9 / 2.0 // in
    const val kWheelCircumference = kWheelRadius * 2 * Math.PI // in
    const val kTurningDiameter = 24.75 // in
    const val kTurningCircumference = kTurningDiameter * Math.PI // in

    // Electrical constants

    const val kLeftMaster = ElectricalConstants.kDriveLeftMasterTalonId
    const val kLeftFollowerA = ElectricalConstants.kDriveLeftFollowerAVictorId
    const val kLeftFollowerB = ElectricalConstants.kDriveLeftFollowerBVictorId
    const val kRightMaster = ElectricalConstants.kDriveRightMasterTalonId
    const val kRightFollowerA = ElectricalConstants.kDriveRightFollowerAVictorId
    const val kRightFollowerB = ElectricalConstants.kDriveRightFollowerBVictorId

    // Teleop Control constants

    const val kOpenLoopRamp = 0.15 // s
    const val kDifferentialDeadband = 0.2
    const val kQuickTurnMultiplier = 0.7

    // Unit conversion constants

    const val kFeetToMeters: Double = 0.3048
    const val kInchesToMeters: Double = 0.0254

    const val kTicksPerRevolution = 1024 // ticks/rev
    const val kTicksPerInch = kTicksPerRevolution / kWheelCircumference // ticks/in
    const val kTicksPerFootPer100ms = 12 * kTicksPerInch * 0.1 // ticks/(ft/100ms)
    const val kTicksPerMeterPer100ms = kTicksPerInch / kInchesToMeters * 0.1 // ticks(m/100ms)

    // Kinematic constants

    const val kMaxVelocity = 12.0 // ft/s
    const val kMaxAcceleration = 9.0 //  ft/s^2
    const val kMaxFreeSpeedVelocity = 14.38 // ft/s

    private const val kScrubFactor = 1.45
    const val kEffectiveWheelBaseRadius = kTurningDiameter / 2 * kScrubFactor // in

    // Dynamic constants

    const val kMaxVolts = 12.0 // V
    const val kFrictionVoltage = 0.0 // V
    const val kLinearInertia = 60.0 // kg
    const val kAngularInertia = 10.0 // kg * m^2
    const val kAngularDrag = 0.0 // (N * m) / (rad/s)
    const val kSpeedPerVolt = (kMaxFreeSpeedVelocity / kWheelRadius) / kMaxVolts // (rad/s) / V
    const val kA = 80.0 // // (rad/s^2) / V
    const val kTorquePerVolt = 0.5 * kWheelRadius * kWheelRadius *
            0.0254 * 0.0254 * kLinearInertia * kA  // (N * m) / V

    // Trajectory constants

    const val kMaxDx = 0.0254 // m

    // PID constants

    val kStraightPID: PID
        get() = PID(
                kP = 3.0, kI = 0.00001, kD = 16.0, kF = 0.0,
                errorEpsilon = 0.07, dErrorEpsilon = 0.04, minTimeInEpsilon = 0.3,
                maxOutput = DriveConstants.kMaxVelocity * 2
        )//meters

    val kTurnPID: PID
        get() = PID(
                kP = 0.5, kI = 0.0001, kD = 1.5, kF = 0.0,
                errorEpsilon = 2.0, dErrorEpsilon = 1.0, minTimeInEpsilon = 0.5,
                maxOutput = DriveConstants.kMaxVelocity * 2
        )//degrees

    val kVelocityFeedforwardPID = PID(kP = 0.8, kI = 0.0, kD = 5.0, kF = 1.0)

    // Talon configuration

    val kMasterTalonConfig = TalonSRXConfiguration().apply {

        slot0.apply {
            kP = kVelocityFeedforwardPID.kP
            kI = kVelocityFeedforwardPID.kI
            kD = kVelocityFeedforwardPID.kD
            kF = kVelocityFeedforwardPID.kF
        }

        openloopRamp = kOpenLoopRamp

        voltageCompSaturation = kMaxVolts

        primaryPID.selectedFeedbackSensor = FeedbackDevice.QuadEncoder
    }
}