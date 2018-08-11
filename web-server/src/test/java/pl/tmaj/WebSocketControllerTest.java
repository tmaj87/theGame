package pl.tmaj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.tmaj.SimpleMessageType.JOIN;
import static pl.tmaj.SimpleMessageType.MESSAGE;

public class WebSocketControllerTest {

    private static final String DUMMY_USER = "dummyUser";
    private static final String EMPTY = "";
    private static final SimpleMessage EMPTY_MESSAGE = new SimpleMessage(EMPTY, MESSAGE);
    private static final SimpleMessage JOIN_MESSAGE = new SimpleMessage(DUMMY_USER, JOIN);
    private static final SimpleMessage JOIN_NOTIFICATION = new SimpleMessage(DUMMY_USER, MESSAGE);
    private static final String NEW_NAME = "newName";

    private UsersHandler users = mock(UsersHandler.class);
    private SimpMessageHeaderAccessor header = mock(SimpMessageHeaderAccessor.class);
    private WebSocketController controller = new WebSocketController(users);

    @BeforeEach
    void setUp() {
        when(header.getSessionId()).thenReturn(DUMMY_USER);
    }

    @Test
    void shouldNotBeAbleToSendEmptyMessage() throws Exception {
        SimpleMessage result = controller.info(EMPTY_MESSAGE, header);

        assertNull(result);
    }

    @Test
    void shouldFeedNewMessage() throws Exception {
        SimpleMessage result = controller.info(JOIN_MESSAGE, header);

        assertEquals(JOIN_NOTIFICATION, result);
    }

    @Test
    void shouldHandleNameChange() throws Exception {
        controller.username(NEW_NAME, header);

        verify(users).setUserName(DUMMY_USER, NEW_NAME);
    }
}
