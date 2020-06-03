package zoomapi.handlers;

import zoomapi.models.Member;
import zoomapi.models.Result;

public interface INewMemberHandler extends IEventHandler {
    Result handle(Member member);
}
