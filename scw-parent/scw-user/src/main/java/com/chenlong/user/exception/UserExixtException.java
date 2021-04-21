package com.chenlong.user.exception;

public class UserExixtException extends RuntimeException{

    public UserExixtException() {
        super("账户已经注册过了");
    }
}
