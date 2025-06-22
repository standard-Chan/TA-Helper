package com.teachAssistantHelper.exception;

public class StaffException extends CustomException {
    public StaffException(ErrorCode errorCode) {
        super(errorCode);
    }
}

