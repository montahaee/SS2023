package IO;

import exception.FileAccessException;
import exception.IncorrectDataFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileSupplierTest {

    private FileSupplier supplier;

    @BeforeEach
    void setUp() {
        Path source1 = Paths.get("src/test/resource/ihk_beispiel_1.in");
        try {
            supplier = new FileSupplier(source1.toString(),new DataStream<>());

        } catch (FileAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void read() {

        /* A matrixwaise representation for the test the first and second example
         in the task sheet.int the file with source2 there is a duplication which
         is commented there.
         */
        List<List<String>> list_1 = new ArrayList<>();
        list_1.add(List.of("HH","H","B","L"));
        list_1.add(List.of("K","FFM","S"));
        list_1.add(List.of("HH","H","FFM","N","M"));
        list_1.add(List.of("H","FFM","N"));
        list_1.add(List.of("DA","FFM","N"));
        list_1.add(List.of("HH","FFM","M"));

//      The source path regarding the second example in the task sheet.
        Path source2 = Paths.get("src/test/resource/ihk_beispiel_2.in");





        try {
//*****************************************************************************

//          TestDrivedeveloping the first source if it will be reade true.
            assertEquals(list_1, supplier.read().getConnections());

//*****************************************************************************

            var reduction1 = new FileSupplier(source2.toString(),new DataStream<>());

        /*
           TestDrivedeveloping the second source to see if it is read as true and Verify that
           the first reduction rule for connections has been correctly applied.
           After running the test you get a massage about the duplication.
                                                                                   .
         */
            assertEquals(supplier.read().getConnections(),reduction1.read().getConnections());


        } catch (IncorrectDataFormatException | FileAccessException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    void getSourcePath() {
        Path newSource = Paths.get("src/test/resource/ihk_beispiel_1.in");
        assertEquals(newSource,supplier.getSourcePath());




    }
}