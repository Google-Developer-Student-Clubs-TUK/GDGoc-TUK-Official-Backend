package gdgoc.tuk.official.question.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.ResourceNotFoundException;

public class QuestionNotFoundException extends ResourceNotFoundException {

  public QuestionNotFoundException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
