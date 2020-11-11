package ru.itis.javalab.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from \"user\" where id = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_USERNAME = "select * from \"user\" where username = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_UUID = "select * from \"user\" where uuid = ?";

    //language=SQL
    private static final String SQL_SELECT_BY_AGE = "select * from \"user\" where age = ?";

    //language=SQL
    private static final String SQL_SELECT = "select * from \"user\"";

    //language=SQL
    private static final String SQL_UPDATE = "UPDATE \"user\" SET first_name=?, last_name=?, age=?,password=? where uuid =?";

    //language=SQL
    private static final String SQL_INSERT = "insert into \"user\" (first_name, last_name, age, password, username) VALUES (?,?,?,?,?)";


    //language=SQL
    private static final String SQL_UPDATE_UUID = "UPDATE \"user\" SET uuid =?, password =? where username=?";


    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("first_name"))
            .lastName(row.getString("last_name"))
            .age(row.getInt("age"))
            .uuid(row.getString("uuid"))
            .password(row.getString("password"))
            .username(row.getString("username"))
            .build();


    public void updateByUsername(String username, String uuid, String password) {
        jdbcTemplate.update(SQL_UPDATE_UUID, uuid, password, username);
    }

    @Override
    public List<User> findAllByAge(Integer age) {
        // TODO: return template.query(SQL_SELECT_BY_AGE, usersRowMapper, age);
        return jdbcTemplate.query(SQL_SELECT_BY_AGE, userRowMapper, age);

//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(SQL_SELECT_BY_AGE);
//            statement.setInt(1, age);
//            resultSet = statement.executeQuery();
//
//            List<User> users = new ArrayList<>();
//
//            while (resultSet.next()) {
//                User user = userRowMapper.mapRow(resultSet);
//                users.add(user);
//            }
//
//            return users;
//
//        } catch (SQLException e) {
//            throw new IllegalStateException(e);
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException ignore) {
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException ignore) {
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ignore) {
//                }
//            }
//        }
    }

    @Override
    public User findByUsername(String username) {
        return (User) jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, userRowMapper, username);
    }

    @Override
    public Optional<User> findByUuid(String uuid) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_SELECT_BY_UUID, userRowMapper, uuid);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }

        return Optional.ofNullable(user);
    }


    @Override
    public Optional<User> findFirstByFirstnameAndLastname(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        // TODO: return template.query(SQL_SELECT, usersRowMapper);
        return jdbcTemplate.query(SQL_SELECT, userRowMapper);

//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//
//        try {
//            connection = dataSource.getConnection();
//            statement = connection.prepareStatement(SQL_SELECT);
//            resultSet = statement.executeQuery();
//
//            List<User> users = new ArrayList<>();
//
//            while (resultSet.next()) {
//                User user = userRowMapper.mapRow(resultSet);
//
//                users.add(user);
//            }
//
//            return users;
//
//        } catch (SQLException e) {
//            throw new IllegalStateException(e);
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException ignore) {
//                }
//            }
//            if (statement != null) {
//                try {
//                    statement.close();
//                } catch (SQLException ignore) {
//                }
//            }
//            if (connection != null) {
//                try {
//                    connection.close();
//                } catch (SQLException ignore) {
//                }
//            }
//        }
    }

    @Override
    public List<User> findAll(int page, int size) {
        return null;
    }


    @Override
    public Optional<User> findById(Long id) {

        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }

        return Optional.ofNullable(user);


    }

//    @Override
//    public void save(User entity) {
//        String firstName = entity.getFirstName();
//        String lastName = entity.getLastName();
//        Integer age = entity.getAge();
//        String password = entity.getPassword();
//
//        jdbcTemplate.update(SQL_INSERT, firstName, lastName, age, password);
//
//    }

    public Long save(User entity) {
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();
        Integer age = entity.getAge();
        String password = entity.getPassword();

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(SQL_INSERT);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.setString(4, password);
            return ps;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }


    @Override
    public void update(User entity) {
    }

    @Override
    public void deleteById(Long id) {
    }

    @Override
    public void delete(User entity) {
    }

}
