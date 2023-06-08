package IO;

import framework.Handleable;

import java.util.List;

/**
 * The TrainConnectionJob class stores the input data from the {@link FileSupplier}.
 * It holds all connections between train lines
 */
public class TrainConnectionJob {

    /** To get the error messages which are not ignorable to reading the input  */
    private String error = "";
    private final String filepath;

    private List<List<String>> connections;

    /**
     * Constructs an implicit TrainConnectionJob.
     */
    public TrainConnectionJob() {
        this.filepath = null;
    }

    /**
     *
     * @param filepath represents Path of the file that holds connection data.
     * @param connections represents the train liens.
     */
    public TrainConnectionJob(String filepath, List<List<String>> connections) {
        this.filepath = filepath;
        this.connections = connections;
    }

    /**
     * To comment the irregularity while reading the input file
     * in the output file can be this methode employed.
     * @see FileConsumer#write(Handleable.Data)
     * @return comments to ignorable errors. This can be empty.
     */
    public String getError() {
        return this.error;
    }

    /**
     *
     * @param error represents an eventual error, which is not ignorable.
     */
    public void setErrors(String error) {
        this.error = error;
    }

    /**
     *
     * @return path to file which provided {@link #connections} data.
     */
    public String getFilepath() {
        return this.filepath;
    }

    /**
     *
     * @return all connections for all trains.
     */
    public List<List<String>> getConnections() {
        return this.connections;
    }

}
