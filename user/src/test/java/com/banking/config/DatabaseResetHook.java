package com.banking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import io.cucumber.java.Before;

public class DatabaseResetHook {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void resetDatabase() {
        jdbcTemplate.execute("DELETE FROM users");
    }
}
