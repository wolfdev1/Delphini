package wolfdev1.com.github.Delphini;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import wolfdev1.com.github.Delphini.Events.Discord.GitHubCmd;
import wolfdev1.com.github.Delphini.Events.Discord.GitHubRepo;
import wolfdev1.com.github.Delphini.Events.Discord.SendMessageOnStreamStart;
import wolfdev1.com.github.Delphini.Events.Twitch.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DelphiniApp {
    public static JDA jda;
    public static GitHub github;

    static {
        try {
            github = new GitHubBuilder().withOAuthToken(Secrets.GITHUB_TOKEN).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws LoginException {
        try{

            OAuth2Credential credential = new OAuth2Credential("twitch", Secrets.TWITCH_BOT_TOKEN);

            TwitchClient twitchClient = TwitchClientBuilder.builder()
                    .withEnableChat(true)
                    .withDefaultAuthToken(credential)
                    .withChatAccount(credential)
                    .withEnableHelix(true)
                    .build();

            twitchClient.getClientHelper().enableStreamEventListener("wolf_mc19");
            twitchClient.getChat().joinChannel("wolf_mc19");
            SimpleEventHandler eventHandler = twitchClient.getEventManager().getEventHandler(SimpleEventHandler.class);

            new Message(eventHandler);
            new Help(eventHandler);
            new Minecraft(eventHandler);
            new SendMessageOnStreamStart(eventHandler);
            new Timeout(eventHandler);
            new Say(eventHandler);

            jda = JDABuilder.createDefault(Secrets.DISCORD_BOT_TOKEN)
                    .setActivity((Activity.playing("Lunar Client")))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .addEventListeners(new GitHubRepo())
                    .addEventListeners(new GitHubCmd())
                    .setChunkingFilter(ChunkingFilter.ALL )

                    .build()
                    ;

            jda.upsertCommand("example", "This was only an example")
                    .addOption(OptionType.ATTACHMENT, "files", "Upload your files here!", true).queue();
            jda.updateCommands().queue();
        }catch (Exception e) {
            System.out.println(
                    DateTimeFormatter.ofPattern("HH:mm:ss:SS").format(LocalDateTime.now())
                            + " [ErrorCatch] The API tried to login without success, make sure you have your Oauth2 token, error code: 6 Login Error"
            );
            System.exit
                    (0);
        }
    }
}
