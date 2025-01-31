package gdgoc.tuk.official.google.exception;

import gdgoc.tuk.official.global.ErrorCode;
import gdgoc.tuk.official.global.exception.GoogleSpreadSheetsException;

public class SheetsCreationException extends GoogleSpreadSheetsException {

  public SheetsCreationException(final ErrorCode errorCode) {
    super(errorCode);
  }
}
