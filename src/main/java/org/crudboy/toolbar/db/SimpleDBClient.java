package org.crudboy.toolbar.db;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SimpleDBClient {

    private DataSource dataSource;

    public SimpleDBClient(String dbType, String ip, String port, String dbName, String userName, String password) {
        BasicDataSource ds = new BasicDataSource();
        String driverClassName = JDBCDriverMap.getDriverClassName(dbType);
        String url = JDBCDriverMap.getURL(dbType, ip, port, dbName);
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(userName);
        ds.setPassword(password);
        dataSource = ds;
    }

    public List<Map<String, Object>> queryAsMap(String sql) throws SQLException {
        QueryRunner run = new QueryRunner(dataSource);
        List<Map<String, Object>> maps = run.query(sql, new MapListHandler());
        return maps;
    }

    public List<Object[]> queryAsObject(String sql) throws SQLException {
        QueryRunner run = new QueryRunner(dataSource);
        List<Object[]> arrays = run.query(sql, new ArrayListHandler());
        return arrays;
    }

    public <T> List<T> queryAsBean(String sql, Class<T> tClass) throws SQLException {
        QueryRunner run = new QueryRunner(dataSource);
        ResultSetHandler<List<T>> resultHandler = new BeanListHandler<T>(tClass);
        List<T> results = run.query(sql, resultHandler);
        return results;
    }
}
