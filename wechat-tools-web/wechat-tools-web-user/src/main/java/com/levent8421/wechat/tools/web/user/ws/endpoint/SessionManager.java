package com.levent8421.wechat.tools.web.user.ws.endpoint;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/28 15:35
 * ClassName: SessionManager
 * Description:
 * 会话控制
 *
 * @author levent8421
 */
@Slf4j
public class SessionManager {
    /**
     * 提供用户ID->SessionId的索引方式
     */
    private final BiMap<Integer, String> userSessionIdTable = HashBiMap.create();
    /**
     * 提供SessionId->Session的索引方式
     */
    private final Map<String, Session> sessionTable = Maps.newHashMap();

    public synchronized void register(Integer uid, Session session) {
        String existsSid = userSessionIdTable.get(uid);
        if (existsSid != null) {
            // 存在已连接的session
            destroy(existsSid);
        }
        String sid = session.getId();
        userSessionIdTable.put(uid, sid);
        sessionTable.put(sid, session);
    }

    public void destroy(String sid) {
        Integer uid = userSessionIdTable.inverse().get(sid);
        userSessionIdTable.remove(uid);
        Session session = sessionTable.get(sid);
        if (session != null) {
            log.warn("Force close session[{}]", sid);
            try {
                session.close();
            } catch (IOException e) {
                log.warn("Error on close session:[{}]", sid, e);
            }
        }
    }

    public Session find(Integer uid) {
        String sid = userSessionIdTable.get(uid);
        if (sid == null) {
            return null;
        }
        return find(sid);
    }

    public Session find(String sid) {
        return sessionTable.get(sid);
    }
}
