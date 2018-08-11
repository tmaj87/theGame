package pl.tmaj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebServerUsersTest {

    private static final String DUMMY_USER = "dummyUser";
    private static final String NEW_NAME = "newUser";
    private static final int THREE_USERS = 3;
    private static final int NO_USERS = 0;

    private WebServerUsers users;

    @BeforeEach
    void setUp() {
        users = new WebServerUsers();
    }

    @Test
    void shouldAddNewUser() {
        users.addUser(DUMMY_USER);

        assertEquals(DUMMY_USER, users.getUser(DUMMY_USER));
    }

    @Test
    void shouldTranslateUserName() {
        users.addUser(DUMMY_USER);
        users.setUserName(DUMMY_USER, NEW_NAME);

        assertEquals(NEW_NAME, users.getUser(DUMMY_USER));
    }

    @Test
    void shouldPickRandomUser() {
        addDummyUsers(THREE_USERS);

        String randomUser = users.pickRandomUser().getNick();

        assertTrue(randomUser.contains(DUMMY_USER));
    }

    @Test
    void shouldGetUserCount() {
        addDummyUsers(THREE_USERS);

        assertEquals(THREE_USERS, users.count());
    }

    @Test
    void shouldClearUsers() {
        addDummyUsers(THREE_USERS);

        users.clear();

        assertEquals(NO_USERS, users.count());
    }

    private void addDummyUsers(int count) {
        for (int i = 0; i < count; i++) {
            users.addUser(DUMMY_USER + "_" + i);
        }
    }
}
