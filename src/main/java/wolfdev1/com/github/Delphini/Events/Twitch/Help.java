package wolfdev1.com.github.Delphini.Events.Twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.common.enums.CommandPermission;
import wolfdev1.com.github.Delphini.main.Config;

public class Help {
    public Help(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }
    public void onChannelMessage(ChannelMessageEvent event) {
        String[] args = event.getMessage().split(" ");

        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "help")) {
            if(!event.getPermissions().contains(CommandPermission.MODERATOR)) {
                event.reply(event.getTwitchChat(), "Sorry, but only moderators can perform this action :(");
            }else{
                if(args.length < 2) {
                    event.reply(event.getTwitchChat(), """
                            Bot feature list:\s
                            Viewer Counter
                            Moderation Commands (Soon)
                            Minecraft server info
                            And so much more...\s""");
                    //Mierda filtre mi token (de nuevo ._.)
                }else{
                    if(args[1].equalsIgnoreCase("Viewer Counter") || args[1].equalsIgnoreCase("viewer_counter")) {
                        event.reply(event.getTwitchChat(), "Viewer Counter info\n" +
                                "Syntax: " + Config.BOT_PREFIX + "viewer-counter or " + Config.BOT_PREFIX + "counter\n" +
                                "Details:\n" +
                                "Only a viewer counter .-.");
                    }else{
                        if(args[1].equalsIgnoreCase("Moderation") || args[1].equalsIgnoreCase("moderation_commands")) {
                            event.reply(event.getTwitchChat(), "Moderation Commands info\n" +
                                    "Commands:\n" +
                                    "Kick\n" +
                                    "Ban or Punish \n" +
                                    "Timeout\n" +
                                    "Say or Say-Message\n\n" +
                                    "Syntax: " + Config.BOT_PREFIX + "<command> <reason (in say is message but XD)>");
                        }else{
                            if(args[1].equalsIgnoreCase("minecraft-server")
                                    || args[1].equalsIgnoreCase("minecraft server") || args[1].equalsIgnoreCase("minecraft server info")
                                    || args[1].equalsIgnoreCase("minecraft-server-info"))
                            {
                                event.reply(event.getTwitchChat(), "Minecraft Server info\n" +
                                        "Syntax: " + Config.BOT_PREFIX + "minecraft\n" +
                                        "Details: \n" +
                                        "Sends you the minecraft java server info");
                            }else{
                                event.reply(event.getTwitchChat(), args[1] + " is not an feature or command, try again 🐈");
                            }
                        }
                    }
                }
            }
        }
    }
}
