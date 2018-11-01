package com.ctoedu.business.network.mina;

import org.apache.mina.core.session.IoSession;

/**
 * 用来管理客户端Session单例对象，负责与服务器通信
 */
public class SessionManager {

    private static SessionManager mInstance = null;

    /**
     * 最终于服务器进行通信的对象
     */
    private IoSession mSession;

    public static SessionManager getInstance() {
        if (mInstance == null) {
            synchronized (SessionManager.class) {
                if (mInstance == null) {
                    mInstance = new SessionManager();
                }
            }
        }
        return mInstance;
    }

    private SessionManager() {
    }

    public void setSession(IoSession session) {
        this.mSession = session;
    }

    /**
     * 将对象写到服务器
     *
     * @param msg
     */
    public void writeToServer(Object msg) {
        if (mSession != null) {
            mSession.write(msg);
        }
    }

    public void closeSession() {
        if (mSession != null) {
            mSession.closeOnFlush();
        }
    }

    public void removeSession() {
        this.mSession = null;
    }


}
