package com.project.member.service;

import com.project.member.Member;

public interface IMemberService {
    int memberRegister(Member member);
    Member memberSearch(Member member);
    Member memberModify(Member member);
    int memberRemove(Member member);
}
