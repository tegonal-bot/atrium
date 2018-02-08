package ch.tutteli.atrium.assertions

import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.throwable.thrown.builders.ThrowableThrownBuilder
import ch.tutteli.atrium.creating.throwable.thrown.creators.ThrowableThrownAssertions
import kotlin.reflect.KClass

@Deprecated("use ThrowableThrownAssertions.toBe instead, will be removed with 1.0.0", ReplaceWith("ThrowableThrownAssertions.toBe(throwableThrownBuilder, expectedType, assertionCreator)"))
fun <TExpected : Throwable> _toThrow(throwableThrownBuilder: ThrowableThrownBuilder, expectedType: KClass<TExpected>, assertionCreator: AssertionPlant<TExpected>.() -> Unit) {
    ThrowableThrownAssertions.toBe(throwableThrownBuilder, expectedType, assertionCreator)
}
