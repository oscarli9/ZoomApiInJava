package zoomapi.models;

import zoomapi.Downloader.ApiEvent;
import zoomapi.handlers.IEventHandler;

public class TaskObject {
    private ApiEvent eventType;
    private String identity;
    private IEventHandler eventHandler;

    public TaskObject(ApiEvent eventType, String identity, IEventHandler eventHandler) {
        this.eventType = eventType;
        this.identity = identity;
        this.eventHandler = eventHandler;
    }

    public void setEventType(ApiEvent eventType) {
        this.eventType = eventType;
    }

    public ApiEvent getEventType() {
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
