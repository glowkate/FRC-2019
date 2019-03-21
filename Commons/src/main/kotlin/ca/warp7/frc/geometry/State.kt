package ca.warp7.frc.geometry

/**
 * Defines a parametric transformation state
 */
interface State<T : State<T>> : StateView<T> {

    /**
     * Get the inverse transformation of this state
     */
    operator fun unaryMinus(): T

    /**
     * Get the inverse transformation of this state
     */
    val inverse: T

    /**
     * Create a copy of this state
     */
    operator fun unaryPlus(): T

    /**
     * Create a copy of this state
     */
    val copy: T

    /**
     * Checks if the state is its identity
     */
    val isIdentity: Boolean


    /**
     * Check if another state is close to the current state
     */
    fun epsilonEquals(state: T, epsilon: Double): Boolean
}