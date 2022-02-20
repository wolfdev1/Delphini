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
                            (Activity.streaming(event.getStream().getGameName(), "https://twitch.tv/" + Config.CHANNEL));
            EmbedBuilder eb =
                    new EmbedBuilder()
                    .setAuthor(event.getChannel().getName(), "https://twitch.tv/" + (event.getChannel().getId()),
                            "http://assets.stickpng.com/images/580b57fcd9996e24bc43c540.png")
                    .setTitle(event.getChannel().getName() + " is now streaming **" + event.getStream().getGameName() + "**")
                            .setThumbnail(event.getStream().getThumbnailUrl(1000, 1000))
                    .setFooter("https://twitch.tv/" + (event.getChannel().getName()))
                            .setTimestamp(event.getStream().getStartedAtInstant())
                            .setFooter("Stream Notifier")
                    .setColor(Color.decode("#a759ff"))
                    ;
            Delphini.jda.getTextChannelById(Config.UPDATE_TEXT_CHANNEL_ID).sendMessageEmbeds(eb.build()).queue();
            System.out.println("The channel " + Config.CHANNEL + " is now streaming!");

    }
}
