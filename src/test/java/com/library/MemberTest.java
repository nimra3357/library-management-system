package com.library;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Member class.
 */
@Tag("fast")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Member Class Tests")
class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("M001", "Ali Raza", "ali@example.com");
    }

    // ============================
    // POSITIVE TESTS
    // ============================

    @Test
    @Order(1)
    @DisplayName("Should create member when all valid details are provided")
    void should_CreateMember_when_ValidDetailsProvided() {
        assertNotNull(member);
        assertEquals("M001", member.getMemberId());
        assertEquals("Ali Raza", member.getName());
        assertEquals("ali@example.com", member.getEmail());
    }

    @Test
    @Order(2)
    @DisplayName("Should have zero borrowed count when newly created")
    void should_HaveZeroBorrowedCount_when_NewMemberCreated() {
        assertEquals(0, member.getBorrowedCount());
    }

    @Test
    @Order(3)
    @DisplayName("Should be able to borrow when borrow count is below limit")
    void should_AllowBorrowing_when_BorrowCountBelowLimit() {
        assertTrue(member.canBorrow());
    }

    @Test
    @Order(4)
    @DisplayName("Should increment borrow count when incrementBorrow is called")
    void should_IncrementBorrowCount_when_IncrementBorrowCalled() {
        member.incrementBorrow();
        assertEquals(1, member.getBorrowedCount());
    }

    @Test
    @Order(5)
    @DisplayName("Should decrement borrow count when decrementBorrow is called")
    void should_DecrementBorrowCount_when_DecrementBorrowCalled() {
        member.incrementBorrow();
        member.incrementBorrow();
        member.decrementBorrow();
        assertEquals(1, member.getBorrowedCount());
    }

    // ============================
    // NEGATIVE TESTS
    // ============================

    @Test
    @Order(6)
    @DisplayName("Should throw exception when member ID is null")
    void should_ThrowException_when_MemberIdIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> new Member(null, "Test User", "test@email.com"));
    }

    @Test
    @Order(7)
    @DisplayName("Should throw exception when email has no @ symbol")
    void should_ThrowException_when_EmailIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Member("M002", "Test User", "invalidemail"));
    }

    @Test
    @Order(8)
    @DisplayName("Should not be able to borrow when limit is reached")
    void should_DisallowBorrowing_when_BorrowLimitReached() {
        member.incrementBorrow();
        member.incrementBorrow();
        member.incrementBorrow();
        assertFalse(member.canBorrow());
    }

    // ============================
    // BOUNDARY TESTS
    // ============================

    @Test
    @Order(9)
    @DisplayName("Should throw exception when incrementing beyond borrow limit")
    void should_ThrowException_when_BorrowingBeyondLimit() {
        member.incrementBorrow();
        member.incrementBorrow();
        member.incrementBorrow();
        assertThrows(IllegalStateException.class, () -> member.incrementBorrow());
    }

    // ============================
    // EXCEPTION HANDLING TESTS
    // ============================

    @Test
    @Order(10)
    @DisplayName("Should throw exception when decrementing with zero borrowed books")
    void should_ThrowException_when_DecrementingWithNoBorrowedBooks() {
        assertThrows(IllegalStateException.class, () -> member.decrementBorrow());
    }
}
