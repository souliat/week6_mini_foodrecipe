package com.sparta.foodrecipe.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.foodrecipe.dto.TokenResponseDto;
import com.sparta.foodrecipe.model.TokenDecode;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;


@Component
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest )request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        TokenResponseDto tokenResponseDto = null;
        String uri = httpRequest.getRequestURI();

        if (uri.contains("/api/posts/write") || uri.contains("/api/posts/update") || uri.contains("api/posts/delete")
                || uri.contains("/api/comments/write") || uri.contains("/api/like")) {
            String authorization = httpRequest.getHeader("Authorization");

            // 에러 발생 부분
            if(authorization == null || !authorization.startsWith("Bearer ")) {
                tokenResponseDto  = new TokenResponseDto(false, "토큰이 유효하지 않습니다.");
            }


            // 인증 성공 부분
            if(tokenResponseDto == null) {
                String token = authorization.substring(7);

                DecodedJWT jwt = null;

                try {
                    Algorithm algorithm = Algorithm.HMAC256("zheldWhffkwkfgkrhtlvek"); //use more secure key
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer("holyshit")
                            .build(); //Reusable verifier instance

                    jwt = verifier.verify(token);

                    TokenDecode tokenDecode = new TokenDecode(jwt);
                    httpRequest.setAttribute("decode", tokenDecode);

                } catch (JWTVerificationException exception){
                    exception.printStackTrace();
                    System.out.println(exception.fillInStackTrace());
                    System.out.println(exception);
                    tokenResponseDto  = new TokenResponseDto(false, "토큰 인증 에러");
                }
            }
        }

        if(uri.contains("/api/posts/detail")) {
            String authorization = httpRequest.getHeader("Authorization");

            if(authorization == null) {
                httpRequest.setAttribute("decode", null);
            } else {
                String token = authorization.substring(7);

                DecodedJWT jwt = null;

                try {
                    Algorithm algorithm = Algorithm.HMAC256("zheldWhffkwkfgkrhtlvek"); //use more secure key
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer("holyshit")
                            .build(); //Reusable verifier instance

                    jwt = verifier.verify(token);

                    TokenDecode tokenDecode = new TokenDecode(jwt);
                    httpRequest.setAttribute("decode", tokenDecode);

                } catch (JWTVerificationException exception){
                    exception.printStackTrace();
                    System.out.println(exception.fillInStackTrace());
                    System.out.println(exception);
                    tokenResponseDto  = new TokenResponseDto(false, "토큰 인증 에러");
                }
            }
        }

        if(uri.endsWith("/api/posts")) {
            String authorization = httpRequest.getHeader("Authorization");

            if(authorization == null) {
                httpRequest.setAttribute("decode", null);
            } else {
                String token = authorization.substring(7);

                DecodedJWT jwt = null;

                try {
                    Algorithm algorithm = Algorithm.HMAC256("zheldWhffkwkfgkrhtlvek"); //use more secure key
                    JWTVerifier verifier = JWT.require(algorithm).withIssuer("holyshit")
                            .build(); //Reusable verifier instance

                    jwt = verifier.verify(token);

                    TokenDecode tokenDecode = new TokenDecode(jwt);
                    httpRequest.setAttribute("decode", tokenDecode);

                } catch (JWTVerificationException exception){
                    exception.printStackTrace();
                    System.out.println(exception.fillInStackTrace());
                    System.out.println(exception);
                    tokenResponseDto  = new TokenResponseDto(false, "토큰 인증 에러");
                }
            }
        }


        if(tokenResponseDto != null) {
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setStatus(HttpServletResponse.SC_OK);

            OutputStream out = httpResponse.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter().writeValue(out, tokenResponseDto);
            out.flush();
            return;
        }


        chain.doFilter(httpRequest, httpResponse);



    }
}
