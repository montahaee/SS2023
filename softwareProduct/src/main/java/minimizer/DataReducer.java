package minimizer;

import datastructure.TrainLine;

import java.util.List;
import java.util.Set;

public class DataReducer {

    private final Set<TrainLine> connections;

    public DataReducer(Set<TrainLine> otherConnection) {
        this.connections = otherConnection;
    }
}
