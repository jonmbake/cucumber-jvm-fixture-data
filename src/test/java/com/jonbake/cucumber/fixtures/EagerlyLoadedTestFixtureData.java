package com.jonbake.cucumber.fixtures;

import com.jonbake.cucumber.annotations.Fixture;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example of fixture data that is eagerly loaded.
 */
@Fixture(eagerlyLoad = true)
public class EagerlyLoadedTestFixtureData {
    private static final AtomicInteger numberOfInstances = new AtomicInteger(0);

    public EagerlyLoadedTestFixtureData () {
        numberOfInstances.incrementAndGet();
    }

    public static int getNumberOfInstances () {
        return numberOfInstances.get();
    }
}
