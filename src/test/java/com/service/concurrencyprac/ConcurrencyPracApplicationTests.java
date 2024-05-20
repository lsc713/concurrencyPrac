//package com.service.concurrencyprac;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//@SpringBootTest
//@Transactional
//class BoardApplicationTest {
//
//    @Autowired
//    JdbcTemplate jdbcTemplate;
//
//    @Test
//    public void testJdbcConnection() {
//        //given
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS test_table (id INT, name VARCHAR(255))");
//        jdbcTemplate.update("INSERT INTO test_table VALUES (1, 'Kim')");
//
//        //when
//        String result = jdbcTemplate.queryForObject("SELECT name FROM test_table WHERE id = 1",
//            String.class);
//
//        //then
//        assertTrue("Kim".equals(result));
//    }
//
//}
