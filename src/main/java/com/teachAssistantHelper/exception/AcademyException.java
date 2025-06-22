package com.teachAssistantHelper.exception;

public class AcademyException extends CustomException {
    public AcademyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
