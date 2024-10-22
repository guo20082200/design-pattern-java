package org.zishi.mq.websocketserver.dto;

import java.security.Principal;

/**
 * @author zishi
 */
public class UserPrincipal  implements Principal {

    private  final String name;

    public UserPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}