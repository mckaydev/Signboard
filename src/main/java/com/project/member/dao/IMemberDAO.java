package com.project.member.dao;

import com.project.member.Member;

public interface IMemberDAO {
    int memberCreate(Member member);
    Member memberRead(Member member);
    int memberUpdate(Member member);
    int memberDelete(Member member);
}
