package ru.itis.javalab.repositories;

import ru.itis.javalab.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private DataSource dataSource;

    //language=SQL
    private static final String SQL_SELECT_BY_AGE = "select * from table_2 where age = ?";

    //language=SQL
    private static final String SQL_SELECT = "select * from table_2";

    //language=SQL
    private static final String SQL_SELECT_BY_ID = "select * from table_2 where id = ?";


    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private RowMapper<User> userRowMapper = row -> User.builder()
            .id(row.getLong("id"))
            .firstName(row.getString("firstName"))
            .lastName(row.getString("lastName"))
            .age(row.getInt("age"))
            .build();


    @Override
    public List<User> findAllByAge(Integer age) {
        // TODO: return template.query(SQL_SELECT_BY_AGE, usersRowMapper, age);
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
        return template.query(SQL_SELECT_BY_AGE, userRowMapper, age);
    }

    @Override
    public Optional<User> findFirstByFirstnameAndLastname(String firstName, String lastName) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        // TODO: return template.query(SQL_SELECT, usersRowMapper);
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
        return template.query(SQL_SELECT, userRowMapper);
    }

    @Override
    public Optional<User> findById(Long id) {
        SimpleJdbcTemplate template = new SimpleJdbcTemplate(dataSource);
        User user = template.queryForObject(SQL_SELECT_BY_ID, userRowMapper, id);
        return Optional.ofNullable(user);
    }

    @Override
    public void save(User entity) {
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
