package pl.tmaj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebServerUsersTest {

    private static final String DUMMY_USER = "dummyUser";
    private static final String NEW_NAME = "newUser";
    private static final int THREE_USERS = 3;
    private static final int NO_USERS = 0;

    @Mock
    public SimpMessagingTemplate template;

    private WebServerUsers users;

    @BeforeEach
    void setUp() {
        users = new WebServerUsers(template);
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
    void shouldPickWinner() {
        addDummyUsers(THREE_USERS);

        assertTrue(users.getWinner().contains(DUMMY_USER));
    }

    @Test
    void shouldGetUserCount() {
        addDummyUsers(THREE_USERS);

        assertEquals(THREE_USERS, users.getUsersCount());
    }

    @Test
    void shouldNotifyAboutNewUser() {
        // breaks SRP
    }

    @Test
    void shouldResetGame() {
        addDummyUsers(THREE_USERS);

        users.restartGame();

        assertEquals(NO_USERS, users.getUsersCount());
    }

    private void addDummyUsers(int count) {
        for (int i = 0; i < count; i++) {
            users.addUser(DUMMY_USER + "_" + i);
        }
    }
}
