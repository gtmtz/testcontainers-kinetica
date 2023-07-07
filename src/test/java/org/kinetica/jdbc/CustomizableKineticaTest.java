package org.kinetica.jdbc;

import org.junit.Test;
import org.kinetica.containers.AbstractContainerBaseTest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.kinetica.utils.ContainerDatabaseTestUtils.performQuery;

public class CustomizableKineticaTest extends AbstractContainerBaseTest {

    @Test
    public void testQueryString() throws SQLException {
        ResultSet resultSet = performQuery(kineticaContainer, kineticaContainer.getTestQueryString());
        int resultSetInt = resultSet.getInt(1);
        assertThat(resultSetInt).as("A basic SELECT query succeeds").isEqualTo(1);
    }

    @Test
    public void testExplicitInitScript() throws SQLException {
        ResultSet resultSet = performQuery(kineticaContainer, "SELECT foo FROM bar");
        String firstColumnValue = resultSet.getString(1);
        assertThat(firstColumnValue).as("Value from init script should equal real value").isEqualTo("hello world");
    }
}
