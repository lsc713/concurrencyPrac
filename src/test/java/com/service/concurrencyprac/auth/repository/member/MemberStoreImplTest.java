package com.service.concurrencyprac.auth.repository.member;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.service.concurrencyprac.auth.domain.member.Member;
import com.service.concurrencyprac.common.exception.EntityAlreadyExistException;
import com.service.concurrencyprac.common.exception.InvalidParamException;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberStoreImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberStoreImpl memberStore;

    @AfterEach
    public void tearDown() {
        memberRepository.deleteAllInBatch();
    }

    @Test
    public void testStoreMemberSuccessful() {
        Member member = createMember();

        // store 메서드 호출
        Member stored = memberStore.store(member);

        // 검증
        assertNotNull(stored);
        assertEquals("test@example.com", stored.getEmail());
    }

    @Test
    public void testStoreMemberAlreadyExists() {
        Member member = createMember();
        memberRepository.save(member);

        EntityAlreadyExistException exception = assertThrows(
            EntityAlreadyExistException.class, () -> {
                memberStore.store(member);
            });

        assertEquals("test@example.com", exception.getMessage());
    }

    @Test
    public void testStoreMemberInvalidEmail() {
        Member member = createMember().toBuilder().email(null).build();

        // 예외 검증
        assertThrows(InvalidParamException.class, () -> {
            memberStore.store(member);
        });
    }

    @Test
    public void testStoreMemberInvalidName() {
        Member member = createMember().toBuilder().name(null).build();
        // 예외 검증
        assertThrows(InvalidParamException.class, () -> {
            memberStore.store(member);
        });
    }

    @Test
    public void testStoreMemberInvalidNickName() {
        Member member = createMember().toBuilder().nickName(null).build();

        // 예외 검증
        assertThrows(InvalidParamException.class, () -> {
            memberStore.store(member);
        });
    }

    @Test
    public void testStoreMemberInvalidStatus() {
        Member member = createMember();
        member.disable();

        // 예외 검증
        assertThrows(InvalidParamException.class, () -> {
            memberStore.store(member);
        });
    }

    @Test
    public void testStoreMemberInvalidToken() throws NoSuchFieldException, IllegalAccessException {
        Member member = createMember();
        Field memberToken = Member.class.getDeclaredField("memberToken");
        memberToken.setAccessible(true);
        memberToken.set(member,null);
        // 예외 검증
        assertThrows(InvalidParamException.class, () -> {
            memberStore.store(member);
        });
    }

    private static Member createMember() {
        return Member.builder()
            .email("test@example.com")
            .password("password")
            .name("Test Name")
            .nickName("TestNickName")
            .role(Member.UserRole.USER)
            .build();
    }
}