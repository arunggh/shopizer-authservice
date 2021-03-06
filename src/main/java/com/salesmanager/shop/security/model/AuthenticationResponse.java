package com.salesmanager.shop.security.model;

import com.salesmanager.shop.dao.Entity;
import java.io.Serializable;

public class AuthenticationResponse extends Entity implements Serializable {
  public AuthenticationResponse() {}

  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private String token;

  public AuthenticationResponse(Long userId, String token) {
    this.token = token;
    super.setId(userId);
  }

  public String getToken() {
    return token;
  }

}
