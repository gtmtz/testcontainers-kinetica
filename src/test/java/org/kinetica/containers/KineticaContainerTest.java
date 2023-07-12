package org.kinetica.containers;

import org.junit.Test;
import org.testcontainers.containers.wait.strategy.WaitAllStrategy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kinetica.KineticaTestImages.KINETICA_INTEL_LATEST_IMAGE;

public class KineticaContainerTest {

    @Test
    public void containerStarted() {
        try (KineticaContainer kineticaContainer = new KineticaContainer(KINETICA_INTEL_LATEST_IMAGE)) {
            kineticaContainer.addEnv("FULL_START", "0");
            kineticaContainer.waitingFor(new WaitAllStrategy().withStartupTimeout(Duration.of(30, ChronoUnit.SECONDS)));
            kineticaContainer.start();
            assertThat(kineticaContainer.isRunning()).isTrue();
        }
    }
}
