package su.postlink;

import com.sun.istack.internal.NotNull;

/**
 * Created by aleksandr on 15.12.16.
 */
public class Host {
    @NotNull
    private String host = "127.0.0.1";

    @NotNull
    private Integer port = 8080;

    public Host(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
