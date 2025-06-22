package com.teachAssistantHelper.exception;

public class StudentException extends CustomException {
    public StudentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
