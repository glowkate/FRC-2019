package ca.warp7.frc2019.subsystems

import ca.warp7.frc.Subsystem
import ca.warp7.frc2019.constants.InfrastructureConstants
import com.kauailabs.navx.frc.AHRS
import edu.wpi.first.wpilibj.Compressor
import edu.wpi.first.wpilibj.PowerDistributionPanel
import edu.wpi.first.wpilibj.SPI
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets

object Infrastructure : Subsystem() {
    private val compressor = Compressor(InfrastructureConstants.kCompressorModule)
    private val ahrs = AHRS(SPI.Port.kMXP)
    private val pdp = PowerDistributionPanel(InfrastructureConstants.kPDPModule)

    var ahrsCalibrated = false
    var yaw = 0.0
    var pitch = 0.0

    var startCompressor = false

    override fun onDisabled() {
        compressor.stop()
    }

    override fun onOutput() {
        if (startCompressor) {
            compressor.start()
            startCompressor = false
        }
    }

    override fun onMeasure(dt: Double) {
        if (!ahrsCalibrated && !ahrs.isCalibrating) ahrsCalibrated = true
        if (compressor.pressureSwitchValue && !compressor.enabled()) startCompressor = true
        if (ahrsCalibrated) {
            yaw = Math.toRadians(ahrs.fusedHeading.toDouble())
            pitch = Math.toRadians(ahrs.pitch.toDouble())
        }
    }

    override fun onPostUpdate() = shuffleboard {
        add("Compressor", compressor)
        add("pdp", pdp).withWidget(BuiltInWidgets.kPowerDistributionPanel)
        add("ahrsCalibrated", ahrsCalibrated)
        add("Yaw", yaw)
        add("Pitch", pitch)
    }
}