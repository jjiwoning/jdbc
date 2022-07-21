package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;


@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        //save test
        Member member = new Member("v101", 10000);
        repositoryV0.save(member);

        //findById test
        Member findMember = repositoryV0.findByid(member.getMemberId());
        log.info("findMember = {}", findMember);
        assertThat(findMember).isEqualTo(member);

        //update -> money 10000 -> 20000
        repositoryV0.update(member.getMemberId(), 20000);
        Member updatedMember = repositoryV0.findByid(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        //delete
        repositoryV0.delete(member.getMemberId());
        assertThatThrownBy(() -> repositoryV0.findByid(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }

}