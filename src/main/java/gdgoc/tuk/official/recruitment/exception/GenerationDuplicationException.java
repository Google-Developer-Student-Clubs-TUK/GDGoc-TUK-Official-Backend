package gdgoc.tuk.official.recruitment.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.DuplicationException;

public class GenerationDuplicationException extends DuplicationException {

  public GenerationDuplicationException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
