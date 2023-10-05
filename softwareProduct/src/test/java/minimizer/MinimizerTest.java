package minimizer;

import IO.DataStream;
import IO.TrainConnectionJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

class MinimizerTest {

    Minimizer minimizer;
    @BeforeEach
    void setUp() {
        minimizer = new Minimizer(new DataStream<>(),new LinkedBlockingQueue<>());
    }

    @Test
    void handle() {

//      A matrixwaise representation for the test the first example in the task sheet.
        List<LinkedList<String>> list_1 = new ArrayList<>();
        list_1.add(new LinkedList<>(List.of("HH","H","B","L")));
        list_1.add(new LinkedList<>(List.of("K","FFM","S")));
        list_1.add(new LinkedList<>(List.of("HH","H","FFM","N","M")));
        list_1.add(new LinkedList<>(List.of("H","FFM","N")));
        list_1.add(new LinkedList<>(List.of("DA","FFM","N")));
        list_1.add(new LinkedList<>(List.of("HH","FFM","M")));
        Path path = Paths.get("src/test/resource/ihk_beispiel_1.in");
        TrainConnectionJob connectionJob = new TrainConnectionJob(path.toString(), list_1);

        Set<String> expected = new LinkedHashSet<> (Arrays.asList("FFM","H"));

        assertEquals(expected, minimizer.handle(connectionJob).stations());
    }

}