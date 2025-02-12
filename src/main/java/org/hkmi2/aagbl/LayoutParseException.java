package org.hkmi2.aagbl;

/**
 * Exception that gets thrown if layout parsing fails
 */
public class LayoutParseException extends Exception
{

  /**
   * For serialization 
   */
  private static final long serialVersionUID = 1L;

/**
   * Constructor
   */
  public LayoutParseException() {
  }

  /**
   * Constructor with message
   * @param message The message
   */
  public LayoutParseException(String message) {
    super(message);
  }

  /**
   * Constructor with {@link Throwable}
   * @param cause The {@link Throwable}
   */
  public LayoutParseException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor with message and cause
   * @param message The message
   * @param cause The cause
   */
  public LayoutParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Full constructor with all usual options for {@link Exception}
   * @param message The message
   * @param cause The cause
   * @param enableSuppression See {@link Throwable#addSuppressed(Throwable)}
   * @param writableStackTrace Normally true ; See {@link Throwable#Throwable(String, Throwable)} and
   * in particular the protected {@link Throwable#Throwable(String, Throwable, boolean, boolean)} constructor for more explanations.
   */
  public LayoutParseException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
