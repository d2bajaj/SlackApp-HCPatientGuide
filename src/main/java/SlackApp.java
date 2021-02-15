import Config.SlackConfiguration;
import EventHandlers.SlackCommandMedicinesHandler;
import EventHandlers.ActionCommandUserSelectedMedicinesHandler;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;

public class SlackApp {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    private static SlackConfiguration configuration = new SlackConfiguration();
    private static String medicineSlashCommand = "/medicines";
    private static String actionIdMedicinesUserSelected = "user_select_medicines_action";

    public static void main(String[] args) throws Exception {

        String botToken = configuration.getBotToken();
        String appToken = configuration.getAppToken();

        App app = new App(AppConfig.builder().singleTeamBotToken(botToken).build());

        app.command(medicineSlashCommand, new SlackCommandMedicinesHandler(actionIdMedicinesUserSelected));
        app.blockAction(actionIdMedicinesUserSelected, new ActionCommandUserSelectedMedicinesHandler());

        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.start();
    }
}