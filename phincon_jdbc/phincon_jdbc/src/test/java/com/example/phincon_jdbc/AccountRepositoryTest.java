package com.example.phincon_jdbc;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.example.phincon_jdbc.repository.AccountRepository;

@JdbcTest
@Sql({"schema.sql", "test-data.sql"})
public class AccountRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository repo;

    @BeforeEach
    void setDatabase() {
        
    }

}
