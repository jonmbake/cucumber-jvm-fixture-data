package com.jonbake.cucumber;

import com.jonbake.cucumber.annotations.Fixture;
import io.cucumber.core.backend.ObjectFactory;
import org.picocontainer.*;
import org.picocontainer.behaviors.AbstractBehaviorFactory;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.behaviors.Storing;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.function.Consumer;

/**
 * An {@link ObjectFactory} that caches DI instances annotated with {@link Fixture} for the lifetime of the test suite.
 * DI instances not annotated have the same default behavior as defined in {@link io.cucumber.picocontainer.PicoFactory}
 * where instances are cached throughout the scenario. This implementation is the same as PicoFactory with the exception
 * of the caching behavior being changed.
 */
public class FixtureObjectFactory implements ObjectFactory {
    private static final FixtureBehavior FIXTURE_BEHAVIOR = new FixtureBehavior();

    private final Set<Class<?>> classes = new HashSet<>();
    private MutablePicoContainer pico;

    private static boolean isInstantiable (Class<?> clazz) {
        boolean isNonStaticInnerClass = !Modifier.isStatic(clazz.getModifiers()) && clazz.getEnclosingClass() != null;
        return Modifier.isPublic(clazz.getModifiers()) && !Modifier.isAbstract(clazz.getModifiers())
                && !isNonStaticInnerClass;
    }

    public void start () {
        pico = new PicoBuilder()
                .withBehaviors(FIXTURE_BEHAVIOR)
                .withLifecycle()
                .build();
        consumeEagerlyLoadedFixtureClass(fixtureClass -> addClass(fixtureClass));
        for (Class<?> clazz : classes) {
            pico.addComponent(clazz);
        }
        // Eagerly load fixture data by calling #getInstance here in #start
        consumeEagerlyLoadedFixtureClass(fixtureClass -> getInstance(fixtureClass));
        pico.start();
    }

    public void stop () {
        pico.stop();
        pico.dispose();
    }

    public boolean addClass (Class<?> clazz) {
        if (isInstantiable(clazz) && classes.add(clazz)) {
            addConstructorDependencies(clazz);
        }
        return true;
    }

    public <T> T getInstance (Class<T> type) {
        return pico.getComponent(type);
    }

    private void consumeEagerlyLoadedFixtureClass (Consumer<Class<?>> fixtureClassConsumer) {
        new Reflections(getClass().getPackage().getName(), ClasspathHelper.forJavaClassPath())
                .getTypesAnnotatedWith(Fixture.class).stream()
                .filter(c -> c.getAnnotation(Fixture.class).eagerlyLoad())
                .forEach(fixtureClassConsumer);
    }

    private void addConstructorDependencies (Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            for (Class<?> paramClazz : constructor.getParameterTypes()) {
                addClass(paramClazz);
            }
        }
    }

    private static class FixtureBehavior extends AbstractBehaviorFactory {
        private final ComponentFactory fixtureComponentFactory = new Storing();
        private final ComponentFactory defaultComponentFactory = new Caching();

        @Override
        public <T> ComponentAdapter<T> createComponentAdapter (ComponentMonitor componentMonitor, LifecycleStrategy lifecycleStrategy, Properties componentProperties, Object componentKey, Class<T> componentImplementation, Parameter... parameters) throws PicoCompositionException {
            if (hasFixtureAnnotation(componentKey)) {
                return fixtureComponentFactory.createComponentAdapter(componentMonitor, lifecycleStrategy, componentProperties, componentKey, componentImplementation, parameters);
            }
            return defaultComponentFactory.createComponentAdapter(componentMonitor, lifecycleStrategy, componentProperties, componentKey, componentImplementation, parameters);
        }

        private boolean hasFixtureAnnotation (Object componentKey) {
            if (componentKey instanceof Class<?>) {
                return ((Class<?>) componentKey).isAnnotationPresent(Fixture.class);
            }
            return false;
        }
    }
}