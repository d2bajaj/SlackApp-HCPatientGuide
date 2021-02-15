package EventHandlers;

import Config.SlackConfiguration;
import SalesforceData.ISalesforceData;
import SalesforceData.SalesforceFakeData;
import com.slack.api.Slack;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.model.block.ContextBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ActionCommandUserSelectedMedicinesHandler implements BlockActionHandler {
    private ISalesforceData salesforceData;
    private static SlackConfiguration configuration = new SlackConfiguration();

    public ActionCommandUserSelectedMedicinesHandler() {
        salesforceData = new SalesforceFakeData();
    }

    @Override
    public Response apply(BlockActionRequest blockActionRequest, ActionContext actionContext) throws IOException, SlackApiException {

        CompletableFuture.runAsync(() -> {
            try {
                replyWithMedicines(blockActionRequest, actionContext);
            } catch (Exception e) {
                try {
                    actionContext.respond("Could not get medicines.");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        });

        return actionContext.ack();
    }

    private void replyWithMedicines(BlockActionRequest userSelectedRequest, ActionContext slashCommandContext) throws IOException, SlackApiException {
        BlockActionPayload payload = userSelectedRequest.getPayload();

        if (payload == null || payload.getActions() == null || payload.getActions().size() < 1) {
            slashCommandContext.respond("Invalid user selection.");
            return;
        }

        String userId = payload.getActions().get(0).getSelectedUser();

        UsersInfoResponse userInfo = Slack.getInstance().methods().usersInfo(UsersInfoRequest.builder().token(configuration.getBotToken()).user(userId).build());
        String patientName = userInfo.getUser().getRealName();

        List<String> medicines = salesforceData.getMedicines(userId);

        if (medicines == null || medicines.isEmpty()) {
            slashCommandContext.respond(String.format("No medicines in record for %s", patientName));
            return;
        }

        final StringBuilder medicineListText = new StringBuilder();
        medicineListText.append(String.format("Here's the list of medicines for %s:", patientName));

        medicines.forEach((med) -> {
            medicineListText.append(String.format("\n - *%s*", med));
        });

        List<LayoutBlock> response = new ArrayList<>();
        response.add(
                ContextBlock
                        .builder()
                        .elements(List.of(MarkdownTextObject
                                .builder()
                                .text(medicineListText.toString())
                                .build()))
                        .build());

        slashCommandContext.respond(response);
    }
}
