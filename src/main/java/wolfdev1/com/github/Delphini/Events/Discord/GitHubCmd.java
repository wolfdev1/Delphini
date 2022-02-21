package wolfdev1.com.github.Delphini.Events.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import wolfdev1.com.github.Delphini.main.Config;
import wolfdev1.com.github.Delphini.main.Delphini;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class GitHubCmd extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {

        try {

        String[] args = event.getMessage().getContentRaw().split(" ");
        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "github"))
        {

                    if(!event.getChannel().getId().equals("940697475088547870"))
                    {
                        event.getMessage().reply("Sorry, but you only can execute this at <#940697475088547870>").queue();
                    }
                    else
                    {
                        GitHub github = Delphini.github;
                        if (args.length < 2)
                        {
                            EmbedBuilder error = new EmbedBuilder()
                                    .setAuthor(event.getJDA().getSelfUser().getName(), null, event.getJDA().getSelfUser().getAvatarUrl())
                                    .setTitle("Error")
                                    .setColor(Color.decode("#ff4242"))
                                    .setDescription("Usage:\n" +
                                            "`" + Config.BOT_PREFIX + "github wolfdev1`\n" +
                                            "`GitHub Command`")
                                    .setTimestamp(Instant.now())
                                    .setFooter("GitHub Help")
                                    ;
                            event.getMessage().replyEmbeds(error.build()).queue();
                        }
                        else
                        {
                            GHUser ghUser = github.getUser(args[1]);
                            if(ghUser.getName() == null)
                            {
                                event.getMessage().reply("There was no Github user found with the name **" + args[1] + "**").queue();
                            }
                            else
                            {
                                GHUser user = github.getUser(args[1]);

                                 EmbedBuilder embedBuilder =
                                         new EmbedBuilder()
                                        .setAuthor(user.getName(),
                                                null, user.getAvatarUrl())
                                        .setTitle(user.getName() + "'s profile.")
                                        .setDescription(user.getBio())
                                                 .setThumbnail(user.getAvatarUrl())
                                        .addField("Followers",
                                                (user.getFollowersCount() < 1 ?
                                                        "*This user has no followers*" : String.valueOf(user.getFollowersCount())) , true)
                                        .addField("Following",
                                                (user.getFollowingCount() < 1 ?
                                                        "*This user does not follow anyone*" : String.valueOf(user.getFollowingCount())), true)
                                        .addField("Repositories",
                                                (user.getRepositories().size() < 1 ?
                                                        "*This user has no repositories*" : String.valueOf(user.getRepositories().size())), false)
                                        .addField("Created at", String.valueOf
                                                (user.getCreatedAt().toInstant()), true)
                                        .addField("Twitter",
                                                (user.getTwitterUsername() == null
                                                        ? "*This user has not connected a twitter account.*" : user.getTwitterUsername()), true)
                                                 .addField("Organization(s)",
                                                         (user.getOrganizations().size() == 0 ? "*User is not in a organization*" : String.valueOf(user.getOrganizations().size())),
                                                         false)
                                                 .addField("", (user.getPublicGistCount() < 1 ?
                                                         "*User has no public gist*" : String.valueOf(user.getPublicGistCount())), false)
                                        .setTimestamp(Instant.now())
                                        .setFooter("Github")
                                        .setColor(Objects.requireNonNull
                                                (event.getMember()).getColor())
                                        ;
                                event.getMessage().replyEmbeds(embedBuilder.build()).queue();
                            }
                        }
                    }
                }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}

