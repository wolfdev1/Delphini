package wolfdev1.com.github.Delphini.Events.Twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import wolfdev1.com.github.Delphini.main.Config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Timeout {
    public Timeout(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }

    public void onChannelMessage(ChannelMessageEvent event)
    {
        String[] args = event.getMessage().split(" ");
        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "timeout")) {
            if(!event.getPermissions().contains(CommandPermission.MODERATOR)) {
                event.reply(event.getTwitchChat(), "Oops! It seems that you dont have sufficient permissions to perform this action");
            }else{
                if(args.length < 2) {
                    event.reply(event.getTwitchChat(), "Sorry, you need to mention an user or paste their id, usage: " +
                            Config.BOT_PREFIX + "timeout @wolf_mc19 Spam :(");
                }else{
                    if(args[1].contains(event.getUser().getId())) {
                        event.reply(event.getTwitchChat(), "WTF BRO, why you are trying to timeout yourself .-.");
                    }else{
                        try{
                            StringBuilder sb = new StringBuilder();
                            for(int i = 2; i < args.length; i++)
                            {
                                sb.append(args[i]).append(" ");
                            }
                            event.timeout(args[1], null, (args.length < 3 ? null : sb.toString()) );
                        }catch (NullPointerException e) {
                            System.out.println(
                                    DateTimeFormatter.ofPattern
                                            ("HH:mm:ss:SS").format(LocalDateTime.now()) + "The bot attempt to timeout '\"" +
                                            " + args[1] + \"' unsuccessfully, unknown error #0, error code: 67\""
                            );
                            event.reply(event.getTwitchChat()
                                    , "The bot attempt to timeout '" + args[1] +
                                            "' unsuccessfully, unknown error #0, error code: 67" );
                        }

                    }
                }
            }
        }
    }
}
