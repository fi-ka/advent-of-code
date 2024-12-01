package util

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Int.toward(to: Byte): IntProgression {
    return IntProgression.fromClosedRange(this, to.toInt(), if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Long.toward(to: Byte): LongProgression {
    return LongProgression.fromClosedRange(this, to.toLong(), if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Byte.toward(to: Byte): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to.toInt(), if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Short.toward(to: Byte): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to.toInt(), if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Char.toward(to: Char): CharProgression {
    return CharProgression.fromClosedRange(this, to, if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Int.toward(to: Int): IntProgression {
    return IntProgression.fromClosedRange(this, to, if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Long.toward(to: Int): LongProgression {
    return LongProgression.fromClosedRange(this, to.toLong(), if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Byte.toward(to: Int): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to, if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Short.toward(to: Int): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to, if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Int.toward(to: Long): LongProgression {
    return LongProgression.fromClosedRange(this.toLong(), to, if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Long.toward(to: Long): LongProgression {
    return LongProgression.fromClosedRange(this, to, if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Byte.toward(to: Long): LongProgression {
    return LongProgression.fromClosedRange(this.toLong(), to, if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Short.toward(to: Long): LongProgression {
    return LongProgression.fromClosedRange(this.toLong(), to, if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Int.toward(to: Short): IntProgression {
    return IntProgression.fromClosedRange(this, to.toInt(), if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Long.toward(to: Short): LongProgression {
    return LongProgression.fromClosedRange(this, to.toLong(), if (this >= to) -1L else 1L)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Byte.toward(to: Short): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to.toInt(), if (this >= to) -1 else 1)
}

/**
 * Returns a progression from this value toward the specified [to] value with the step -1.
 */
infix fun Short.toward(to: Short): IntProgression {
    return IntProgression.fromClosedRange(this.toInt(), to.toInt(), if (this >= to) -1 else 1)
}
