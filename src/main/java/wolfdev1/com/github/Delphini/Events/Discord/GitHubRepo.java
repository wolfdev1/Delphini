    package wolfdev1.com.github.Delphini.Events.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.HttpException;
import wolfdev1.com.github.Delphini.Config;
import wolfdev1.com.github.Delphini.DelphiniApp;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class GitHubRepo extends ListenerAdapter
{

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split(" ");


                    try {

                        if(args[0].equalsIgnoreCase(Config.BOT_PREFIX + "github-repo") || args[0].equalsIgnoreCase(Config.BOT_PREFIX + "githubrepo"))
                        {

                            if(!event.getChannel().getId().equals("940697475088547870"))
                            {
                                event.getMessage().reply("Sorry, but you only can execute this at <#940697475088547870>").queue();
                            }
                            else {
                                if (args.length < 3) {
                                    EmbedBuilder eb =
                                            new EmbedBuilder()
                                                    .setAuthor(event.getJDA().getSelfUser().getName(),
                                                            null,
                                                            event.getJDA().getSelfUser().getAvatarUrl())
                                                    .setTitle("Error")
                                                    .setDescription("Usage: " + Config.BOT_PREFIX + "`github-repo user repo-name`\n" +
                                                            "GitHub Repository")
                                                    .setFooter("Github API")
                                                    .setTimestamp(Instant.now())
                                                    .setColor(Color.decode("#ff4242"));

                                    event.getMessage().replyEmbeds(eb.build()).queue();
                                    }
                                    else
                                    {
                                        GitHub github = DelphiniApp.github;
                                        GHUser user = github.getUser(args[1]);

                                        if(user.getName() == null)
                                        {
                                            event.getMessage().reply("Sorry but **" + args[1] + "** is not a valid user").queue();
                                        }
                                        else
                                        {
                                            if(user.getRepository(args[2]).getName() == null)
                                            {
                                                event.getMessage().reply("**" + args[1] + "/"+args[2] + "** is not a valid repository try again").queue();
                                            }
                                            else
                                            {
                                                try{
                                                    GHRepository repo = user.getRepository(args[2]);
                                                    EmbedBuilder repoEb = new EmbedBuilder()
                                                            .setAuthor(user.getName(), null, user.getAvatarUrl())
                                                            .setTitle(repo.getName())
                                                            .setDescription(repo.getDescription())
                                                            .addField("Collaborators", String.valueOf(repo.getCollaboratorNames()), true)
                                                            .addField("Forks", (repo.getForksCount() < 1 ?
                                                                    "*This repo has no forks*" : String.valueOf(repo.getForksCount())), true)
                                                            .addField("Default Branch", repo.getDefaultBranch(), false)
                                                            .addField("Open Issues", (repo.getOpenIssueCount() < 1 ? "*This repo has no Open Issues*" :
                                                                    String.valueOf(repo.getOpenIssueCount())), true)
                                                            .addField("Is Fork", repo.isFork() ? "*True*" : "*False*", true)
                                                            .setColor(Objects.requireNonNull(event.getMember()).getColor())
                                                            .addField("Main Language",
                                                                    (repo.getLanguage() == null ? "*This repo has no languages*" : repo.getLanguage()) , false)
                                                            ;

                                                    event.getMessage().replyEmbeds(repoEb.build()).queue();
                                                } catch (HttpException e) {
                                                    GHRepository repo = user.getRepository(args[2]);
                                                    EmbedBuilder ebNoCollaborators = new EmbedBuilder()
                                                    .setAuthor(user.getName(), null, user.getAvatarUrl())
                                                            .setTitle(repo.getName())
                                                            .setDescription(repo.getDescription())
                                                            .addField("Collaborators","No access to collaborators", true)
                                                            .addField("Forks", (repo.getForksCount() < 1 ?
                                                                    "*This repo has no forks*" : String.valueOf(repo.getForksCount())), true)
                                                            .addField("Default Branch", repo.getDefaultBranch(), false)
                                                            .addField("Open Issues", (repo.getOpenIssueCount() < 1 ? "*This repo has no Open Issues*" :
                                                                    String.valueOf(repo.getOpenIssueCount())), true)
                                                            .addField("Is Fork", repo.isFork() ? "*True*" : "*False*", true)
                                                            .setColor(Objects.requireNonNull(event.getMember()).getColor())
                                                            .addField("Main Language",
                                                                    (repo.getLanguage() == null ? "*This repo has no languages*" : repo.getLanguage()) , false);
                                                    event.getMessage().replyEmbeds(ebNoCollaborators.build()).queue();
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                    } catch (IOException e){
                            e.printStackTrace();
                }
            }

    }

