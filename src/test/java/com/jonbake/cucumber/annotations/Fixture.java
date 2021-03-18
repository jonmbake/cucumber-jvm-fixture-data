package com.jonbake.cucumber.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Indicates that a cucumber dependency-injected instance should be fixture-scoped, i.e. a single instance throughout
 * the lifetime of all test suite scenarios.
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Fixture {
    /**
     * Indicates that a fixture should be eagerly loaded at the start before the first scenario runs.
     *
     * @return boolean indicating if fixture shold be eagerly loaded
     */
    boolean eagerlyLoad () default false;
}
