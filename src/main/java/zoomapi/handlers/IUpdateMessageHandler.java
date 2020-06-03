package zoomapi.handlers;

import zoomapi.models.Message;
import zoomapi.models.Result;

public interface IUpdateMessageHandler extends IEventHandler {
    Result handle(Message message);
}
