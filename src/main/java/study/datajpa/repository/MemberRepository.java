package study.datajpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    // NamedQuery -> 없어도 동작 => NamedQuery 먼저 찾고 없으면 메서드 이름으로 동작
    @Query(name = "Member.findByUsername")
    List<Member> findByUsername(@Param("username") String username);

    // 이름없는 NamedQuery / application loading 시 parsing -> 문법오류 체크 가능
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findUsernameList();

    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // 컬렉션
    List<Member> findListByUsername(String username);

    // 단건
    Member findMemberByUsername(String username);

    // Optional 단건
    Optional<Member> findOptByUsername(String username);

    // Slice<Member> findByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join m.team t",
            countQuery = "select count(m.username) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    // fetch -> 연관된 팀 쿼리하나로 다 끌고옴 (join 은 원하는 옵션으로 적기)
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    // 복잡할 땐 JPQL fetch join 고려 간단할 떈 EntityGraph
    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

    // @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all") // 이런식으로 Entity에 정의한 Named 해서 사용할 수도
    List<Member> findMemberEntityGraphByUsername(@Param("username") String username);

    // 조회만 하는게 확실할 때 이렇게 하면 비용 감소
    @QueryHints(value= @QueryHint(name="org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);
}
