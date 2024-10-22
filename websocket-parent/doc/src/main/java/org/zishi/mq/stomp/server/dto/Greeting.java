package org.zishi.mq.stomp.server.dto;

import lombok.Getter;

/**
 * @author zishi
 */
@Getter
public class Greeting {

  private String content;

  public Greeting() {
  }

  public Greeting(String content) {
    this.content = content;
  }

}