package ca.warp7.frc.geometry

interface StateView<T : State<T>> {

    /**
     * Get the state that is being viewed
     */
    val state: T

    /**
     * Create an interpolator for the this state and another state
     */
    operator fun rangeTo(state: T): Interpolator<T>
}