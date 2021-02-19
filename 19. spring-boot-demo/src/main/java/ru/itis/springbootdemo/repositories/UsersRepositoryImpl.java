package ru.itis.springbootdemo.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.itis.springbootdemo.models.User;

import javax.sql.DataSource;
import java.util.List;


@Repository
public class UsersRepositoryImpl implements UsersRepository {

    @Autowired
    private DataSource dataSource;

    //language=sql
    String INSERT_SQL = "insert into users (email, password, \"confirmCode\", state) values (?, ?, ?, ?)";
    //language=sql
    String SELECT_ALL_SQL = "select * from users";

    private JdbcTemplate jdbcTemplate;


    public UsersRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userRowMapper = (row, i) -> User.builder()
            .id(row.getLong("id"))
            .email(row.getString("email"))
            .password(row.getString("password"))
            .confirmCode(row.getString("confirmCode"))
            .state(User.State.valueOf(row.getString("state")))
            .build();

    public void save(User user) {
        jdbcTemplate.update(INSERT_SQL, user.getEmail(), user.getPassword(), user.getConfirmCode(), user.getState().toString());

    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, userRowMapper);
    }
}
