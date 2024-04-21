package com.community.Community.Exceptions;

import org.springframework.mail.MailException;

public class SpringCommunitiesException extends Throwable {
    public SpringCommunitiesException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringCommunitiesException(String exMessage) {
        super(exMessage);
    }
}
