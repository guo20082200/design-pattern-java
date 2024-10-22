package doc.d03.vo;

import java.security.Principal;


/**
 * @author zishi
 */
public class WebSocketUser implements Principal {

    /**
     * 用户信息
     */
    private final String name;

    public WebSocketUser(String name) {
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
}
