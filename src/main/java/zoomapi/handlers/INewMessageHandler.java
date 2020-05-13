package zoomapi.handlers;

import zoomapi.Message;
import zoomapi.Result;

public interface INewMessageHandler extends IEventHandler {
    Result handle(Message message);
}
