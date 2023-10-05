package minimizer;

import IO.*;
import framework.Handleable;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

//TODO repair the MinimizerTest prosovical

/**
 * Minimizer
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
        List<LinkedList<String>> connections = issue.getConnections();
/*        The followings are commented because of the testing
          the reduction rules in MinimizerTest. After testing,
          You can uncomment those.
 */

///        secondReduceConnections(connections);
//        thirdReduceConnections(connections);
        DataReducer reducer = new DataReducer(connections);
//        reducer.secondReduceConnections(connections);
//        reducer.thirdReduceConnections();

        return new Minimization(findPopularRoute(issue.getConnections()));
//        return new Minimization(findPopularRoute(reducer.getConnections()));
    }

    /**
     * This method takes in a list of lists of strings representing
     * train line connections and returns a list of popular routes
     * for service station.
     * @param connections A list of lists of strings
     *                    representing train line connections.
     * @return A list of popular routes for srvice stations.
     */
    private Set<String> findPopularRoute(List<LinkedList<String>> connections) {
        boolean findAllRoute = false;

        List<LinkedList<String>> lastIntersection = new ArrayList<>();

        Set<String> result = new HashSet<>();

        while (!findAllRoute) {
            LinkedList<String> maxList = findMaxList(connections);
            List<LinkedList<String>> commonLines = findCommonStationsTrainLines(connections, maxList);
            Map.Entry<Long, List<String>> firstMostRepeats = findMostRepeatedStations(commonLines);
            LinkedList<String> newStation = new LinkedList<>();

            if (firstMostRepeats.getKey() >= 1 && result.stream().noneMatch(firstMostRepeats.getValue()::contains)) {
                newStation = new LinkedList<>(firstMostRepeats.getValue());
                newStation.retainAll(maxList);
                result.add(newStation.getFirst());
            }
//                result.add(firstMostRepeats.getValue().getFirst());

            // Check if there are more connections to process
            if (connections.isEmpty()) {
                findAllRoute = true;
            } else {
                List<LinkedList<String>> newConnections = findCommonStationsTrainLines(commonLines, findMaxList(commonLines));
                maxList = findMaxList(connections);
                lastIntersection = findCommonStationsTrainLines(newConnections, maxList);
            }
            if (!lastIntersection.isEmpty() &&
                !findMostRepeatedStations(lastIntersection).getValue().isEmpty()) {
                // Add it to the result list
                Map.Entry<Long, List<String>> lastMostRepeated = findMostRepeatedStations(lastIntersection);
                if (lastMostRepeated.getKey() >= 1 && result.stream().noneMatch(lastMostRepeated.getValue()::contains)) {
                    newStation =  new LinkedList<>(lastMostRepeated.getValue());
                    newStation.retainAll(maxList);
                    result.add(newStation.getFirst());
//                    result.add(lastMostRepeated.getValue().getFirst());
                }
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
    private List<LinkedList<String>> findCommonStationsTrainLines(List<LinkedList<String>> connections, List<String> pivot) {
        List<LinkedList<String>> intersections = new ArrayList<>();
        for (LinkedList<String> connection : connections) {
            LinkedList<String> copy = new LinkedList<>(connection);
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
    private static Map.Entry<Long, List<String>> findMostRepeatedStations(List<LinkedList<String>> connections) {
        Map<String, Long> frequencyMap = connections.stream().flatMap(List::stream).collect(Collectors.groupingBy(
                Function.identity(), Collectors.counting()));
        long maxFrequency = frequencyMap.values().stream().max(Long::compare).orElse(0L);
        List<String> mostRepeatedStations = frequencyMap.entrySet().stream().filter(entry -> entry.getValue() == maxFrequency).map(
                Map.Entry::getKey).toList();
        return new AbstractMap.SimpleEntry<>(maxFrequency, mostRepeatedStations);
    }

    /**
     * This method takes in a list of lists of strings representing train line connections and
     * returns a list of strings representing the train line with the maximum number of stations.
     * @param connections connections A list of lists of strings representing train line connections.
     * @return A list of strings representing the train line with the maximum number of stations.
     */
    private LinkedList<String> findMaxList(List<LinkedList<String>> connections) {
        LinkedList<String> maxList = connections.stream().max(Comparator.comparingInt(List::size)).orElse(null);
        connections.remove(maxList);
        return maxList;
    }

    /**
     *  Starts the thread and gets data from the DataStream provided by the Producer,
     *  transforms it and wraps the original and transformed data in {@link Data}
     *  and adds it to the ConcurrentLinkedQueue for the consumer.
     *  Finishes when input data without filepath are received.
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
