package ru.itdrive.web.repositories;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

import ru.itdrive.web.models.User;

@Component(value = "usersRepositoryJdbcImpl")
public class UsersRepositoryJdbcImpl implements UsersRepository {

    //language=SQL
    private static final String SQL_INSERT_USER = "insert into simple_user(email, password, confirm_code) " +
            "values (?, ?, ?)";

    //language=SQL
    private static final String SQL_UPDATE_USER = "update simple_user set email = ?, password = ?, confirm_code = ?, is_deleted = ? " +
            "where id = ?";

    //language=SQL
    private static final String SQL_FIND_USER_BY_ID = "select * from simple_user where id = ?";

    //language=SQL
    private static final String SQL_FIND_USER_BY_EMAIL = "select * from simple_user where email = ?";

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .confirmCode(row.getString("confirm_code"))
            .password(row.getString("password"))
            .isDeleted(row.getBoolean("is_deleted"))
            .build();


    @Override
    public void save(User entity) {
        jdbcTemplate.update(SQL_INSERT_USER, entity);
    }

    @Override
    public void update(User entity) {
        jdbcTemplate.update(SQL_UPDATE_USER, entity);
    }

    @Override
    public Optional<User> findOne(Long id) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_ID, userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        User user;
        try {
            user = jdbcTemplate.queryForObject(SQL_FIND_USER_BY_EMAIL, userRowMapper, email);
        } catch (EmptyResultDataAccessException e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }
}
