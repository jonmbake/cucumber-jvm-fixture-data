package com.jonbake.cucumber.fixtures;

import com.jonbake.cucumber.annotations.Fixture;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example of fixture data that is lazilly loaded.
 */
@Fixture
public class TestFixtureData {
    private static final AtomicInteger numberOfInstances = new AtomicInteger(0);

    public TestFixtureData () {
        numberOfInstances.incrementAndGet();
    }

    public static int getNumberOfInstances () {
        return numberOfInstances.get();
    }
}
