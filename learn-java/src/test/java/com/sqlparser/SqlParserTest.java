package com.sqlparser;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.SelectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SqlParserTest {

    @Test
    @DisplayName("解析复杂 SQL 查询")
    void test01() throws JSQLParserException {

        String sql = "SELECT username, age FROM users WHERE age > 30";
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        System.out.println("表名: " + plainSelect.getFromItem());
        System.out.println("条件: " + plainSelect.getWhere());
    }

    @Test
    @DisplayName("动态生成 SQL 查询")
    void test02() throws JSQLParserException {
        Select select = SelectUtils.buildSelectFromTable(new Table("users"));
        System.out.println(select.toString());
    }

    @Test
    @DisplayName("修改 SQL 查询")
    void test03() throws JSQLParserException {
        String sql = "SELECT username FROM users";
        Select select = (Select) CCJSqlParserUtil.parse(sql);
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression("age > 30"));
        System.out.println("修改后的 SQL: " + select);
    }

    @Test
    @DisplayName("SQL 注入检测")
    void test04() throws JSQLParserException {
//        String sql = "SELECT username FROM users";
//        Select select = (Select) CCJSqlParserUtil.parse(sql);
//        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
//        plainSelect.setWhere(CCJSqlParserUtil.parseCondExpression("age > 30"));
//        System.out.println("修改后的 SQL: " + select);
    }


}
