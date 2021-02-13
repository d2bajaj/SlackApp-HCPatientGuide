package EventHandlers;

import SalesforceData.ISalesforceData;
import SalesforceData.SalesforceFakeData;
import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MedicinesSlashCommandHandler implements SlashCommandHandler {

    private ISalesforceData salesforceData;

    public MedicinesSlashCommandHandler() {
        salesforceData = new SalesforceFakeData();
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext)
            throws IOException, SlackApiException {

        String commandParameter = slashCommandRequest.getPayload().getText();

        CompletableFuture.runAsync(() -> {
            try {
                replyWithMedicines(slashCommandRequest, slashCommandContext);
            } catch (IOException e) {
                try {
                    slashCommandContext.respond("Could not get medicines for " + commandParameter);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                e.printStackTrace();
            }
        });

        return slashCommandContext.ack(String.format("Looking up medicines for %s", commandParameter));
    }

    private void replyWithMedicines(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext) throws IOException {
        String patientName = slashCommandRequest.getPayload().getText();
        List<String> patients = salesforceData.getPatients(patientName);

        if (patients == null || patients.size() == 0) {
            slashCommandContext.respond("No patients found with name " + patientName);
        } else if (patients.size() == 1) {
            // Get meds for patient
            String patientId = patients.get(0);
            SlashCommandResponse.SlashCommandResponseBuilder builder = SlashCommandResponse.builder();
            builder.text("List of medicines");
            builder.blocks()


            slashCommandContext.respond(String.join(" and ", salesforceData.getMedicines(patientId)));
        } else {
            slashCommandContext.respond("Found multiple matches: " + String.join(" and ", patients));
        }
    }
}