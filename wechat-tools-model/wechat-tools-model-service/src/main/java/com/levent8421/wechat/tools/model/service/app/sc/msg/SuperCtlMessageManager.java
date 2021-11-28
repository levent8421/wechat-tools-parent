package com.levent8421.wechat.tools.model.service.app.sc.msg;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.levent8421.wechat.tools.commons.utils.ThreadUtils;
import com.levent8421.wechat.tools.model.service.app.sc.vo.SuperCtlMessage;
import com.levent8421.wechat.tools.model.service.general.SuperCtlActionService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Create by Levent8421
 * Date: 2021/11/26 10:37
 * ClassName: SuperCtlMessageManager
 * Description:
 * SuperCtl Message Manager
 *
 * @author levent8421
 */
@Slf4j
public class SuperCtlMessageManager implements Runnable {
    private static final int CHECK_INTERVAL = 1000;
    private static final int TIMEOUT = 10 * 1000;

    @Data
    private static class MessageInfo {
        private final long start;
        private SuperCtlMessage<?> msg;
    }

    private final Map<Integer, MessageInfo> messageTable = Maps.newHashMap();
    private final ExecutorService threadPool = ThreadUtils.createSingleThreadPool("SuperCtl-MSG");
    private boolean running = false;
    private final SuperCtlActionService superCtlActionService;

    public SuperCtlMessageManager(SuperCtlActionService superCtlActionService) {
        this.superCtlActionService = superCtlActionService;
    }

    public void register(SuperCtlMessage<?> msg) {
        int id = msg.getId();
        long start = System.currentTimeMillis();
        MessageInfo info = new MessageInfo(start);
        info.setMsg(msg);
        synchronized (messageTable) {
            messageTable.put(id, info);
        }
    }

    public void remove(Integer id) {
        synchronized (messageTable) {
            this.messageTable.remove(id);
        }
    }

    public void startCheckTask() {
        if (running) {
            return;
        }
        running = true;
        threadPool.execute(this);
    }

    public void stopCheckTask() {
        running = false;
        ThreadUtils.shutdownExecutorService(threadPool, CHECK_INTERVAL * 2);
    }

    @Override
    public void run() {
        while (running) {
            delay();
            try {
                checkTimeout();
            } catch (Exception e) {
                log.error("Error on DeviceMessage Checker", e);
            }
        }
    }

    private void checkTimeout() {
        Collection<MessageInfo> messages;
        synchronized (messageTable) {
            messages = Lists.newArrayList(messageTable.values());
        }
        long now = System.currentTimeMillis();
        List<Integer> timeoutIds = Lists.newArrayList();
        for (MessageInfo info : messages) {
            long start = info.getStart();
            long awaitTime = now - start;
            if (awaitTime > TIMEOUT) {
                SuperCtlMessage<?> msg = info.getMsg();
                superCtlActionService.notifyActionTimeout(msg.getId(), awaitTime);
                timeoutIds.add(msg.getId());
            }
        }
        synchronized (messageTable) {
            for (Integer id : timeoutIds) {
                messageTable.remove(id);
            }
        }
    }

    private void delay() {
        ThreadUtils.trySleep(CHECK_INTERVAL);
    }
}
