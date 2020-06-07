package com.project.member.dao;

import com.project.member.Member;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.sql.*;

@Service
public class MemberDAO implements IMemberDAO{
//    private String driver = "oracle.jdbc.driver.OracleDriver";
//    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
//    private String dbId = "C##MCKAY";
//    private String dbPw = "rewq7428";

    private String mysqlDriver = "com.mysql.cj.jdbc.Driver";
    private String mysqlUrl = "jdbc:mysql://localhost:3306/signboard?serverTimezone=Asia/Seoul";
    private String mysqldbId = "jutabi";
    private String mysqldbPw = "test1234";

    private DriverManagerDataSource dataSource;
    private JdbcTemplate template;

//    private Connection connection = null;
//    private PreparedStatement preparedStatement = null;
//    private ResultSet resultSet = null;
    public MemberDAO() {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(mysqlDriver);
        dataSource.setUrl(mysqlUrl);
        dataSource.setUsername(mysqldbId);
        dataSource.setPassword(mysqldbPw);
        // dataSource.setDriverClass(driver) - (c3p0)
        // dataSource.setJdbcUrl(url) - (c3p0)
        // dataSource.setUser(dbId) - (c3p0)
        template = new JdbcTemplate();
        template.setDataSource(dataSource);
    }

    @SneakyThrows
    @Override
    public int memberCreate(Member member) {
        int result;
        String sql = "insert into member(memberId, memberPw, memberEmail) values (?, ?, ?)";
        result = template.update(sql, member.getMemberId(), member.getMemberPw(), member.getMemberEmail());

//        try {
//            Class.forName(driver);
//            connection = DriverManager.getConnection(url, dbId, dbPw);
//            String sql = "INSERT INTO member(memberId, memberPw, memberEmail) values (?, ?, ?)";
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, member.getMemberId());
//            preparedStatement.setString(2, member.getMemberPw());
//            preparedStatement.setString(3, member.getMemberEmail());
//            result.set(preparedStatement.executeUpdate());
//        } catch (ClassNotFoundException | SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if (preparedStatement != null) { preparedStatement.close(); }
//            if (connection != null) { connection.close(); }
//        }
        return result;
    }

    @Override
    public Member memberRead(Member member) {
        List<Member> members;
        String sql = "select * from member where memberId = ? and memberPw = ?";
        members = template.query(sql, preparedStatement -> {
            preparedStatement.setString(1, member.getMemberId());
            preparedStatement.setString(2, member.getMemberPw());
        }, (resultSet, rowNum) -> {
            Member mem = new Member();
            mem.setMemberId(resultSet.getString("memberId"));
            mem.setMemberPw(resultSet.getString("memberPw"));
            mem.setMemberEmail(resultSet.getString("memberEmail"));
            return mem;
        });
        if(members.isEmpty()) { return null; }
        return members.get(0);
    }

    @Override
    public int memberUpdate(Member member) {
        int result;
        String sql = "update member set memberPw = ?, memberEmail = ? where memberId = ?";
        result = template.update(sql, member.getMemberPw(), member.getMemberEmail(), member.getMemberId());
        return result;
    }

    @Override
    public int memberDelete(Member member) {
        int result;
//        String oraclesql = "delete member where memberId = ? and memberPw = ? and memberEmail = ?";
        String sql = "delete from member where memberId = ? and memberPw = ? and memberEmail = ?";
        result = template.update(sql, member.getMemberId(), member.getMemberPw(), member.getMemberEmail());
        return result;
    }
}
