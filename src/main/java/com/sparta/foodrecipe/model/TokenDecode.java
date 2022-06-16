package com.sparta.foodrecipe.model;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenDecode {
    String token;
    String username;
    String nickname;
    Long id;

    public TokenDecode(DecodedJWT jwt) {
        this.token = jwt.getToken();
        this.id = Long.parseLong(jwt.getClaim("id").toString());
        this.username = jwt.getClaim("username").toString();
        this.nickname = jwt.getClaim("nickname").toString();

    }
}
