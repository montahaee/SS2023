package datastructure;

import exception.InvalidTrainLineException;

import java.util.*;

/**
 * This class represent a trin
 */
public class TrainLine implements Cloneable {
    private String from;
    private String to;

    /**
     *
     * @param from name of the station where the train line starts.
     * @param to name of the next station.
     * @throws InvalidTrainLineException if the start and end stations are the same.
     */
    public TrainLine(String from, String to) throws InvalidTrainLineException {
        if (from.equals(to)) {
            throw new InvalidTrainLineException("The start station" +
                    " and the end station of the train line cannot be the same.");
        }
        this.from = from;
        this.to = to;
    }

    /**
     * Constructs a copy of the other.
     * @param other the TrainLine object to copy from.
     */
    public TrainLine(TrainLine other) {
        this.from = other.getFrom();
        this.to = other.getTo();
    }

    //TODO addFrom, addTo, and add based on linkedList
    public void addFrom() {
        List<String >  t = new ArrayList<>();
        t.add("A");
        LinkedHashSet<String> test = new LinkedHashSet<>(t);

        var to = test.contains("A");

    }

    /**
     *
     * @param trainLine  The train line to which the current line will be rebased.
     * @return Returns a new TrainLine object representing the rebased line,
     * or null if the lines are not connectable.
     */
    public TrainLine rebaseLine(TrainLine trainLine) {
        TrainLine newLine = new TrainLine(this);
        try {
            if (!this.isLinesConnectable(trainLine)) {
                return null;
            } else {
                newLine = new TrainLine(this.from, trainLine.getTo());
            }

        } catch (InvalidTrainLineException e) {
            System.err.println(e.getMessage());
        }
        return newLine;
    }

    /**
     *
     * @param trainLine The object to check if it is connected.
     * @return True if only previous of @ trainLine is equal to next of the current line.
     */
    public boolean isLinesConnectable(TrainLine trainLine) {
        return (!this.equals(trainLine)) &&
        (this.to.equals(trainLine.getFrom()));
    }

    /**
     *
     * @return the previous train stain at the line.
     */
    public String getFrom() {
        return this.from;
    }

    /**
     *
     * @return the next train station at the line.
     */
    public String getTo() {
        return this.to;
    }

    /**
     * Set the previous stations' name.
     * @param from the new previous stations' name.
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Set the next stations' name.
     * @param to the new next stations' name.
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Creates and returns a copy of this TrainLine object.
     * The precise meaning of "copy" may depend on the class of the object.
     * The general intent is that, for any object x, the expression:
     * x.clone() != x will be true, and that the expression:
     * x.clone().getClass() == x.getClass() will be true, but these are not absolute requirements.<br>
     * <b>Note</b> that the `clone()` method does not call any constructors.
     * @return a clone of this instance.
     * @throws CloneNotSupportedException if the object's class does not support the `Cloneable` interface.
     * Subclasses like Trinaline that override the `clone()` method can also throw this
     * exception to indicate that an instance cannot be cloned.
     */
    @Override
    public TrainLine clone() {
        try {
            return (TrainLine) super.clone();
        } catch (CloneNotSupportedException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Compares against another line for equality.
     * @param other the object to compare to.
     * @return true if the line is equal to o, otherwise false.
     */
    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TrainLine that) || other.getClass() != this.getClass()) {
            return false;
        }
        return this.from.equals(that.getFrom()) && this.to.equals(that.getTo());
    }

    /**
     * This method is supported for the benefit of hash tables such as those provided by `HashMap`.
     * The general contract of `hashCode` is:
     * - Whenever it is invoked on the same object more than once during an execution of a Java application, the `hashCode` method must consistently return the same integer, provided no information used in `equals` comparisons on the object is modified.
     * - If two objects are equal according to the {@link #equals(Object)} method, then calling the `hashCode` method on each of the two objects must produce the same integer result.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to);
    }
}
