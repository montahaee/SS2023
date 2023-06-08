package minimizer;

import IO.DataStream;
import IO.TrainConnectionJob;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
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
        List<List<String>> list_1 = new ArrayList<>();
        list_1.add(List.of("HH","H","B","L"));
        list_1.add(List.of("K","FFM","S"));
        list_1.add(List.of("HH","H","FFM","N","M"));
        list_1.add(List.of("H","FFM","N"));
        list_1.add(List.of("DA","FFM","N"));
        list_1.add(List.of("HH","FFM","M"));
        Path path = Paths.get("src/test/resource/ihk_beispiel_1.in");
        TrainConnectionJob connectionJob = new TrainConnectionJob(path.toString(), list_1);

        List<String> expected = new LinkedList<> (Arrays.asList("FFM","H"));

        assertEquals(expected, minimizer.handle(connectionJob).stations());
    }

    @Test
    void testSecondReduction() {
        //*****************************************************************************

//      The source path for the 3rd example in the task sheet.
        Path source3 = Paths.get("src/test/resource/ihk_beispiel_3.in");

//      matrixwaise input for the test of 3rd example in the task sheet.
        List<List<String>> input_2 =  new LinkedList<>();
        input_2.add(new LinkedList<>(List.of("S","DA","H","K")));
        input_2.add(new LinkedList<>(List.of("FFM","DA","H","K")));
        input_2.add(new LinkedList<>(List.of("M","DA","H","B")));
        input_2.add(new LinkedList<>(List.of("C","M","H","E")));
//      expected_2 for the test for 3rd example in the task sheet.
        List<List<String>> expected_2 = new ArrayList<>();
        expected_2.add(List.of("S","H","K"));
        expected_2.add(List.of("FFM","H","K"));
        expected_2.add(List.of("M","H","B"));
        expected_2.add(List.of("C","M","H","E"));

        minimizer.TestSecondReduction(input_2);
        assertEquals(expected_2, input_2);
//      *******************************************************************************

//      more possibility to reduce  than input in the test for 3rd example in the task sheet.
        List<List<String>> input_2_1 =  new LinkedList<>();
        input_2_1.add(new LinkedList<>(List.of("S","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("FFM","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("M","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("C","M","E","K")));

//      expected value for the test an example inspired by the last example(expected_2)
        List<List<String>> expected_2_1 = new ArrayList<>();
        expected_2_1.add(List.of("S","K"));
        expected_2_1.add(List.of("FFM","K"));
        expected_2_1.add(List.of("M","K"));
        expected_2_1.add(List.of("C","M","E","K"));

        minimizer.TestSecondReduction(input_2_1);
        assertEquals(expected_2_1, input_2_1);
    }

    @Test
    void testThirdReduction() {

//      matrixwaise input of the test for 4th example in the task sheet.
        List<List<String>> input_3 =  new LinkedList<>();
        input_3.add(new LinkedList<>(List.of("S","DA","H","K","M")));
        input_3.add(new LinkedList<>(List.of("DA","H")));
        input_3.add(new LinkedList<>(List.of("M","N","DA","B")));
        input_3.add(new LinkedList<>(List.of("C","M","E")));
//      expected_2 for the test for 3rd example in the task sheet.
        List<List<String>> expected_3 = new ArrayList<>();
        expected_3.add(List.of("DA","H"));
        expected_3.add(List.of("M","N","DA","B"));
        expected_3.add(List.of("C","M","E"));

        minimizer.TestThirdReduction(input_3);
        assertEquals(expected_3, input_3);

        List<List<String>> input_3_1 =  new LinkedList<>();
        input_3_1.add(new LinkedList<>(List.of("S","DA","H","K","M")));
        input_3_1.add(new LinkedList<>(List.of("DA","H","K")));
        input_3_1.add(new LinkedList<>(List.of("DA","H")));
        input_3_1.add(new LinkedList<>(List.of("M","DA","H","B")));
        input_3_1.add(new LinkedList<>(List.of("C","M","H","E")));

        List<List<String>> expected_3_1 = new ArrayList<>();
        expected_3_1.add(List.of("DA","H"));
        expected_3_1.add(List.of("M","DA","H","B"));
        expected_3_1.add(List.of("C","M","H","E"));

        minimizer.TestThirdReduction(input_3_1);
        assertEquals(expected_3_1, input_3_1);
    }



}