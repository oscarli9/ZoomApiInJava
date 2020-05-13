package zoomapi;

import zoomapi.handlers.IEventHandler;

import javax.annotation.Nonnull;

public class TaskObject {
    private String eventType;
    private String identity;
    private IEventHandler eventHandler;

    public TaskObject(String eventType, String identity, IEventHandler eventHandler) {
        this.eventType = eventType;
        this.identity = identity;
        this.eventHandler = eventHandler;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIdentity() {
        return identity;
    }

    public void setEventHandler(IEventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    public IEventHandler getEventHandler() {
        return eventHandler;
    }
}
