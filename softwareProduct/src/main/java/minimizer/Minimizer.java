package minimizer;

import IO.*;
import framework.Handleable;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 * Minimizer is a class that is used to reduce the input data according 2nd and
 * 3rd rul of the reductions techniques.
 * <U>Notice: the first rul is done automatically by reading the input data </U>
 * of the file in {@link FileSupplier#read()}, without loss of information to find
 * the minimization of the number of the train service and their corresponding train
 * stations, which is also applied in this class.
 * <P></P>
 * Minimizer can run as a thread and will obtain new {@link TrainConnectionJob} from
 * the {@link DataStream dataStream}, transform the data and make the data available for the
 * consumer {@link FileConsumer} in a ConcurrentLinkedQueue.
 */
public class Minimizer implements Handleable<TrainConnectionJob, Minimization>, Runnable {

    private final DataStream<TrainConnectionJob> connectionsDataQueue;
    private final LinkedBlockingQueue<Data<TrainConnectionJob, Minimization>> handleQueue;

    /**
     * Constructs Simulator with DataStream and ConcurrentLinkedQueue.
     * @param connectionsDataQueue DataStream that provides data to be transformed.
     * @param handleQueue Queue that passes transformed data to consumer.
     */

    public Minimizer(DataStream<TrainConnectionJob> connectionsDataQueue, LinkedBlockingQueue<Data<TrainConnectionJob, Minimization>> handleQueue) {
        this.connectionsDataQueue = connectionsDataQueue;
        this.handleQueue = handleQueue;
    }

    /**
     * Takes an issue performs the transformation in form of handle and returns the result.
     *
     * @param issue - the specified issue that has to be handled.
     * @return the handle object
     */
    @Override
    public Minimization handle(TrainConnectionJob issue) {
        List<List<String>> connections = issue.getConnections();
/*        The followings are commented because of the testing
          the reduction rules in MinimizerTest. After testing,
          You can uncomment those.
 */

///        secondReduceConnections(connections);
//        thirdReduceConnections(connections);

        return new Minimization(findPopularRoute(issue.getConnections()));
    }

    /**
     * This method takes in a list of lists of strings representing
     * train line connections and returns a list of popular routes
     * for service station.
     * @param connections A list of lists of strings
     *                    representing train line connections.
     * @return A list of popular routes for srvice stations.
     */
    private List<String> findPopularRoute(List<List<String>> connections) {
        boolean findAllRoute = false;

        List<List<String>> lastIntersection = new ArrayList<>();

        List<String> result = new ArrayList<>();

        while (!findAllRoute) {
            List<String> maxList = findMaxList(connections);
            List<List<String>> commonLines = findCommonStationsTrainLines(connections, maxList);
            Map.Entry<String, Long> mostRepeated = findMostRepeatedStation(commonLines);
            if (mostRepeated == null) {
                break;
            }
            if (mostRepeated.getValue() >= 1 && !result.contains(mostRepeated.getKey()))
                result.add(mostRepeated.getKey());

            // Check if there are more connections to process
            if (connections.isEmpty()) {
                findAllRoute = true;
            } else {
                List<List<String>> newConnections = findCommonStationsTrainLines(commonLines, findMaxList(commonLines));
                maxList = findMaxList(connections);
                lastIntersection = findCommonStationsTrainLines(newConnections, maxList);
            }
            if (!lastIntersection.isEmpty() &&
                findMostRepeatedStation(lastIntersection) != null) {
                // Add it to the result list
                Map.Entry<String, Long> lastMostRepeated = findMostRepeatedStation(lastIntersection);
                if (lastMostRepeated.getValue() >= 1 && !result.contains(lastMostRepeated.getKey())) result.add(lastMostRepeated.getKey());
            }
        }
        return result;
    }

    /**
     * This method takes in a list of lists of strings representing train line connections
     * and a list of strings representing a pivot train line. The method returns a list of
     * lists of strings representing the common stations between the train lines in connections
     * and the pivot train line.
     * @param connections A list of lists of strings representing train line connections.
     * @param pivot pivot A list of strings representing a pivot train line.
     * @return A list of lists of strings representing the common stations between
     * the train lines in connections and the pivot train line.
     */
    private List<List<String>> findCommonStationsTrainLines(List<List<String>> connections, List<String> pivot) {
        List<List<String>> intersections = new ArrayList<>();
        for (List<String> connection : connections) {
            List<String> copy = new ArrayList<>(connection);
            copy.retainAll(pivot);
            intersections.add(copy);
        }
        return intersections;
    }

    /**
     * This method takes in a list of lists of strings representing train line connections and
     * returns a Map.Entry object containing the most repeated station and its frequency.
     * @param connections A list of lists of strings representing train line connections.
     * @return A Map.Entry object containing the most repeated station and its frequency.
     */
    private Map.Entry<String, Long> findMostRepeatedStation(List<List<String>> connections) {
        // store the result of the max operation in a variable
        Optional<Map.Entry<String, Long>> mostRepeatedEntry = connections.stream().flatMap(List::stream).collect(Collectors.groupingBy (Function.identity (),
                Collectors.counting ())).entrySet().stream().max(Map.Entry.comparingByValue ());
        // return the variable or null if not present
        return mostRepeatedEntry.orElse(null);
    }

    /**
     * This method takes in a list of lists of strings representing train line connections and
     * returns a list of strings representing the train line with the maximum number of stations.
     * @param connections connections A list of lists of strings representing train line connections.
     * @return A list of strings representing the train line with the maximum number of stations.
     */
    private static List<String> findMaxList(List<List<String>> connections) {
        List<String> maxList = connections.stream().max(Comparator.comparingInt(List::size)).orElse(null);
        connections.remove(maxList);
        return maxList;
    }

    /**
     * This only for testing the reduction technique ( the 3rd rule).
     * @param connections represent all possible train connection lines.
     */
    public void TestThirdReduction(List<List<String>> connections) {
        thirdReduceConnections(connections);
    }


    /**
     * This method takes in a list of lists of strings representing train line connections and
     * reduces the connections by removing subsets.
     * @param connections A list of lists of strings representing train line connections.
     */
    private void thirdReduceConnections(List<List<String>> connections) {
        List<List<String>> copy = new ArrayList<>(connections);

        for (List<String> subset : copy) {
            for (List<String> superset : connections) {
                if (new HashSet<>(superset).containsAll(subset) && !subset.equals(superset)) {
                    connections.remove(superset);
                    break;
                }
            }
        }
    }

    /**
     * This only for testing the reduction technique ( the second rule).
     * @param connections represent all possible train connection lines.
     */
    public void TestSecondReduction(List<List<String>> connections) {
        secondReduceConnections(connections);
    }

    /**
     * This method is based on the second reduction technic in the task sheet.
     * @param connections represent all possible connections between trains.
     */
    private void secondReduceConnections(List<List<String>> connections) {
        boolean hasRepeatedPair = true;
        while (hasRepeatedPair) {
            // convert the 2D list into a list of pairs
            List<Map.Entry<String, String>> pairs = convertConnectionsToLinePairs(connections);
            // remove the most repeated pair and get the station and the frequency
            Map.Entry<String, Long> result = removeMostRepeatedTrainLine(pairs);
            // check if the frequency is greater than or equal to 2
            if (result != null && result.getValue() > 2) {
                // remove the station from the 2D list
                removeStationFromConnections(connections, result.getKey());
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
    private void removeStationFromConnections(List<List<String>> connections, String station) {
        Stream<List<String>> stream = connections.parallelStream ();
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
    private Map.Entry<String, Long> removeMostRepeatedTrainLine(List<Map.Entry<String, String>> list) {
        Stream<Map.Entry<String, String>> stream = list.stream ();
        Map<Map.Entry<String, String>, Long> frequencyMap = stream
                .collect (Collectors.groupingBy (
                        Function.identity (),
                        Collectors.counting ()));
        // find the entry with the maximum frequency
        Optional<Map.Entry<Map.Entry<String, String>, Long>> mostRepeatedEntry = frequencyMap.entrySet ().stream ()
                // compare the entries by their frequency
                .max (Map.Entry.comparingByValue ());
        // remove the most repeated entry from the list if present
        mostRepeatedEntry.ifPresent (entry -> list.remove (entry.getKey ()));
        return mostRepeatedEntry.map (entry -> new AbstractMap.SimpleEntry<> (
                entry.getKey ().getKey (), entry.getValue ())).orElse (null);
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
    private List<Map.Entry<String, String>> convertConnectionsToLinePairs(List<List<String>> connections) {
        Stream<List<String>> stream = connections.stream ();
        return stream
                // flatten each sublist into a stream of pairs of adjacent elements
                .flatMap (sublist -> IntStream.range (0, sublist.size () - 1)
                        // map each pair to a Map.Entry
                        .mapToObj (i -> new AbstractMap.SimpleEntry<> (sublist.get (i), sublist.get (i + 1))))
                .collect (Collectors.toList ()); // since java 16 it is not needed to collect it.
    }

    /**
     *  Starts the thread and gets data from the DataStream provided by the Producer,
     *  transforms it and wraps the original and transformed data in {@link Data}
     *  and adds it to the ConcurrentLinkedQueue for the consumer.
     *  Finishes when Messdaten without filepath are received.
     */
    @Override
    public void run() {
        Data<TrainConnectionJob, Minimization> data;

        HashSet<String> processedData = new HashSet<>();

        while(true){

            TrainConnectionJob job = this.connectionsDataQueue.take();
            if(job.getFilepath() == null){
                this.handleQueue.add(new Data<>());
                break;
            }else if(processedData.contains(job.getFilepath())){
                continue;
            }
            processedData.add(job.getFilepath());
            Minimization handle = handle(job);

            data = new Data<>();

            data.in = job;
            data.out = handle;

            this.handleQueue.add(data);

        }

    }
}
