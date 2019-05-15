package ch.tutteli.atrium.spec.integration

import ch.tutteli.atrium.assertions.AssertionGroup
import ch.tutteli.atrium.assertions.ExplanatoryAssertionGroupType
import ch.tutteli.atrium.core.coreFactory
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.creating.PlantHasNoSubjectException
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.spec.AssertionVerb
import org.jetbrains.spek.api.Spek

abstract class NewSubjectLessAssertionSpec<T : Any>(
    groupPrefix: String,
    vararg assertionCreator: Pair<String, Expect<T>.() -> Unit>
) : Spek({

    group("${groupPrefix}assertion function can be used in an ${AssertionGroup::class.simpleName} with an ${ExplanatoryAssertionGroupType::class.simpleName} and reported without failure") {
        assertionCreator.forEach { (name, createAssertion) ->
            test("fun `$name`") {
                val assertions = coreFactory.newCollectingAssertionContainer<T> { throw PlantHasNoSubjectException() }
                    .addAssertionsCreatedBy(createAssertion)
                    .getAssertions()
                val plant = coreFactory.newReportingPlant(
                    AssertionVerb.ASSERT, { 1.0 },
                    coreFactory.newOnlyFailureReporter(
                        coreFactory.newAssertionFormatterFacade(coreFactory.newAssertionFormatterController()),
                        coreFactory.newNoOpAtriumErrorAdjuster()
                    )
                )
                val explanatoryGroup = AssertImpl.builder.explanatoryGroup
                    .withDefaultType
                    .withAssertions(assertions)
                    .build()
                plant.addAssertion(explanatoryGroup)
            }
        }
    }
})

/**
 * Helper function to map an arbitrary `Expect<T>.(...) -> Unit` function to a parameter-less one.
 */
fun <T : Any> liftToExpect(createAssertion: Expect<T>.() -> Unit): Expect<T>.() -> Unit = createAssertion
