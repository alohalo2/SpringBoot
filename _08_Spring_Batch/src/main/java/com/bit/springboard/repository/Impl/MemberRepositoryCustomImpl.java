package com.bit.springboard.repository.Impl;

import com.bit.springboard.entity.Member;
import com.bit.springboard.repository.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.bit.springboard.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {
    // QueryDSL의 메소드를 이용해서 쿼리를 생성하는 클래스
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Member searchOne(Long id) {
        return jpaQueryFactory.selectFrom(member) // 모든 필드 내용 조회
                .orderBy(member.id.desc()) // 문법 순서가 이상한 것들의 대부분은 알아서 정렬해준다.
                .where(member.id.eq(id).and(member.nickname.contains("bit")))
                .fetchOne();
    }

    @Override
    public String searchUsername(Long id) {
        return jpaQueryFactory.select(member.username)
                .from(member) // 특정 필드만 내용 조회
                .where(member.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Member> findInactiveMembers(LocalDateTime oneYearAgo) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.lastLoginDate.before(oneYearAgo))
                .fetch();
    }



















}
