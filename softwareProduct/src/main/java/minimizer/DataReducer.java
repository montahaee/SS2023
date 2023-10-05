package minimizer;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The DataReducer class is responsible for reduction of the input data
 * assigned to train line connections {@link IO.TrainConnectionJob}.It provides functionality
 * to remove redundancy and unnecessary inputs to reduce the calculation of the best candidate
 * places for train station service applied by class {@link Minimizer}
 * This class is crucial for scenarios where there is a large set of connections,
 * and there's a need to make the data more manageable and the processing more efficient.
 */
public class DataReducer {

    private final List<LinkedList<String>> connections;

    public DataReducer() {
        this(new ArrayList<>());
    }

    public DataReducer(List<LinkedList<String>> connections) {
        this.connections = connections;
    }

    /**
     * This method is based on the second reduction technic in the task sheet.
//     * @param connections represent all possible connections between trains.
     */
    public void secondReduceConnections(@NotNull List<LinkedList<String>> connections) {
        boolean hasRepeatedPair = true;
        while (hasRepeatedPair) {
            // convert the 2D list into a list of pairs
            List<Map.Entry<String, String>> pairs = convertConnectionsToLinePairs(connections);
            // remove the most repeated pair and get the station and the frequency
//            Map.Entry<String, Long> result = removeMostRepeatedTrainLine(pairs);
            String result = removeMostRepeatedTrainLine(pairs);
            // check if the frequency is greater than or equal to 2
            if (result != null) {
                // remove the station from the 2D list
                removeStationFromConnections(connections, result);
            } else {
                hasRepeatedPair = false;
            }
        }
    }

    /**
     *
     * @param connections represent all possible connections between trains.
     * @param station Teh train station which will be removed from the connections.
     */
    private void removeStationFromConnections(List<LinkedList<String>> connections, String station) {
        Stream<LinkedList<String>> stream = connections.parallelStream ();
        stream.forEach (sublist -> sublist.remove (station));
    }

    /**
     * This methode has maine roll to imply the second rul of the reduction method.
     * This will be repeated in a while loop as long as the value of entry is equals
     * or greater than 2.
     * @param list A list of pairs which of them contains the names of two connected stations.
     * @return  return a Map.Entry with the key of the most repeated train station
     * and the number of repetition as the value or null if the list is empty.
     */
    private String removeMostRepeatedTrainLine(@NotNull List<Map.Entry<String, String>> list) {
        Map<Map.Entry<String, String>, Long> frequencyMap = list.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));

        Optional<Map.Entry<Map.Entry<String, String>, Long>> mostRepeatedEntry = frequencyMap.entrySet().stream().filter(e -> {
                    String innerKey = e.getKey().getKey();
                    String innerVal = e.getKey().getValue();
                    boolean isUnique = frequencyMap.entrySet().stream().noneMatch(
                            entry -> entry.getKey().getKey().equals(innerKey) && !entry.getKey().getValue().equals(innerVal));
                    return isUnique && e.getValue() >= 2;
                }).max(Map.Entry.comparingByValue());

        return mostRepeatedEntry.map(e -> e.getKey().getKey()).orElse(null);
    }


    /**
     *
     * @param connections A 2D list representing connections
     *                    between train stations. Each sublist
     *                    represents a train line and contains
     *                    the names of the stations on that
     *                    line in order.
     * @return A list of pairs representing edges in a graph.
     * Each pair contains the names of two connected stations.
     */
    private List<Map.Entry<String, String>> convertConnectionsToLinePairs(List<LinkedList<String>> connections) {
        Stream<LinkedList<String>> stream = connections.stream ();
        return stream
                // flatten each sublist into a stream of pairs of adjacent elements
                .flatMap (sublist -> IntStream.range (0, sublist.size () - 1)
                        // map each pair to a Map.Entry
                        .mapToObj (i -> new AbstractMap.SimpleEntry<> (sublist.get (i), sublist.get (i + 1))))
                .collect (Collectors.toList ()); // since java 16 it is not needed to collect it.
    }

    /**
     * This method takes in a list of lists of strings representing train line connections and
     * reduces the connections by removing subsets.
//     * @param connections A list of lists of strings representing train line connections.
     */
    public void thirdReduceConnections(List<LinkedList<String>> connections) {
        List<LinkedList<String>> copy = new ArrayList<>(connections);

        for (LinkedList<String> subset : copy) {
            for (LinkedList<String> superset : connections) {
                if (new HashSet<>(superset).containsAll(subset) && !subset.equals(superset)) {
                    connections.remove(superset);
                    break;
                }
            }
        }
    }

    /**
     *
     * @return the connections to find the
     */
    public List<LinkedList<String>> getConnections() {
        return connections;
    }
}
