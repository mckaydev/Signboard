package com.project.srchhisto.dao;

import com.project.member.Member;
import com.project.srchhisto.Srchhisto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISrchhistoDAO {
    int storeInsert(Srchhisto srchhisto);
    int getListSize(Member member);
    int getBookmarkedSize(Member member);
    List<Srchhisto> selectHistory(@Param("username") String username,
                                  @Param("startIndex") int startIndex,
                                  @Param("contentPerPage") int contentPerPage);
    List<Srchhisto> selectBookmark(@Param("username") String username,
                                  @Param("startIndex") int startIndex,
                                  @Param("contentPerPage") int contentPerPage);
    List<Srchhisto> storeSelect(Member member);
    List<Srchhisto> bookmarkedStoreSelect(Member member);
    int storeBookmark(Srchhisto srchhisto);
    int storeUnBookmark(Srchhisto srchhisto);
    int storeDelete(Srchhisto srchhisto);
}
