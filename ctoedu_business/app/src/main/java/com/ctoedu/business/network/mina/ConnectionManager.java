package com.ctoedu.business.network.mina;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import java.lang.ref.WeakReference;
import java.net.InetSocketAddress;

/**
 * 负责与远程和服器的连接建立，消息接收
 */
public class ConnectionManager {

    public static String BROADCAST_ACTION = "com.ctoedu.mina.broadcast";
    public static String MESSAGE = "message";

    private ConnectionConfig mConfig;
    private WeakReference<Context> mContext;
    private NioSocketConnector mConnector;
    private IoSession mSession;
    private InetSocketAddress mAddress;

    public ConnectionManager(ConnectionConfig config) {
        this.mConfig = config;
        this.mContext = new WeakReference<Context>(config.getContext());
        init();
    }

    /**
     * 完成Connection初始化
     */
    public void init() {
        mAddress = new InetSocketAddress(mConfig.getIp(), mConfig.getPort());
        mConnector = new NioSocketConnector();
        mConnector.getSessionConfig().setReadBufferSize(mConfig.getReadBufferSize());
        mConnector.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        mConnector.setHandler(new DefaultHandel(mContext.get()));
    }

    /**
     * 取得连接对象
     *
     * @return
     */
    public boolean connect() {
        try {
            ConnectFuture future = mConnector.connect(mAddress);
            future.awaitUninterruptibly();
            mSession = future.getSession();
        } catch (Exception e) {
            return false;
        }
        return mSession == null ? false : true;
    }

    public void disConnection() {
        mConnector.dispose();
        mConnector = null;
        mSession = null;
        mAddress = null;
        mContext = null;
    }

    private static class DefaultHandel extends IoHandlerAdapter {

        private Context mContext;

        private DefaultHandel(Context context) {
            this.mContext = context;
        }

        @Override
        public void sessionOpened(IoSession session) throws Exception {
            if (session != null) {
                SessionManager.getInstance().setSession(session);
            }
        }

        @Override
        public void sessionCreated(IoSession session) throws Exception {
        }

        @Override
        public void messageReceived(IoSession session, Object message) throws Exception {
            if (mContext != null) {
                Intent intent = new Intent(BROADCAST_ACTION);
                intent.putExtra(MESSAGE, message.toString());
                LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
            }
        }

        @Override
        public void sessionClosed(IoSession session) throws Exception {
            SessionManager.getInstance().removeSession();
        }
    }
}