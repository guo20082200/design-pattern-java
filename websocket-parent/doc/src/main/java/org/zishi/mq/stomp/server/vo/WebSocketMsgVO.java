package org.zishi.mq.stomp.server.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zishi
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WebSocketMsgVO implements Serializable {

	private String content;


	@Override
	public String toString() {
		return "WebSocketMsgVO{" +
				"content='" + content + '\'' +
				'}';
	}
}
