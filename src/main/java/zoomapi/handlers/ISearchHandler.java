package zoomapi.handlers;

import zoomapi.models.Result;
import zoomapi.components.ChatMessages;

public interface ISearchHandler {
    Result search(ChatMessages chatMessages, String channelId, String fromDate, String toDate, String condition);
}
