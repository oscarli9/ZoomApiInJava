package zoomapi.handlers;

import zoomapi.Member;
import zoomapi.Result;

public interface INewMemberHandler extends IEventHandler {
    Result handle(Member member);
}
