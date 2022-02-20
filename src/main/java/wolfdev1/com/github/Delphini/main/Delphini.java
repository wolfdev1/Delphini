package wolfdev1.com.github.Delphini.main;

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.philippheuer.events4j.simple.SimpleEventHandler;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import wolfdev1.com.github.Delphini.Events.Discord.SendMessageOnStreamStart;
import wolfdev1.com.github.Delphini.Events.Twitch.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Delphini {
    public static JDA jda;
    public static void main(String[] args) {
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

            new MessageLogger(eventHandler);
            new HelpCommand(eventHandler);
            new MinecraftCommand(eventHandler);
            new SendMessageOnStreamStart(eventHandler);
            new TimeoutCommand(eventHandler);
            new SayCommand(eventHandler);

            jda = JDABuilder.createDefault(Secrets.DISCORD_BOT_TOKEN)
                    .setActivity(Activity.streaming("Just Chatting", "https://twitch.tv/" + Config.CHANNEL))
                    .enableIntents(GatewayIntent.GUILD_MEMBERS)
                    .setChunkingFilter(ChunkingFilter.ALL )
                    .build()
                    ;
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
