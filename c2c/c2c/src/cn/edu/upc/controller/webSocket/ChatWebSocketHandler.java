package cn.edu.upc.controller.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import cn.edu.upc.tool.SaveSession;

import java.io.IOException;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final static List<WebSocketSession> sessions = Collections.synchronizedList(new ArrayList<WebSocketSession>());

    /**
     * 接收文本消息，并发送出去
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

        try {

            String m = message.getPayload();
            String[] wsk = m.split(",");
            String phone = wsk[0];
            long time = Long.parseLong(wsk[1]);
            String action = wsk[2];
            if (action.equals("start")){
                session.sendMessage(new TextMessage("success"));
                SaveSession.getInstance().save(phone,time);
                return;
            }
            boolean b = SaveSession.getInstance().isHave(phone,time);
            if (b) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage("error"));
                }
            } else {
                if (session.isOpen())
                    session.sendMessage(new TextMessage("success"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                session.sendMessage(new TextMessage("error"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 连接建立后处理
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    /**
     * 抛出异常时处理
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        sessions.remove(session);
    }

    /**
     * 连接关闭后处理
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        sessions.remove(session);
    }

}