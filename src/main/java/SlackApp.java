import EventHandlers.MedicinesSlashCommandHandler;
import EventHandlers.UserSelectedMedicinesHandler;
import com.slack.api.RequestConfigurator;
import com.slack.api.Slack;
import com.slack.api.app_backend.slash_commands.SlashCommandResponseSender;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
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

    private static Configuration configuration = new Configuration();
    private static String medicineSlashCommand = "/medicines";
    private static String actionIdMedicinesUserSelected = "user_select_medicines_action";

    public static void main(String[] args) throws Exception {

        String botToken = configuration.getBotToken();
        String appToken = configuration.getAppToken();

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());

        app.command(medicineSlashCommand, new MedicinesSlashCommandHandler(actionIdMedicinesUserSelected));
        app.blockAction(actionIdMedicinesUserSelected, new UserSelectedMedicinesHandler());

        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}