package ru.itis.servlets.repositories;
;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.itis.servlets.models.Role;
import ru.itis.servlets.models.State;
import ru.itis.servlets.models.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UsersRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String SQL_SELECT =
            "SELECT * FROM users where id = ?";
    @Override
    public Optional<User> find(Long aLong) {
            try {
                User user = jdbcTemplate.queryForObject(SQL_SELECT, new Object[]{aLong}, userRowMapper);
                return Optional.ofNullable(user);
            } catch (EmptyResultDataAccessException e) {
                return Optional.empty();
            }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    //language=sql
    private static final String SQL_INSERT =
            "INSERT INTO users (birth_day, nick, login, password, mail, state, code, role) values (?,?,?,?,?,?,?,?)";

    @Override
    public void save(User entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_INSERT);
            statement.setObject(1, entity.getBirthDay());
            statement.setString(2, entity.getNick());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getMail());
            statement.setString(6, entity.getState().toString());
            statement.setString(7, entity.getConfirmCode());
            statement.setString(8, entity.getRole().toString());
            return statement;
        }, keyHolder);
        entity.setId((Long) keyHolder.getKey());
    }

    @Override
    public void delete(Long aLong) {

    }

    private static final String SQL_UPDATE =
            "UPDATE users set birth_day = ?, nick = ?, login = ?, password = ?, mail = ?, state = ?, code = ?, role = ? where id = ?";

    @Override
    public void update(User entity) {
        int update = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection
                    .prepareStatement(SQL_UPDATE);
            statement.setObject(1, entity.getBirthDay());
            statement.setString(2, entity.getNick());
            statement.setString(3, entity.getLogin());
            statement.setString(4, entity.getPassword());
            statement.setString(5, entity.getMail());
            statement.setString(6, entity.getState().toString());
            statement.setString(7, entity.getConfirmCode());
            statement.setString(8, entity.getRole().toString());
            statement.setLong(9, entity.getId());
            return statement;
        });
        if (update == 0) {
            throw new IllegalStateException("Balance update failed");
        }
    }

    private RowMapper<User> userRowMapper = (row, rowNumber) ->
            User.builder()
                    .id(row.getLong("id"))
                    .birthDay(row.getDate("birth_day"))
                    .nick(row.getString("nick"))
                    .login(row.getString("login"))
                    .password(row.getString("password"))
                    .mail(row.getString("mail"))
                    .state(State.valueOf(row.getString("state")))
                    .role(Role.valueOf(row.getString("role")))
                    .confirmCode(row.getString("code"))
                    .build();

    private static final String SQL_SELECT_BY_CODE =
            "SELECT * FROM users where code = ?";

    @Override
    public Optional<User> findByCode(String code) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_CODE, new Object[]{code}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final String SQL_SELECT_BY_LOGIN =
            "SELECT * FROM users where login = ?";

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_LOGIN, new Object[]{login}, userRowMapper);
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}
