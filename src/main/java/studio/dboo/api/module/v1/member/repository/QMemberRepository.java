package studio.dboo.api.module.v1.member.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import studio.dboo.api.module.v1.member.vo.QMember;

@Repository
public class QMemberRepository extends QuerydslRepositorySupport  implements QMemberRepositorySupport {

    private final QMember qMember = QMember.member;

    public QMemberRepository(Class<?> domainClass, JPAQueryFactory jpaQueryFactory) {
        super(domainClass);
    }

}
