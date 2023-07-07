package org.kinetica.utils;

import com.gpudb.GPUdb;
import com.gpudb.GPUdbBase;
import com.gpudb.GPUdbException;
import com.gpudb.Record;
import com.gpudb.protocol.ExecuteSqlRequest;
import com.gpudb.protocol.ExecuteSqlResponse;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.testcontainers.containers.JdbcDatabaseContainer;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.kinetica.containers.KineticaContainer.KINETICA_PORT;

public class ContainerDatabaseTestUtils {

    public static ResultSet performQuery(JdbcDatabaseContainer<?> container, String sql) throws SQLException {
        DataSource ds = getDataSource(container);
        Statement statement = ds.getConnection().createStatement();
        statement.execute(sql);
        ResultSet resultSet = statement.getResultSet();

        resultSet.next();
        return resultSet;
    }

    private static DataSource getDataSource(JdbcDatabaseContainer<?> container) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(container.getJdbcUrl());
        hikariConfig.setUsername(container.getUsername());
        hikariConfig.setPassword(container.getPassword());
        hikariConfig.setDriverClassName(container.getDriverClassName());
        return new HikariDataSource(hikariConfig);
    }

    public static List<Record> executeKineticaRequest(JdbcDatabaseContainer<?> container, String sql) throws GPUdbException {
        GPUdb kiGPUdb = getKiGPUdb(container);
        ExecuteSqlRequest executeSqlRequest = new ExecuteSqlRequest();
        executeSqlRequest.setStatement(sql);
        executeSqlRequest.setLimit(-1);
        executeSqlRequest.setData(null);
        ExecuteSqlResponse executeSqlResponse = kiGPUdb.executeSql(executeSqlRequest);
        List<Record> records = executeSqlResponse.getData();
        return records;
    }

    private static GPUdb getKiGPUdb(JdbcDatabaseContainer<?> container) {
        GPUdbBase.Options options = new GPUdbBase.Options();
        options.setUsername(container.getUsername());
        options.setPassword(container.getPassword());
        GPUdb kineticaDb = null;
        try {
            kineticaDb = new GPUdb("http://" + container.getHost() + ":" + container.getMappedPort(KINETICA_PORT), options);
        } catch (GPUdbException e) {
            throw new RuntimeException(e);
        }
        return kineticaDb;
    }
}
