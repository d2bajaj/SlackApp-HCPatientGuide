package SalesforceData;

import Config.SlackConfiguration;
import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.conversations.ConversationsOpenRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsOpenResponse;
import com.slack.api.model.block.LayoutBlock;

import java.io.IOException;
import java.util.List;

public class SlackConversations {
    private static SlackConfiguration configuration = new SlackConfiguration();

    public String createGroupConversation(List<String> userIds) throws IOException, SlackApiException {
        if (userIds == null || userIds.size() < 1) {
            return null;
        }

        ConversationsOpenResponse response = Slack.getInstance().methods()
                .conversationsOpen(ConversationsOpenRequest.builder()
                        .token(configuration.getBotToken())
                        .users(userIds)
                        .build());

        if (response.isOk()) {
            return response.getChannel().getId();
        }

        return null;
    }

    public void sendMessage(String conversationId, List<LayoutBlock> message) throws IOException, SlackApiException {
        ChatPostMessageResponse postMessageResponse = Slack.getInstance().methods()
                .chatPostMessage(ChatPostMessageRequest.builder()
                        .token(configuration.getBotToken())
                        .channel(conversationId)
                        .blocks(message)
                        .build());
    }
}
