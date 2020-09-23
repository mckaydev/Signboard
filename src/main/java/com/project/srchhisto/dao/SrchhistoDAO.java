package com.project.srchhisto.dao;

import com.project.member.Member;
import com.project.srchhisto.Srchhisto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrchhistoDAO implements ISrchhistoDAO{

    private final SqlSession sqlSession;

    @Autowired
    public SrchhistoDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public int storeInsert(Srchhisto srchhisto) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.storeInsert(srchhisto);
    }

    @Override
    public int testDataInsert() {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.testDataInsert();
    }

    @Override
    public int getListSize(Member member) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.getListSize(member);
    }

    @Override
    public int getBookmarkedSize(Member member) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.getBookmarkedSize(member);
    }

    @Override
    public List<Srchhisto> selectHistoryLimit(String username, int startIndex, int contentPerPage) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.selectHistoryLimit(username, startIndex, contentPerPage);
    }

    @Override
    public List<Srchhisto> selectBookmarkedHistoryLimit(String username, int startIndex, int contentPerPage) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.selectBookmarkedHistoryLimit(username, startIndex, contentPerPage);
    }

    @Override
    public List<Srchhisto> selectHistory(Member member) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.selectHistory(member);
    }

    @Override
    public List<Srchhisto> selectBookmarkedHistory(Member member) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.selectBookmarkedHistory(member);
    }

    @Override
    public int storeBookmark(Srchhisto srchhisto) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.storeBookmark(srchhisto);
    }

    @Override
    public int storeUnBookmark(Srchhisto srchhisto) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.storeUnBookmark(srchhisto);
    }

    @Override
    public int storeDelete(Srchhisto srchhisto) {
        ISrchhistoDAO dao = sqlSession.getMapper(ISrchhistoDAO.class);

        return dao.storeDelete(srchhisto);
    }
}
