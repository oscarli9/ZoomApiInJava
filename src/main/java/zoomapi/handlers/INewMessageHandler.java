package zoomapi.handlers;

import zoomapi.models.Message;
import zoomapi.models.Result;

public interface INewMessageHandler extends IEventHandler {
    Result handle(Message message);
}
