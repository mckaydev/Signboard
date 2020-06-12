package com.project.srchhisto.dao;

import com.project.member.Member;
import com.project.srchhisto.Srchhisto;

import java.util.List;

public interface ISrchhistoDAO {
    int storeInsert(Srchhisto srchhisto);
    List<Srchhisto> storeSelect(Member member);
    List<Srchhisto> bookmarkedStoreSelect(Member member);
    int storeBookmark(Srchhisto srchhisto);
    int storeUnBookmark(Srchhisto srchhisto);
    int storeDelete(Srchhisto srchhisto);
}
