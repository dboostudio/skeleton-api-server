package studio.dboo.api.module.v1.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import studio.dboo.api.module.v1.member.vo.Member;
import studio.dboo.api.module.v1.member.vo.QMember;

import java.util.Optional;

@Repository
public class QMemberRepository extends QuerydslRepositorySupport  implements QMemberRepositorySupport {

    private QMember qMember = QMember.member;
    private final JPAQueryFactory queryFactory;

    public QMemberRepository(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
        this.queryFactory = jpaQueryFactory;
    }

}
