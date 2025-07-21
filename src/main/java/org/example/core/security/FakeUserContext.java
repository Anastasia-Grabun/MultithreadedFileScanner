package org.example.core.security;

import org.springframework.stereotype.Component;

@Component
class FakeUserContext implements UserContext {

    @Override
    public String getCurrentUsername() {
        return "demoUser";
    }

}

