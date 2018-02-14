package pl.tmaj;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
class WebClient extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/feed");
        config.setApplicationDestinationPrefixes("/client");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/register")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}

class SimpleMessage {

    private String content;

    public SimpleMessage() {
    }

    public SimpleMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = name;
    }
}

@Controller
class GreetingController {

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage message) throws Exception {
        Thread.sleep(1000);
        return new SimpleMessage("Hello, " + message.getContent() + "!");
    }
}
