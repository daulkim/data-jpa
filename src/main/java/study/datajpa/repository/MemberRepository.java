package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // NamedQuery -> 없어도 동작 => NamedQuery 먼저 찾고 없으면 메서드 이름으로 동작
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username );
}
