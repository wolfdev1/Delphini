package wolfdev1.com.github.Delphini.Events.Twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageLogger {

    public MessageLogger(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event) {
        if(event.getMessageEvent().getUser().getName().equals("delphiniapp")) {
            return;
        }else{
            System.out.printf(
                    DateTimeFormatter.ofPattern("HH:mm:ss:SSS ").format(LocalDateTime.now()) + "MessageLogger <%s> %s%n",
                    event.getUser().getName(),
                    event.getMessage()
            );
        }
    }
}
