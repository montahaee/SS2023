import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestDrivedeveloping {

    public static void main(String[] args) {
//        String[][] arr = new String[][]{{"HH","H","B","L"}, {"K","FFM","S"},{"HH","H","FFM","N","M"},
//                {"H","FFM","N"},{"DA","FFM","N"},{"HH","FFM","M"}};
//        for (String[] e: arr) {
//            System.out.println(Arrays.toString(e));
//
//        }
//        System.out.println(System.lineSeparator());
        //make a 2D List
        List<List<String>> list = new ArrayList<>();
        list.add(List.of("HH","H","B","L"));
        list.add(List.of("K","FFM","S"));
        list.add(List.of("HH","H","FFM","N","M"));
        list.add(List.of("H","FFM","N"));
        list.add(List.of("DA","FFM","N"));
        list.add(List.of("HH","FFM","M"));
//        var t = list;
//        removeStationDuplication(t);
//        System.out.println("test: "+t);
//        System.out.println(Arrays.toString(findMaxList(arr)));
//        System.out.println(System.lineSeparator());
        System.out.println(list);
        removeSuperSets(list);
        System.out.println(list);
        System.out.println();
        System.out.println(findPopularRoute(list));
        List<String> maxList1 = findMaxList(list);  //35 done 7
        System.out.println(list);
//        removeStationDuplication(list);
        System.out.println();
//        removeDuplication(list);
        System.out.println(list);
        System.out.println(maxList1);
//        The number of intersection element is 0ne less than the original matrix (2D List)
        System.out.println("Intersection1:");
        List<List<String>> intersections1 = findCommonStationsTrainLines(list,maxList1); // 40 done 11
        System.out.println(intersections1);
        removeDuplication(intersections1);
        System.out.println(intersections1);
        System.out.println(findMostRepeatedStation(intersections1)); //43 & 46 done 12
//        var testMyAlgo = list;
//        removeConnectionsWithMostRepeatedStation(testMyAlgo);
//        System.out.println(testMyAlgo);
        System.out.println(System.lineSeparator());
        System.out.println("Intersection2:");
        List<String> maxListFromIntersection1 = findMaxList(intersections1);//54 done 21
        System.out.println(maxListFromIntersection1);
        List<String> maxList2 = findMaxList(list); // 35 done 22
        System.out.println(maxList2);
        List<List<String>> intersections2 =
                findCommonStationsTrainLines(intersections1,maxListFromIntersection1); // 54 done 21

        System.out.println(intersections2);
        removeDuplication(intersections2);
        System.out.println(intersections2);
        System.out.println(hasEmptyList(intersections2));
        List<List<String>> intersections2_1 = //40 done
                findCommonStationsTrainLines(intersections2,maxList2); //done 20
        System.out.println(intersections2_1);
        removeDuplication(intersections2_1);
        System.out.println(intersections2_1);
        System.out.println(findMostRepeatedStation( intersections2_1));


//        List<String> maxList2_1 = findMaxList(intersections2_1);
//
//        System.out.println(maxList2_1);
//
//        List<List<String>> intersections3_1 = findCommonStationsTrainLines(intersections2_1,maxList2_1);
//        System.out.println(intersections3_1);

        System.out.println(System.lineSeparator());

        System.out.println("Intersection3:");
        List<String> maxList3 = findMaxList(intersections2);
        System.out.println(maxList3);
        List<List<String>> intersections3 = findCommonStationsTrainLines(intersections2,maxList3);
        System.out.println(intersections3); //TODO how to avoid /remove the duplications in every List of 2D List (next Step Reduction Minimizer)
        removeDuplication(intersections3);
        System.out.println(intersections3);



// TODO       Search if there is an empty List
        System.out.println(hasEmptyList(intersections3));
//  TODO      Search If should be all element of the last intersection 2D list so the 2D list
//   with at least one empty 1D list have to be the same.
        System.out.println("reduction 2:");
        List<List<String>> reduction_2 = new ArrayList<>();
        reduction_2.add(List.of("HH","DA","H","B","L"));
        reduction_2.add(List.of("K","FFM","S"));
        reduction_2.add(List.of("HH","DA","H","FFM","N","M")); // add a double quote here
        reduction_2.add(List.of("FM","DA","H","FFM","N"));
        reduction_2.add(List.of("DA","FFM","N"));
        reduction_2.add(List.of("HH","FFM","M"));
        System.out.println("The original 2D List");
        System.out.println( reduction_2);
//        System.out.println(removeStationDuplication(reduction2));
        System.out.println(maxIntersection(reduction_2));
        System.out.println(removeIntersectionElements(reduction_2));
        System.out.println(removeDuplicates(reduction_2));
        var linkedReduction = reduction2(reduction_2);
//        System.out.println(linkedReduction);
        System.out.println(System.lineSeparator());
//        System.out.println("The conversion of 2D List to List of paired element:");
//        var pairs = convertToPairsUsingStream(reduction_2);
//        System.out.println(pairs);
//        System.out.println("\n max repeated:");
//        System.out.println(findMostRepeatedEntry(pairs));
        List<List<String>> reduction_2_1 = new ArrayList<>();
        reduction_2_1.add(List.of("S","DA","H","K"));
        reduction_2_1.add(List.of("FFM","DA","H","K"));
        reduction_2_1.add(List.of("M","DA","H","B"));
        reduction_2_1.add(List.of("C","M","E","H"));
        System.out.println(System.lineSeparator());

//        System.out.println("The original 2D List from Task sheet:");
//        System.out.println(reduction_2_1);
//        var pairs_2_1 = convertToPairsUsingStream(reduction_2_1);
//        System.out.println("The  paired List from Task sheet:");
//        System.out.println(pairs_2_1);
//        System.out.println("\n max repeated:");
//        System.out.println(findMostRepeatedEntry(pairs_2_1));

        System.out.println(System.lineSeparator());
        List<List<String>> reduction_2_2 = new ArrayList<>();
        reduction_2_2.add(List.of("S","DA","H","K"));
        reduction_2_2.add(List.of("FFM","DA","H","K"));
        reduction_2_2.add(List.of("M","DA","H","K"));
        reduction_2_2.add(List.of("C","M","E","K"));
        System.out.println("The mor complicated 2D list than 2D List from Task sheet:");
        System.out.println(reduction_2_2);
        System.out.println("The paired List from Task sheet:");
        var pairs_2_2 = convertConnectionsToLinePairs(reduction_2_1);

        System.out.println(pairs_2_2);
        System.out.println("\n max repeated:");
        System.out.println(removeMostRepeatedTrainLine(pairs_2_2));
        System.out.println(pairs_2_2);
        System.out.println("\n max repeated:");

        System.out.println(removeMostRepeatedTrainLine(pairs_2_2));
        System.out.println(pairs_2_2);
        System.out.println(removeMostRepeatedTrainLine(pairs_2_2));
        System.out.println(pairs_2_2);
        System.out.println(removeMostRepeatedTrainLine(pairs_2_2));

        TestArrayList();






    }


//    to remove duplications between connections. This should be to each 2D(original  list run.

    /**
     *
     * @param connections represents the connections between many train stations
     */
    private static void removeDuplication(List<List<String>> connections) {
        Set<List<String>> set = new LinkedHashSet<>(connections);
        connections.clear();
        connections.addAll(set);
//        return connections;
    }

    private static boolean hasEmptyList(List<List<String>> connections) {
        return connections.stream().anyMatch(List::isEmpty);
    }

    // Find all intersection sets between the maxList and other lists
    private static List<List<String>> findCommonStationsTrainLines(List<List<String>> connections, List<String> pivot) {
        List<List<String>> intersections = new ArrayList<>();
        for (List<String> connection : connections) {
            List<String> copy = new ArrayList<>(connection);
            copy.retainAll(pivot);
            intersections.add(copy);
        }
        return intersections;
    }

    // find the list with max number of elements and remove it form 2d list.
    private static List<String> findMaxList(List<List<String>> connections) {
        List<String> maxList = connections.stream().max(Comparator.comparingInt(List::size)).orElse(null);
        connections.remove(maxList);
        return maxList;
    }



//    public static void removeConnectionsWithMostRepeatedStation(List<List<String>> connections) {
//        // find the most repeated station
//        String mostRepeatedStation = findMostRepeatedStation(connections);
//        // remove all sublists that contain it
//        connections.removeIf(sublist -> sublist.contains(mostRepeatedStation));
//    }

    private static Map.Entry<String, Long> findMostRepeatedStation(List<List<String>> connections) {
        // store the result of the max operation in a variable
        Optional<Map.Entry<String, Long>> mostRepeatedEntry = connections.stream().flatMap(List::stream).collect(Collectors.groupingBy (Function.identity (),
                Collectors.counting ())).entrySet().stream().max(Map.Entry.comparingByValue ());
        // return the variable or null if not present
        return mostRepeatedEntry.orElse(null);
    }
//TODO
// Step 1: remove all duplication of sublists in 2 D List. Done
// Step 2: find the max sublist from 2D List and remove it from the 2D List.
// Step 3: find the intersection between the max sublist and other sublists of 2D List.
// Step 4: find the most repeated element in the intersections.
// Step 5: find  max sublist from the last Intersection
// Step 6: find the (second)intersection between the most recent max sublist and the most recent Intersection.
// Step 7: find the next (second) max sublist from the original 2D List.
// Step 8: find the new intersection the second max sublist and second intersection.
// step 9: repeat the Step 4 regarding to the intersection of step 8.

    private List<String> findMostCommonStation(List<List<String>> connections) {
        boolean hasRepeatedStation = true;
        List<String> result = new ArrayList<>();
        while (hasRepeatedStation) {
            List<String> maxTrainline = findMaxList(connections);
            // find the common stations between the max sublist and other sublists
            List<List<String>> intersections = findCommonStationsTrainLines(connections, maxTrainline);
            Map.Entry<String, Long> mostRepeatedStation = findMostRepeatedStation(intersections);
            if (mostRepeatedStation != null && mostRepeatedStation.getValue() >= 1) {
                result.add(mostRepeatedStation.getKey());

            } else {
                hasRepeatedStation = false;
            }
        }
        return result;
    }

    private static List<String> findPopularRoute(List<List<String>> connections) {
        boolean done = false;

        // Initialize a variable for the last intersection
        List<List<String>> lastIntersection = new ArrayList<>();

        // Initialize a list for the result
        List<String> result = new ArrayList<>();

        // Loop until the flag is true
        while (!done) {
            // Perform your steps here
            List<String> maxList = findMaxList(connections);
            List<List<String>> intersections = findCommonStationsTrainLines(connections, maxList);
            Map.Entry<String, Long> mostRepeated = findMostRepeatedStation(intersections);
            if (mostRepeated == null) {
                break;
            }
            if (mostRepeated.getValue() >= 1 && !result.contains(mostRepeated.getKey()))
                result.add(mostRepeated.getKey());

            // Check if there are more connections to process
            if (connections.isEmpty()) {
                // If not, set the flag to true
                done = true;
            } else {
                // If yes, update the connections and intersections
                List<List<String>> newConnections = findCommonStationsTrainLines(intersections, findMaxList(intersections));
                maxList = findMaxList(connections);
                lastIntersection = findCommonStationsTrainLines(newConnections, maxList);
            }
            if (!lastIntersection.isEmpty() &&
                    findMostRepeatedStation(lastIntersection) != null) {
                // Add it to the result list
                Map.Entry<String, Long> lastMostRepeated =  findMostRepeatedStation(lastIntersection);
                if (lastMostRepeated.getValue() >= 1 && !result.contains(lastMostRepeated.getKey())) result.add(lastMostRepeated.getKey());
            }
        }

        // Use the last intersection here if needed


        // Return the result list
        return result;
    }


//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//    rul 1
//    private static void removeStationDuplication(List<List<String>> connections) {
//        connections.replaceAll(connection -> connection.stream().distinct().toList());
//    }

    private static List<List<String>> removeStationDuplication(List<List<String>> inputList) {
        Map<String, Long> stringCounts = inputList.stream()
                .flatMap(List::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        Set<String> duplicates = stringCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return inputList.stream()
                .map(sublist -> sublist.stream()
                        .filter(str -> !duplicates.contains(str) || sublist.lastIndexOf(str) == sublist.size() - 1)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

//    private static List<List<String>> removeDuplicates(List<List<String>> list) {
//
//
//
//    }

    private static List<String> maxIntersection(List<List<String>> connections) {
        return connections.stream()
                .flatMap(l1 -> connections.stream().map(l2 -> {
                    Set<String> intersection =  new HashSet<>(l1);
                    intersection.retainAll(l2);
                    return intersection;
                }))
                .max(Comparator.comparingInt(Set::size))
                .orElse(Set.of()).stream().toList();

    }
//-------------------------------------------------------------------------------------------------
//    Reduction2
    public static List<Map.Entry<String, String>> convertConnectionsToLinePairs(List<List<String>> connections) {
        // create a stream of sublists from the 2D List
        Stream<List<String>> stream = connections.stream ();
        // collect the stream into a list of Map.Entry
        // return the result list
        return stream
                // flatten each sublist into a stream of pairs of adjacent elements
                .flatMap (sublist -> IntStream.range (0, sublist.size () - 1)
                        // map each pair to a Map.Entry
                        .mapToObj (i -> new AbstractMap.SimpleEntry<> (sublist.get (i), sublist.get (i + 1))))
                // collect the pairs into a list
                .collect (Collectors.toList ());
    }

    public static Map.Entry<String, String> findMostRepeatedEntry(List<Map.Entry<String, String>> list) {
        // create a stream of entries from the list
        Stream<Map.Entry<String, String>> stream = list.stream ();
        // collect the stream into a map of entries to their frequency
        Map<Map.Entry<String, String>, Long> frequencyMap = stream
                // group the entries by their key and value
                .collect (Collectors.groupingBy (
                        Function.identity (),
                        // count the frequency of each group
                        Collectors.counting ()
                ));
        // return the entry with the maximum frequency
        return frequencyMap.entrySet ().stream ()
                // compare the entries by their frequency
                .max (Map.Entry.comparingByValue ())
                // get the entry or null if the list is empty
                .map (Map.Entry::getKey)
                .orElse (null);
    }

//--------------------------------------------------------------------------------------------------------------------
//    Reduction 3

//    private static void thirdReduceConnections(List<List<String>> connections) {
//        connections.forEach(connection -> findAndRemoveSubsets(connections,connection));
//    }
//    private static void findAndRemoveSubsets(List<List<String>> list, List<String> sublist) {
//        List<List<String>> result = list.stream()
//                .filter(l -> new HashSet<>(l).containsAll(sublist)).toList();
//        // Remove the result list from the original list
//        list.removeAll(result);
//    }
private static void removeSuperSets(List<List<String>> list) {
    List<List<String>> copy = new ArrayList<>(list);

    for (List<String> subset : copy) {
        for (List<String> superset : list) {
            if (new HashSet<>(superset).containsAll(subset) && !subset.equals(superset)) {
                list.remove(superset);
                break;
            }
        }
    }
}




//    public static String findAndRemoveMostRepeatedEntry(List<Map.Entry<String, String>> list) {
//        // create a stream of entries from the list
//        Stream<Map.Entry<String, String>> stream = list.stream ();
//        // collect the stream into a map of entries to their frequency
//        Map<Map.Entry<String, String>, Long> frequencyMap = stream
//                // group the entries by their key and value
//                .collect (Collectors.groupingBy (
//                        Function.identity (),
//                        // count the frequency of each group
//                        Collectors.counting ()
//                ));
//        // find the entry with the maximum frequency
//        Optional<Map.Entry<String, String>> mostRepeatedEntry = frequencyMap.entrySet ().stream ()
//                // compare the entries by their frequency
//                .max (Map.Entry.comparingByValue ())
//                // get the entry or empty if the list is empty
//                .map (Map.Entry::getKey);
//        // remove the most repeated entry from the list if present
//        mostRepeatedEntry.ifPresent (list::remove);
//        // return the most repeated entry or null if the list is empty
//        return mostRepeatedEntry.orElse(null) != null ? mostRepeatedEntry.orElse(null).getKey() : null;
//    }

//    public static String findAndRemoveMostRepeatedEntry(List<Map.Entry<String, String>> list) {
//        // create a stream of entries from the list
//        Stream<Map.Entry<String, String>> stream = list.stream();
//        // collect the stream into a map of entries to their frequency
//        Map<Map.Entry<String, String>, Long> frequencyMap = stream
//                // group the entries by their key and value
//                .collect(Collectors.groupingBy(
//                        Function.identity(),
//                        // count the frequency of each group
//                        Collectors.counting()
//                ));
//        // find the entry with the maximum frequency
//        Optional<Map.Entry<Map.Entry<String, String>, Long>> mostRepeatedEntry = frequencyMap.entrySet().stream()
//                // compare the entries by their frequency
//                .max(Map.Entry.comparingByValue());
//        if (mostRepeatedEntry.isPresent()) {
//            Map.Entry<Map.Entry<String, String>, Long> entry = mostRepeatedEntry.get();
//            long frequency = entry.getValue();
//            System.out.println("The most repeated entry appears " + frequency + " times.");
//            // remove the most repeated entry from the list
//            list.remove(entry.getKey());
//            // return the most repeated entry's key
//            return entry.getKey().getKey();
//        } else {
//            System.out.println("The list is empty.");
//            return null;
//        }
//    }
    //This for reduction2
    public static Map.Entry<String, Long> removeMostRepeatedTrainLine(List<Map.Entry<String, String>> list) {
        // create a stream of entries from the list
        Stream<Map.Entry<String, String>> stream = list.stream ();
        // collect the stream into a map of entries to their frequency
        Map<Map.Entry<String, String>, Long> frequencyMap = stream
                // group the entries by their key and value
                .collect (Collectors.groupingBy (
                        Function.identity (),
                        // count the frequency of each group
                        Collectors.counting ()
                ));
        // find the entry with the maximum frequency
        Optional<Map.Entry<Map.Entry<String, String>, Long>> mostRepeatedEntry = frequencyMap.entrySet ().stream ()
                // compare the entries by their frequency
                .max (Map.Entry.comparingByValue ());
        // remove the most repeated entry from the list if present
        mostRepeatedEntry.ifPresent (entry -> list.remove (entry.getKey ()));
        // return a new Map.Entry with the key of the most repeated pair and the number of repetition as the value or null if the list is empty
        return mostRepeatedEntry.map (entry -> new AbstractMap.SimpleEntry<> (entry.getKey ().getKey (), entry.getValue ())).orElse (null);
    }


    //    public static Map.Entry<String, String> findAndRemoveMostRepeatedEntry(List<Map.Entry<String, String>> list) {
//        // create a stream of entries from the list
//        Stream<Map.Entry<String, String>> stream = list.stream ();
//        // collect the stream into a map of entries to their frequency
//        Map<Map.Entry<String, String>, Long> frequencyMap = stream
//                // group the entries by their key and value
//                .collect (Collectors.groupingBy (
//                        Function.identity (),
//                        // count the frequency of each group
//                        Collectors.counting ()
//                ));
//        // find the entry with the maximum frequency
//        Optional<Map.Entry<String, String>> mostRepeatedEntry = frequencyMap.entrySet ().stream ()
//                // compare the entries by their frequency
//                .max (Map.Entry.comparingByValue ())
//                // get the entry or empty if the list is empty
//                .map (Map.Entry::getKey);
//        // set the value of the entry before the most repeated entry in the list to the value of the most repeated entry if present and not at index 0
//        mostRepeatedEntry.ifPresent (entry -> {
//            // get the index of the most repeated entry in the list
//            int index = list.indexOf (entry);
//            // check if the index is valid and not at the beginning
//            if (index > 0 && index < list.size ()) {
//                // get the key and value of the entry before it in the list
//                String previousKey = list.get (index - 1).getKey ();
//                String previousValue = list.get (index - 1).getValue ();
//                // set the value of the entry before it to the value of the most repeated entry
//                list.set (index - 1, new AbstractMap.SimpleEntry<> (previousKey, entry.getValue ()));
//            }
//        });
//        // remove the most repeated entry from the list if present
//        mostRepeatedEntry.ifPresent (list::remove);
//        // return the most repeated entry or null if the list is empty
//        return mostRepeatedEntry.orElse (null);
//    }
//----------------------------------------------------------------------------------------------------------------------
    public static List<List<String>> removeIntersectionElements(List<List<String>> list) {
        // find the intersection sets of the sublists
        List<Set<String>> intersections = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                // create a set from the first sublist
                Set<String> set1 = new HashSet<>(list.get(i));
                // retain only the elements that are also in the second sublist
                set1.retainAll(list.get(j));
                // check if the set is not empty and not already in the intersections list
                if (!set1.isEmpty() && !intersections.contains(set1)) {
                    // add the set to the intersections list
                    intersections.add(set1);
                }
            }
        }

        // create a stream from the list
        Stream<List<String>> stream = list.stream();

        // create a predicate that checks if an element belongs to any intersection set
        Predicate<String> isInIntersection = s -> intersections.stream().anyMatch(set -> set.contains(s));

        // create a function that returns the last element of an intersection set that contains an element
        Function<String, String> lastOfIntersection = s -> intersections.stream()
                .filter(set -> set.contains(s))
                .map(set -> set.toArray()[set.size() - 1].toString())
                .findFirst()
                .orElse(s);

        // split the stream into two streams, one for the elements that belong to the intersection sets, and one for the elements that do not
        Map<Boolean, Stream<List<String>>> partitioned = stream.collect(Collectors.partitioningBy(
                        sublist -> sublist.stream().anyMatch(isInIntersection),
                        Collectors.toUnmodifiableList()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stream()));

        // apply a filter and a collector to each stream
        Stream<List<String>> filtered = partitioned.get(true).map(sublist -> sublist.stream()
                .filter(s -> s.equals(lastOfIntersection.apply(s)))
                .collect(Collectors.toList()));
        Stream<List<String>> unfiltered = partitioned.get(false);

        // combine the two streams back into a single list

        // return the result
        return Stream.concat(filtered, unfiltered).collect(Collectors.toList());
    }


    private static List<List<String>> removeDuplicates(List<List<String>> list) {
        List<List<String>> newList = new ArrayList<>();
        for (List<String> element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    private static List<LinkedList<String>> reduction2(List<List<String>> connections) {
//        return connections.stream().map(LinkedList::new).collect(Collectors.toList());
        return connections.stream().map(LinkedList::new).toList();
    }

    private static void TestArrayList() {
        List<Integer> arrayList = new ArrayList<>();
        arrayList.add(1); // 1 is autoboxed to new Integer(1)
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(1);
        arrayList.add(4);
        arrayList.add(0, 10);
        arrayList.add(3, 30);
        System.out.println(arrayList);
        System.out.println(new LinkedList<Integer>(arrayList));
    }







}
