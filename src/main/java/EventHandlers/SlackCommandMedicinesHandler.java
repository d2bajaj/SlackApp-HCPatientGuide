package EventHandlers;

import SalesforceData.ISalesforceData;
import SalesforceData.SalesforceFakeData;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.PlainTextObject;
import com.slack.api.model.block.element.UsersSelectElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SlackCommandMedicinesHandler implements SlashCommandHandler  {

    private String actionIdUserPicker;

    public SlackCommandMedicinesHandler(String actionIdUserSelectedMedicine) {
        actionIdUserPicker = actionIdUserSelectedMedicine;
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext)
            throws IOException, SlackApiException {

        String commandParameter = slashCommandRequest.getPayload().getText();

        CompletableFuture.runAsync(() -> {
            try {
                replyWithUserPicker(slashCommandRequest, slashCommandContext);
            } catch (IOException e) {
                try {
                    slashCommandContext.respond("Could not get medicines for " + commandParameter);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        });

        return slashCommandContext.ack(replyWithUserPicker(slashCommandRequest, slashCommandContext));
    }

    private List<LayoutBlock> replyWithUserPicker(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext) throws IOException {
        List<LayoutBlock> response = new ArrayList<>();
        response.add(
                SectionBlock
                        .builder()
                        .text(PlainTextObject
                                .builder()
                                .text("Please pick a patient for medicines")
                                .build())
                        .accessory(UsersSelectElement.builder().actionId(actionIdUserPicker).build())
                        .build());

        return response;
    }
}