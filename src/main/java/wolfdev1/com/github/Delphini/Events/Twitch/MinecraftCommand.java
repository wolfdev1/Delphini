package wolfdev1.com.github.Delphini.Events.Twitch;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import wolfdev1.com.github.Delphini.main.Config;

public class MinecraftCommand {
    public MinecraftCommand(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelMessageEvent.class, this::onChannelMessage);
    }
    public void onChannelMessage(ChannelMessageEvent event)
    {
        String[] args = event.getMessage().split(" ");

        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "minecraft")) {
            if(args.length > 1) {
                event.reply(event.getTwitchChat(), "So much arguments provided, try again please");
            }else{
                event.reply(event.getTwitchChat(), "Minecraft Server info: Server Ip: ip.deunserver.xd, Online Players <Here bukkit method>, " +
                        "Default Gamemode <Here Bukkit default gamemode method>, Server Owner: <Here bukkit get owner method>");
            }
        }
    }
}
