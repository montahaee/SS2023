package framework;

/**
 *The Handleable Interface should be implemented by any classes whose instances
 * are handled to finde a result for the problem as input 'in' and finally transfer
 * it to a new object. The class must define a method of one argument called handle.
 *<p>
 * The interface is designed to provide a common protocol for objects that wish to
 * take an object that represents their issue, work on a solution and transform it
 * into a new object that represents their solution.
 * It has a Data class that is used to store the Problem and Solution together.
 * @param <I> The type of Problem to work with
 * @param <R> The type of resolve to output
 *
 *
 */
public interface Handleable<I,R> {


    /**
     * The class is designed to provide a data container for the problem and solution objects.
     * @param <I> The object class that represents the input.
     * @param <R> The object class that represents the result.
     */
    class Data<I, R>{
        public I in;
        public R out;
    }

    enum Request {
        REDUCE, MINIMIZE
    }

    /**
     *Takes an issue performs the transformation in form of handle and returns the result.
     * @param issue - the specified issue that has to be solved.
     * @return the solution object
     */
    R handle(I issue);
}
