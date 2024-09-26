package org.zishi.mq.jdbctemplatedemo.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.zishi.mq.jdbctemplatedemo.service.UserService


@Service
class UserServiceImpl : UserService {

    @Autowired
    private val jdbcTemplate: JdbcTemplate? = null

    override val allUsers: Int?
        get() = jdbcTemplate!!.queryForObject("select count(1) from USER", Int::class.java)

    override fun create(name: String, password: String?) {
        jdbcTemplate!!.update("insert into USER(USERNAME, PASSWORD) values(?, ?)", name, password)
    }

    override fun deleteByName(name: String) {
        jdbcTemplate!!.update("delete from USER where USERNAME = ?", name)
    }

    override fun deleteAllUsers() {
        jdbcTemplate!!.update("delete from USER")
    }
}