package IO;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * DataStream class creates a thread safe container that allows a producer to insert data and
 * continuously overwrite the old values. Each takes operation {@link #take()}
 * must wait until a new object is available.
 * <p>
 * Provides a one parameter method for adding and method for taking the element from the DataStream.
 *
 * @param <S> The streaming data to be transferred.
 */
public class DataStream<S> {

    private S input;
    private final AtomicInteger counter;

    /**
     * Implicitly constructs a DataStream setting counter to zero.
     */
    public DataStream() {
        this.counter = new AtomicInteger(0);
    }

    /**
     * Insert data to the stream. Overwriting old data and only making the newest element available.
     * Notifying threads that are waiting to take an item{@link #take()}.
     * @param newInput is the element that should be inserted.
     */
    public synchronized void put(final S newInput) {
        Objects.requireNonNull(newInput,"TrainConnectionJob data cannot be null.");
        this.input = newInput;
        counter.set(1);
        notify();
    }

    /**
     * Retrieves and removes the element from the DataStream,
     * waiting if necessary for another thread to insert it.
     * @return the type of element that is contained in the stream.
     */
    public synchronized S take() {
        while (this.counter.get() == 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.counter.decrementAndGet();
        return this.input;
    }
}
