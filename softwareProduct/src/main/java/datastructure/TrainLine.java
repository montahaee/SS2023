package datastructure;

import exception.InvalidTrainLineException;

import java.util.Objects;

/**
 * Füt mehr objjektive Aspekt
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
     * Constructs a copy of the other.
     * @param other the TrainLine object to copy from.
     */
    public TrainLine(TrainLine other) {
        this.from = other.getFrom();
        this.to = other.getTo();
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

    @Override
    public TrainLine clone() {
        try {
            return (TrainLine) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
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

    @Override
    public int hashCode() {
        return Objects.hash(this.from, this.to);
    }
}
