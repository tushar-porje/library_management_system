package com.kane.library_management_system.library_management_system.dao.daoimpl;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.kane.library_management_system.library_management_system.dao.UserDao;
import com.kane.library_management_system.library_management_system.entity.User;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user_table";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new User(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email")
        ));
    }

    @Override
    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM user_table WHERE id = ?";
        User user = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> 
            new User(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email")
            ), id);
        return Optional.ofNullable(user);
    }

    @Override
    public long save(User user) {
        String sql = "INSERT INTO user_table (first_name, last_name, email) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    @Override
    public int update(Long id, User user) {
        String sql = "UPDATE user_table SET first_name = ?, last_name = ?, email = ? WHERE id = ?";
        return jdbcTemplate.update(sql, user.getFirstName(), user.getLastName(), user.getEmail(), id);
    }

    @Override
    public int deleteById(Long id) {
        String sql = "DELETE FROM user_table WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
}
