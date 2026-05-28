package com.spendinsights.controller;

import com.spendinsights.domain.User;
import com.spendinsights.security.UserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public class Me {
    public static User user(UserPrincipal p) {
        return p.user();
    }
}
