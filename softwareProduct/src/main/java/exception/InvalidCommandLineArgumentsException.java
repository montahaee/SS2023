package exception;

public class InvalidCommandLineArgumentsException extends Exception{

    /**
     * Constructs an InvalidCommandLineArgumentsException
     */
    public InvalidCommandLineArgumentsException(){}

    /**
     * Constructs an InvalidCommanLineArgumentsException with the specified detail message.
     * @param message specified detail message.
     */
    public InvalidCommandLineArgumentsException(String message){
        super(message);
    }

    /**
     * Constructs an InvalidCommandLineArgumentsException with the specified detail message and cause.
     * @param message specified detail message.
     * @param innerException the cause of the exception.
     */
    public InvalidCommandLineArgumentsException(String message, Throwable innerException){
        super(message, innerException);
    }

    /**
     * Constructs InvalidCommandLineArgumentsException with the specified cause.
     * @param cause reason of the error
     */
    public InvalidCommandLineArgumentsException(Throwable cause) { super(cause);}
}
