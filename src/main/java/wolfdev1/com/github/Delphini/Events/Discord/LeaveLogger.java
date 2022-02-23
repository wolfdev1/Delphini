package wolfdev1.com.github.Delphini.Events.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class LeaveLogger extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(@NotNull GuildMemberRemoveEvent event) {

        EmbedBuilder leaveEmbed = new EmbedBuilder();

        leaveEmbed
                .setColor(Color.decode("#ff6e7a"))
                .setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl())
                .setTitle("Member left")
                .addField("User ID", event.getMember().getId(), true)
                .addField("User Tag", event.getMember().getUser().getAsTag(), true)
                .addField("User", event.getUser().getAsMention(), false)
                .setFooter("Delphini")
                .setTimestamp(Instant.now())
        ;
        Objects.requireNonNull
                        (event.getGuild().getTextChannelById("946117650675806238"))
                .sendMessageEmbeds(leaveEmbed.build())
                .queue();
    }
}
