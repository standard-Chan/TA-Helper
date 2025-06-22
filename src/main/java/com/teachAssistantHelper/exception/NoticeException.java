package com.teachAssistantHelper.exception;

public class NoticeException extends CustomException {
    public NoticeException(ErrorCode errorCode) {
        super(errorCode);
    }
}

