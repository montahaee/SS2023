package exception;

public class InvalidTrainLineException extends Exception {

    /**
     * Construct an implicit InvalidTrainLineException
     */
    public InvalidTrainLineException() { super();}

    /**
     * Construct an explicit InvalidTrainLineException with specified massage.
     * @param msg specified detail of error message.
     */
    public InvalidTrainLineException(String msg) { super(msg);}

    /**
     * Constructs an explicit InvalidCommandLineArgumentsException with the specified detail cause.
     * @param cause reason of the error.
     */
    public InvalidTrainLineException(Throwable cause) {super(cause);}


    /**
     * Constructs an explicit InvalidCommandLineArgumentsException with the specified detail message and cause.
     * @param msg specified detail message.
     * @param inner the cause of the exception.
     */
    public InvalidTrainLineException(String msg, Throwable inner) {super(msg, inner);}
}
