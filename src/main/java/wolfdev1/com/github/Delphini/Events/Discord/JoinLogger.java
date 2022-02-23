package wolfdev1.com.github.Delphini.Events.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class JoinLogger extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {



        EmbedBuilder joinEmbed = new EmbedBuilder();
                                joinEmbed
                                        .setColor(Color.decode("#a8c5ff"))
                                        .setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl())
                                        .setTitle("Member joined")
                                        .addField("User ID", event.getMember().getId(), true)
                                        .addField("User Tag", event.getMember().getUser().getAsTag(), true)
                                        .addField("Member", event.getMember().getAsMention(), false)
                                        .setFooter("Delphini")
                                        .setTimestamp(Instant.now())
        ;
        Objects.requireNonNull
                        (event.getGuild().getTextChannelById("946117650675806238"))
                            .sendMessageEmbeds(joinEmbed.build())
                                .queue();


    }
}
