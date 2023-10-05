package minimizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataReducerTest {

    DataReducer reducer;
    @BeforeEach
    void setUp() {
        reducer = new DataReducer();
    }

    @Test
    void secondReduceConnections() {
        //      matrixwaise input_2th for the test of 3rd example in the task sheet.
        List<LinkedList<String>> input_2th =  new LinkedList<>();
        input_2th.add(new LinkedList<>(List.of("S","DA","H","K")));
        input_2th.add(new LinkedList<>(List.of("FFM","DA","H","K")));
        input_2th.add(new LinkedList<>(List.of("M","DA","H","B")));
        input_2th.add(new LinkedList<>(List.of("C","M","H","E")));

        //      expected_2th for the test for 3rd example in the task sheet.
        List<LinkedList<String>> expected_2th = new ArrayList<>();
        expected_2th.add(new LinkedList<>(List.of("S","H","K")));
        expected_2th.add(new LinkedList<>(List.of("FFM","H","K")));
        expected_2th.add(new LinkedList<>(List.of("H","B"))); // I improved the methode along the aime in the second reduction.
        expected_2th.add(new LinkedList<>(List.of("C","H","E")));
        reducer.secondReduceConnections(input_2th);
        assertEquals(expected_2th, input_2th);
//      *******************************************************************************

//      more possibility to reduce  than input_2th in the test for 3rd example in the task sheet.
        List<LinkedList<String>> input_2_1 =  new LinkedList<>();
        input_2_1.add(new LinkedList<>(List.of("S","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("FFM","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("M","DA","H","K")));
        input_2_1.add(new LinkedList<>(List.of("C","M","E","K")));

//      expected_2th value for the test an example inspired by the last example(expected_2)
        List<List<String>> expected_2_1 = new ArrayList<>();
        expected_2_1.add(List.of("S","K"));
        expected_2_1.add(List.of("FFM","K"));
        expected_2_1.add(List.of("M","K"));
        expected_2_1.add(List.of("C","M","E","K"));

        reducer.secondReduceConnections(input_2_1);
        assertEquals(expected_2_1, input_2_1);
    }

    @Test
    void thirdReduceConnections() {

//      matrixwaise input of the test for 4th example in the task sheet.
        List<LinkedList<String>> input_3 =  new LinkedList<>();
        input_3.add(new LinkedList<>(List.of("S","DA","H","K","M")));
        input_3.add(new LinkedList<>(List.of("DA","H")));
        input_3.add(new LinkedList<>(List.of("M","N","DA","B")));
        input_3.add(new LinkedList<>(List.of("C","M","E")));
//      expected_2 for the test for 3rd example in the task sheet.
        List<List<String>> expected_3 = new ArrayList<>();
        expected_3.add(List.of("DA","H"));
        expected_3.add(List.of("M","N","DA","B"));
        expected_3.add(List.of("C","M","E"));

        reducer.thirdReduceConnections(input_3);
        assertEquals(expected_3, input_3);
//      *******************************************************************************

        List<LinkedList<String>> input_3_1 =  new LinkedList<>();
        input_3_1.add(new LinkedList<>(List.of("S","DA","H","K","M")));
        input_3_1.add(new LinkedList<>(List.of("DA","H","K")));
        input_3_1.add(new LinkedList<>(List.of("DA","H")));
        input_3_1.add(new LinkedList<>(List.of("M","DA","H","B")));
        input_3_1.add(new LinkedList<>(List.of("C","M","H","E")));

        List<LinkedList<String>> expected_3_1 = new ArrayList<>();
        expected_3_1.add(new LinkedList<>(List.of("DA","H")));
        expected_3_1.add(new LinkedList<>(List.of("M","DA","H","B")));
        expected_3_1.add(new LinkedList<>(List.of("C","M","H","E")));

        reducer.thirdReduceConnections(input_3_1);
        assertEquals(expected_3_1, input_3_1);
}

    @Test
    void getConnections() {
    }
}