package com.levent8421.wechat.tools.web.user.ws.endpoint;

import com.levent8421.wechat.tools.commons.utils.CollectionUtils;
import com.levent8421.wechat.tools.web.commons.security.TokenException;
import com.levent8421.wechat.tools.web.commons.security.TokenVerifier;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.websocket.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/27 15:58
 * ClassName: AbstractWebsocketEndpoint
 * Description:
 * Abstract WebSocket Endpoint
 *
 * @author levent8421
 */
@Slf4j
public abstract class AbstractWebsocketEndpoint {
    public static final String TOKEN_NAME = "token";
    private final TokenVerifier tokenVerifier;

    protected AbstractWebsocketEndpoint(TokenVerifier tokenVerifier) {
        this.tokenVerifier = tokenVerifier;
    }

    /**
     * On Websocket Open
     *
     * @param tokenData tokenData
     * @param session   session
     */
    public abstract void onSocketOpen(Session session, Map<String, Object> tokenData);

    @OnOpen
    public void onSocketOpenHandler(Session session) {
        List<String> tokens = session.getRequestParameterMap().get(TOKEN_NAME);
        if (CollectionUtils.isEmpry(tokens)) {
            log.warn("Session permission denied![{}]", session.getId());
            forceCloseSession(session);
            return;
        }
        String token = tokens.get(0);
        if (StringUtils.isBlank(token)) {
            log.warn("WebSocket request with a Empty token:[{}]", token);
            forceCloseSession(session);
            return;
        }
        Map<String, Object> tokenData;
        try {
            tokenData = tokenVerifier.verifyAndDecode(token);
        } catch (TokenException e) {
            log.warn("WebSocket request with a bad token", e);
            forceCloseSession(session);
            return;
        }
        onSocketOpen(session, tokenData);
    }

    private void forceCloseSession(Session session) {
        try {
            session.close();
        } catch (IOException e) {
            log.error("Error on force close session[{}]", session.getId(), e);
        }
    }

    /**
     * On websocket close
     *
     * @param session session
     */
    public abstract void onSocketClose(Session session);

    @OnClose
    public void onSocketCloseHandler(Session session) {
        onSocketClose(session);
    }

    /**
     * On websocket error
     *
     * @param session session
     * @param error   error
     */
    public abstract void onSocketError(Session session, Throwable error);

    @OnError
    public void onSocketErrorHandler(Session session, Throwable error) {
        onSocketError(session, error);
    }

    /**
     * On Websocket message
     *
     * @param session session
     * @param message message
     */
    public abstract void onSocketMessage(Session session, String message);

    @OnMessage
    public void onSocketMessageHandler(Session session, String message) {
        onSocketMessage(session, message);
    }
}
