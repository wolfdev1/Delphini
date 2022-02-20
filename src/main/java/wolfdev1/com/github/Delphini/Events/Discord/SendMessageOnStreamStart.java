package wolfdev1.com.github.Delphini.Events.Discord;

import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent;
import com.github.twitch4j.events.ChannelGoLiveEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import wolfdev1.com.github.Delphini.main.Config;
import wolfdev1.com.github.Delphini.main.Delphini;

import java.awt.*;

public class SendMessageOnStreamStart {
    public SendMessageOnStreamStart(SimpleEventHandler eventHandler) {
        eventHandler.onEvent(ChannelGoLiveEvent.class, this::onChannelGoLive);
    }
    public void onChannelGoLive(ChannelGoLiveEvent event) {
            Delphini.jda
                    .getPresence().setActivity
                            (Activity.streaming("Just Chatting", "https://twitch.tv/" + Config.CHANNEL));
            EmbedBuilder eb = new EmbedBuilder()
                    .setAuthor(event.getChannel().getName(), "https://twitch.tv/" + (event.getChannel().getId()),
                            "https://hipertextual.com/wp-content/uploads/2021/10/twitch.jpeg")
                    .setTitle(event.getChannel().getId() + " is now streaming **" + event.getStream().getGameName() + "**")
                    .setImage(event.getStream().getThumbnailUrl())
                    .setFooter("https://twitch.tv/" + (event.getChannel().getName()))
                    .setColor(Color.decode("#a759ff"))
                    ;
            Delphini.jda.getTextChannelById(Config.UPDATE_TEXT_CHANNEL_ID).sendMessageEmbeds(eb.build()).queue();
            System.out.println("The channel " + Config.CHANNEL + " is now streaming!");

    }
}
