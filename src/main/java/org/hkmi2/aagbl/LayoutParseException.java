package org.hkmi2.aagbl;

/**
 * Exception specialized class that gets thrown when there was an error in
 * the parsing of an Ascii art layout file.
 * @see Exception
 * @author hkaradimas
 *
 */
public class LayoutParseException
extends Exception
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
   * @param message The exception message
   */
  public LayoutParseException(String message) {
    super(message);
  }

  /**
   * Constructor with {@link Throwable}
   * @param cause The cause of the exception
   */
  public LayoutParseException(Throwable cause) {
    super(cause);
  }

  /**
   * Constructor with message and cause
   * @param message The exception message
   * @param cause The cause of the exception
   */
  public LayoutParseException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Full constructor with all usual options for {@link Exception}
   * @param message The exception message
   * @param cause The cause of the exception
   * @param enableSuppression true if we enable suppression, See {@link Throwable#addSuppressed(Throwable)}
   * @param writableStackTrace true if stack trace is writable. Normally true ; See {@link Throwable#Throwable(String, Throwable)} and
   * in particular the protected {@link Throwable#Throwable(String, Throwable, boolean, boolean)} constructor for more explanations.
   */
  public LayoutParseException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
