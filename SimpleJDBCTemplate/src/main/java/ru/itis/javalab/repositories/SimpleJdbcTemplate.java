package ru.itis.javalab.repositories;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleJdbcTemplate {
    private final DataSource dataSource;
    
    public SimpleJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> T queryForObject(String SQL, RowMapper<T> rowMapper, Object... args) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(SQL);

            T result = (T) new Object();
            int i = 1;
            for (Object argz : args) {
                preparedStatement.setObject(i, argz);
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result = rowMapper.mapRow(resultSet);
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }

    public <T> List<T> query(String SQL, RowMapper<T> rowMapper, Object... args) {
        Connection con = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            con = dataSource.getConnection();
            preparedStatement = con.prepareStatement(SQL);

            List<T> result = new ArrayList<>();
            int i = 1;
            for (Object argz : args) {
                preparedStatement.setObject(i, argz);
                i++;
            }
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(rowMapper.mapRow(resultSet));
            }

            return result;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    //ignore
                }
            }
        }
    }
}
