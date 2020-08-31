package com.project.member.dao;

import com.project.member.LMember;
import com.project.member.Member;

public interface IMemberDAO {
    int memberCreate(Member member);
    Member memberRead(Member member);
    LMember findById(String username);
    int memberUpdate(Member member);
    int memberDelete(Member member);
    void tableCreate(Member member);
    void tableDelete(Member member);
}
