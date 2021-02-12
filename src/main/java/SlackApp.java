import com.slack.api.Slack;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.model.event.AppMentionEvent;
import com.slack.api.model.event.MessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlackApp {
    static {

        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    public static void main(String[] args) throws Exception {
        String botToken = "xoxb-1739437941058-1736552516213-Ix71mNEc8jxZtcQm3GX7L8Xl";
        String appToken = "xapp-1-A01N47UDJ01-1732833890679-80d777448b69818a6152eeb691dc49e56ac4f6619ba52c6838d70c6ae862d5e4";

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());
        app.event(AppMentionEvent.class, (req, ctx) -> {
            ctx.say("Mention event Jason!");
            return ctx.ack();
        });

        app.event(MessageEvent.class, (req, ctx) -> {
            ctx.say("Hello Jason");
            return ctx.ack();
        });


        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}