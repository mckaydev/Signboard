package com.project.srchhisto.dao;

import com.project.member.Member;
import com.project.srchhisto.Srchhisto;

import java.util.List;

public interface ISrchhistoDAO {
    int storeInsert(Srchhisto srchhisto);
    List<Srchhisto> storeSelect(Member member);
    int bookmarkedStoreSelect(Srchhisto srchhisto);
    int storeDelete(Srchhisto srchhisto);
}
