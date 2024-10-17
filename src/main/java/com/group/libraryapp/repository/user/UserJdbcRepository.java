package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id) {
        String readSql = "SELECT * FROM user where id = ?";
        // 조회한 결과값이 하나라도 있다면 무조건 0을 출력한다
        // 하지만 query list를 return하므로 isEmpty와 boolean을 사용
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
    }

    public void updateUserName(String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }
    public boolean isUserNotExist(String name) {
        String readSql = "SELECT * FROM user where name = ?";
        // 조회한 결과값이 하나라도 있다면 무조건 0을 출력한다
        // 하지만 query list를 return하므로 isEmpty와 boolean을 사용
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
    }

    public void deleteUser(String name) {
        String sql = "DELETE from user where name = ?";
        jdbcTemplate.update(sql, name);
    }

    public void saveUser(String name, Integer age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        // update는 insert, update, delete 쿼리에 사용될 수 있다(데이터의 변경이 발생함)
        jdbcTemplate.update(sql, name, age);
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        // mapRow : 위의 sql을 실행하고 가져온 유저정보들을 UserResponse로 바꿔줌
        // mapRow 함수의 결과값 = UserResponse
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }
}
