package com.project.srchhisto.dao;

import com.project.srchhisto.Srchhisto;

public interface ISrchhistoDAO {
    int storeInsert(Srchhisto srchhisto);
    int storeSelect(Srchhisto srchhisto);
    int bookmarkedStoreSelect(Srchhisto srchhisto);
    int storeDelete(Srchhisto srchhisto);
}
