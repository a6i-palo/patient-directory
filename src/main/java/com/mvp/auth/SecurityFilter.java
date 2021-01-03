package com.mvp.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.mvp.auth.models.Credentials;
import com.mvp.auth.models.User;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    verifyToken(request);
    filterChain.doFilter(request, response);
  }

  private void verifyToken(HttpServletRequest request) {
    String session = null;
    FirebaseToken decodedToken = null;
    Credentials.CredentialType type = null;
    String token = getBearerToken(request);
    logger.info(token);
    try {

      if (token != null && !token.equalsIgnoreCase("undefined")) {
        decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
        type = Credentials.CredentialType.ID_TOKEN;
      }

    } catch (FirebaseAuthException e) {
      e.printStackTrace();
      log.error("Firebase Exception:: ", e.getLocalizedMessage());
    }
    User user = firebaseTokenToUserDto(decodedToken);
    if (user != null) {
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(
              user, new Credentials(type, decodedToken, token, session), null);
      authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
  }

  private String getBearerToken(HttpServletRequest request) {
    String bearerToken = null;
    String authorization = request.getHeader("Authorization");
    if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
      bearerToken = authorization.substring(7);
    }
    return bearerToken;
  }

  private User firebaseTokenToUserDto(FirebaseToken decodedToken) {
    User user = null;
    if (decodedToken != null) {
      user = new User();
      user.setUid(decodedToken.getUid());
      user.setName(decodedToken.getName());
      user.setEmail(decodedToken.getEmail());
      user.setPicture(decodedToken.getPicture());
      user.setIssuer(decodedToken.getIssuer());
      user.setEmailVerified(decodedToken.isEmailVerified());
    }
    return user;
  }
}
