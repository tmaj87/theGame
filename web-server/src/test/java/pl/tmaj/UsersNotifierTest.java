package pl.tmaj;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import pl.tmaj.common.SimpleMessage;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.tmaj.common.SimpleMessageType.*;

public class UsersNotifierTest {

    private static final String DUMMY_USER = "dummyUser";
    private static final int ONE_USER = 1;
    private static final String DESTINATION = "/feed/info";

    private SimpMessagingTemplate template = mock(SimpMessagingTemplate.class);
    private UsersNotifier notifier = new UsersNotifier(template);

    @Test
    void shouldNotifyAboutNewUser() {
        notifier.notifyJoin(DUMMY_USER);

        verify(template).convertAndSend(DESTINATION, new SimpleMessage(DUMMY_USER, JOIN));
    }

    @Test
    void shouldNotifyAboutLeavingUser() {
        notifier.notifyLeft(DUMMY_USER);

        verify(template).convertAndSend(DESTINATION, new SimpleMessage(DUMMY_USER, LEFT));
    }

    @Test
    void shouldNotifyAboutUsersCount() {
        notifier.notifyCount(ONE_USER);

        verify(template).convertAndSend(DESTINATION, new SimpleMessage(String.valueOf(ONE_USER), COUNT));
    }

    @Test
    void shouldNotifyAboutWinner() {
        notifier.notifyWon(DUMMY_USER);

        verify(template).convertAndSend(DESTINATION, new SimpleMessage(DUMMY_USER, WON));
    }
}
