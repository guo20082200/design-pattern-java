package org.zishi.mq.stomp.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Principal;

/**
 * @author zishi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StompAuthenticatedUser implements Principal {

	/**
	 * 用户唯一ID
	 */
	private String username;

	/**
	 * 用户昵称
	 */
	private String userToken;

	/**
	 * 用于指定用户消息推送的标识
	 * @return
	 */
	@Override
	public String getName() {
		return this.username;
	}

}

