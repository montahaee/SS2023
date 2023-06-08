package exception;

/**
 * FileAccessException thrown when path cannot be accessed.
 */
public class FileAccessException extends Exception{

    /**
     *  Default constructs a FileAccessException.
     */
    public FileAccessException(){}

    /**
     * Constructs a FileAccessException with specified detail message.
     * @param massage Message specifying what caused the exception..
     */
    public FileAccessException(String massage){

        super(massage);
    }

    /**
     * Constructs a FileAccessException with the specified detail message and cause.
     * @param message Message specifying. what caused the exception.
     * @param innerException specifies the cause of exception.
     */
    public FileAccessException(String message, Exception innerException){
        super(message, innerException);
    }

    /**
     *
     * @return error's massage.
     */
    public String getMassage() {
        return super.getMessage();
    }

}
