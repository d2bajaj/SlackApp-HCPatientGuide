package EventHandlers;

import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import com.slack.api.methods.SlackApiException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MedicinesSlashCommandHandler implements SlashCommandHandler {

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext slashCommandContext)
            throws IOException, SlackApiException {

        String name = slashCommandRequest.getPayload().getText();

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10000);
                slashCommandContext.respond("Responding to " + name);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        });

        return slashCommandContext.ack(String.format("Looking up medicines for %s", name));
    }
}