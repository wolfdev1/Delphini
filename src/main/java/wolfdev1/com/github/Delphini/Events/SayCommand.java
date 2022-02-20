package wolfdev1.com.github.Delphini.Events;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import wolfdev1.com.github.Delphini.main.Config;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SayCommand {

    public SayCommand(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event) {
        String[] args = event.getMessage().split(" ");

        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "say"))
        if(!event.getPermissions().contains(CommandPermission.MODERATOR)) {
            event.reply(event.getTwitchChat(), "Sorry but only moderators can perform this action");
        }else{
            if(args.length < 2) {
                event.reply(event.getTwitchChat(), "You need to provide minimum one argument, syntax: $say Hello World :D");
            }else{
                StringBuilder sb = new StringBuilder();
                for(int i = 1; i < args.length; i++)
                {
                    sb.append(args[i]).append(" ");
                }
                event.getTwitchChat().sendMessage("wolf_mc19", sb.toString());
                System.out.println(DateTimeFormatter.ofPattern("HH:mm:ss:SSS ").format(LocalDateTime.now()) + " CommandExecution " +
                        event.getUser().getName() + "has been executed say successfully");
                event.getTwitchChat().delete("wolf_mc19", event.getMessageEvent().getMessageId().toString());

            }
        }
    }
}
