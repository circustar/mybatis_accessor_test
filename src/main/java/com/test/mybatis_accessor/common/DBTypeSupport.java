package com.test.mybatis_accessor.common;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

public class DBTypeSupport {
    public static DBType getDBType(Connection connection) {
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName().toLowerCase();
            if (databaseProductName.indexOf("oracle") >= 0) {
                return DBType.ORACLE;
            }

            if (databaseProductName.indexOf("mysql") >= 0) {
                return DBType.MYSQL;
            }

            if (databaseProductName.indexOf("db2") >= 0) {
                return DBType.DB2;
            }
        } catch (Exception var4) {
        }

        return DBType.UNKNOWN;
    }
    public static DBType getDbType(DataSource dataSource) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            return getDBType(connection);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if(connection!= null) {
                try {
                    connection.close();
                } catch (Exception ex2) {

                }
            }
        }
    }
}
