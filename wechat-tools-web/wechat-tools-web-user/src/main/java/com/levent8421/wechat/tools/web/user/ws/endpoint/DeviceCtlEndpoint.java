package com.levent8421.wechat.tools.web.user.ws.endpoint;

import com.alibaba.fastjson.JSON;
import com.levent8421.wechat.tools.commons.entity.SuperCtlAction;
import com.levent8421.wechat.tools.commons.entity.SuperCtlDevice;
import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.model.service.app.sc.define.MotorStates;
import com.levent8421.wechat.tools.model.service.app.sc.define.SuperCtlDeviceStatus;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import com.levent8421.wechat.tools.model.service.general.SuperCtlDeviceService;
import com.levent8421.wechat.tools.model.service.general.listener.SuperCtlActionCompleteListener;
import com.levent8421.wechat.tools.web.commons.util.ParamChecker;
import com.levent8421.wechat.tools.web.user.serurity.UserToken;
import com.levent8421.wechat.tools.web.user.serurity.UserTokenVerifier;
import com.levent8421.wechat.tools.web.user.vo.MotorCtlParam;
import com.levent8421.wechat.tools.web.user.ws.WebSocketSpringConfigurator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * Create by Levent8421
 * Date: 2021/11/27 16:56
 * ClassName: DeviceCtlEndpoint
 * Description:
 * Device Control Websocket Endpoint
 * ws://localhost:9011/api/token/ws/device-ctrl
 *
 * @author levent8421
 */
@Slf4j
@Component
@ServerEndpoint(value = "/api/ws/device-ctl", configurator = WebSocketSpringConfigurator.class)
public class DeviceCtlEndpoint extends AbstractWebsocketEndpoint implements SuperCtlActionCompleteListener {
    private final SuperCtlActionService superCtlActionService;
    private final SuperCtlDeviceService superCtlDeviceService;
    private final SessionManager sessionManager = new SessionManager();

    public DeviceCtlEndpoint(SuperCtlActionService superCtlActionService,
                             SuperCtlDeviceService superCtlDeviceService,
                             UserTokenVerifier tokenVerifier) {
        super(tokenVerifier);
        this.superCtlActionService = superCtlActionService;
        this.superCtlDeviceService = superCtlDeviceService;
        superCtlActionService.setActionCompleteListener(this);
    }

    @Override
    public void onSocketOpen(Session session, Map<String, Object> tokenData) {
        String sid = session.getId();
        Integer uid = (Integer) tokenData.get(UserToken.USER_ID_NAME);
        log.debug("Session[{}] open,uid=[{}]", sid, uid);
        sessionManager.register(uid, session);
    }

    @Override
    public void onSocketClose(Session session) {
        String sid = session.getId();
        log.debug("Session close[{}]", sid);
        sessionManager.destroy(sid);
    }

    @Override
    public void onSocketError(Session session, Throwable error) {
        String sid = session.getId();
        log.warn("Session error[{}]", sid, error);
        sessionManager.destroy(sid);
    }

    @Override
    public void onSocketMessage(Session session, String message) {
        String sid = session.getId();
        log.debug("Session message[{}]:[{}]", sid, message);
        try {
            resolveMessage(session, message);
        } catch (Exception e) {
            log.warn("Error on resolve message", e);
        }
    }

    private void resolveMessage(Session session, String message) {
        MotorCtlParam param = JSON.parseObject(message, MotorCtlParam.class);
        Class<BadRequestException> e = BadRequestException.class;
        ParamChecker.notNull(param, e, "empty param");
        ParamChecker.notNull(param.getDeviceId(), e, "deviceId is required!");
        ParamChecker.notEmpty(param.getStatus(), e, "status is required!");
        for (MotorCtlParam.State state : param.getStatus()) {
            ParamChecker.notNull(state.getMotor(), e, "status[$i].motor is required!");
            ParamChecker.notEmpty(state.getState(), e, "status[$i].state is required!");
            if (!MotorStates.isStateInRule(state.getState())) {
                throw new BadRequestException("Invalidate state:" + state.getState());
            }
        }

        SuperCtlDevice device = superCtlDeviceService.require(param.getDeviceId());
        SuperCtlDeviceStatus targetStatus = JSON.parseObject(device.getStatusJson(), SuperCtlDeviceStatus.class);
        for (MotorCtlParam.State state : param.getStatus()) {
            switch (state.getMotor()) {
                case SuperCtlDeviceStatus.MOTOR1:
                    targetStatus.setMotor1(state.getState());
                    break;
                case SuperCtlDeviceStatus.MOTOR2:
                    targetStatus.setMotor2(state.getState());
                    break;
                default:
                    throw new BadRequestException("Invalidate motor:" + state.getMotor());
            }
        }
        log.info("Operate device[{}] to target[{}]", device.getId(), targetStatus);
        SuperCtlAction action = superCtlActionService.sendAction(device, targetStatus);
        log.info("Send action:[{}]", action.getId());
    }

    @Override
    public void onComplete(SuperCtlAction action) {
        SuperCtlDevice device = superCtlDeviceService.require(action.getDeviceId());
        Integer uid = device.getWechatUserId();
        Session session = sessionManager.find(uid);
        if (session == null) {
            log.warn("Can not find subscribe session for uid:[{}]", uid);
            return;
        }
        action.setDevice(device);
        String resp = JSON.toJSONString(action);
        try {
            session.getBasicRemote().sendText(resp);
        } catch (IOException e) {
            log.error("Error on sensor complete message to session[{}]", session.getId(), e);
        }
    }
}
