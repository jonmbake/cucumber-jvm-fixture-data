package com.jonbake.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    plugin = {"pretty", "html:target/cucumber-report.html"},
    glue = "com.jonbake.cucumber.steps",
    features = "src/test/resources/features",
    objectFactory = FixtureObjectFactory.class
)
public class RunCucumberTest {
}