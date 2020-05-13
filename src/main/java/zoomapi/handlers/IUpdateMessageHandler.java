package zoomapi.handlers;

import zoomapi.Message;
import zoomapi.Result;

public interface IUpdateMessageHandler extends IEventHandler {
    Result handle(Message message);
}
