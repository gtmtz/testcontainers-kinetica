package org.kinetica.containers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import static org.kinetica.KineticaTestImages.KINETICA_INTEL_LATEST_IMAGE;

public abstract class AbstractContainerBaseTest {

    private static final Logger logger = LoggerFactory.getLogger(AbstractContainerBaseTest.class);
    public static final KineticaContainer kineticaContainer;
    private static final String USER = "admin";
    private static final String PWD = "admin";

    static {
        kineticaContainer = new KineticaContainer(KINETICA_INTEL_LATEST_IMAGE);
        kineticaContainer.withInitScript("somepath/init_kinetica.sql");
        kineticaContainer.withUsername(USER);
        kineticaContainer.withPassword(PWD);
        kineticaContainer.withLogConsumer(new Slf4jLogConsumer(logger));
        kineticaContainer.addEnv("FULL_START", "1");

        long start = System.currentTimeMillis();
        kineticaContainer.start();
        logger.info("Kinetica container start completed after: " + ((System.currentTimeMillis() - start) / 1000) + " seconds.");
    }
}
