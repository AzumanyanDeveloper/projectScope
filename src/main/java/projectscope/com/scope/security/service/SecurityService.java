package projectscope.com.scope.security.service;


import projectscope.com.scope.entity.User;

@FunctionalInterface
public interface SecurityService {
    User fetchCurrentUser();
}
