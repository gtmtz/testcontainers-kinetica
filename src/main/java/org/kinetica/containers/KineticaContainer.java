package org.kinetica.containers;

import org.jetbrains.annotations.NotNull;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Set;

public class KineticaContainer extends JdbcDatabaseContainer<KineticaContainer> {

    private static final DockerImageName DEFAULT_IMAGE_NAME = DockerImageName.parse("kinetica/kinetica-intel");

    static final String DEFAULT_TAG = "latest";

    static final String DEFAULT_USER = "admin";

    static final String DEFAULT_PASSWORD = "admin";

    public static final Integer KINETICA_PORT = 9191;

    private String databaseName = "kinetica";

    private String username = DEFAULT_USER;

    private String password = DEFAULT_PASSWORD;

    public KineticaContainer(String dockerImageName) {
        this(DockerImageName.parse(dockerImageName));
    }

    public KineticaContainer(final DockerImageName dockerImageName) {
        super(dockerImageName);
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME);
        addExposedPort(KINETICA_PORT);
    }

    /**
     * @return the ports on which to check if the container is ready
     * @deprecated use {@link #getLivenessCheckPortNumbers()} instead
     */
    @NotNull
    @Override
    @Deprecated
    protected Set<Integer> getLivenessCheckPorts() {
        return super.getLivenessCheckPorts();
    }

    @Override
    protected void configure() {
        addEnv("KINETICA_USER", username);
        addEnv("KINETICA_PASSWORD", password);
        addEnv("FULL_START", "1");
        setStartupAttempts(2);
    }

    @Override
    public String getDriverClassName() {
        return "com.kinetica.jdbc.Driver";
    }

    @Override
    public String getJdbcUrl() {
        return "jdbc:kinetica://" + getHost() + ":" + getMappedPort(KINETICA_PORT);
    }

    @Override
    protected String constructUrlForConnection(String queryString) {
        String url = super.constructUrlForConnection(queryString);
        return url;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getTestQueryString() {
        return "SELECT 1";
    }

    @Override
    public KineticaContainer withDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
        return self();
    }

    @Override
    public KineticaContainer withUsername(final String username) {
        this.username = username;
        return self();
    }

    @Override
    public KineticaContainer withPassword(final String password) {
        this.password = password;
        return self();
    }

    @Override
    protected void waitUntilContainerStarted() {
        getWaitStrategy().waitUntilReady(this);
    }
}

