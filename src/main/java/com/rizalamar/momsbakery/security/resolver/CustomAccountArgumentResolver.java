package com.rizalamar.momsbakery.security.resolver;

import com.rizalamar.momsbakery.annotation.CurrentAccount;
import com.rizalamar.momsbakery.domain.Account;
import com.rizalamar.momsbakery.repository.AccountRepository;
import com.rizalamar.momsbakery.security.AccountUserDetails;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CustomAccountArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentAccount.class) && parameter.getParameterType().equals(Account.class);
    }

    @Override
    public @Nullable Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }

        Object principal = authentication.getPrincipal();

        if(principal instanceof AccountUserDetails userDetails){
            return userDetails.getAccount();
        }

        return null;
    }
}
