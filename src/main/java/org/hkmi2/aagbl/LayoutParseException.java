package org.hkmi2.aagbl;

/**
 * Exception specialized class
 * @see Exception
 * @author hkaradimas
 *
 */
public class LayoutParseException
extends Exception
{

  /**
   * _
   */
  public LayoutParseException() {
  }

  /**
   * _
   * @param message The exception message
   */
  public LayoutParseException(String message) {
    super(message);
  }

  /**
   * _
   * @param cause The cause of the exception
   */
  public LayoutParseException(Throwable cause) {
    super(cause);
  }

  /**
   * _
   * @param message The exception message
   * @param cause The cause of the exception
   */
  public LayoutParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * _
   * @param message The exception message
   * @param cause The cause of the exception
   * @param enableSuppression true if we enable suppression
   * @param writableStackTrace true if stack trace is writable
   */
  public LayoutParseException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
