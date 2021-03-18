package com.jonbake.cucumber.steps;

import com.jonbake.cucumber.fixtures.EagerlyLoadedTestFixtureData;
import com.jonbake.cucumber.fixtures.TestFixtureData;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.assertEquals;

/**
 * Steps for the fixture.feature.
 */
public class FixtureDataSteps {
    public FixtureDataSteps (TestFixtureData testFixtureData) {
    }

    @Given("I have fixture data defined")
    public void iHaveFixtureDataDefined () {
        // NO OP -- TestFixtureData is defined
    }

    @Given("I have eagerly loaded fixture data defined")
    public void iHaveEagerlyLoadedFixtureDataDefined () {
        // NO OP -- EagerlyLoadedTestFixtureData is defined
    }

    @And("the fixture data class is not declared as a constructor argument in the scenario steps")
    public void theFixtureDataClassIsNotDeclaredAsAConstructorArgumentInTheScenarioSteps () {
        // No OP - EagerlyLoadedTestFixtureData is not a constructor arg
    }

    @When("I run this scenario")
    public void iRunThisScenario () {
        // NO OP -- This scenario is running
    }

    @Then("the fixture data is loaded once")
    public void theFixtureDataIsLoadedOnce () {
        assertEquals(1, TestFixtureData.getNumberOfInstances());
    }

    @Then("the eagerly loaded fixture data is loaded once")
    public void theEagerlyLoadedFixtureDataIsLoadedOnce () {
        assertEquals(1, EagerlyLoadedTestFixtureData.getNumberOfInstances());
    }
}
