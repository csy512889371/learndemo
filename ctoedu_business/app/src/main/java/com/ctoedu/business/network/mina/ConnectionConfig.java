package com.ctoedu.business.network.mina;

import android.content.Context;

/**
 * 与服务器连接的配置参数类
 */
public class ConnectionConfig {

    private Context context;
    private String ip;
    private int port;
    private int readBufferSize;
    private long connectionTimeout;

    private ConnectionConfig() {
    }

    public Context getContext() {
        return context;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getReadBufferSize() {
        return readBufferSize;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }

    public static class Builder {
        private Context context;
        private String ip = "192.168.1.16";
        private int port = 9123;
        private int readBufferSize = 10240;
        private long connectionTimeout = 10000;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setIP(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            this.port = port;
            return this;
        }

        public Builder setReadBufferSize(int size) {
            this.readBufferSize = size;
            return this;
        }

        public Builder setConnectionTimeout(long millions) {

            this.connectionTimeout = millions;
            return this;
        }

        private void applyConfig(ConnectionConfig config) {
            config.context = this.context;
            config.ip = this.ip;
            config.port = this.port;
            config.readBufferSize = this.readBufferSize;
            config.connectionTimeout = this.connectionTimeout;
        }

        public ConnectionConfig builder() {
            ConnectionConfig config = new ConnectionConfig();
            applyConfig(config);
            return config;
        }
    }
}
