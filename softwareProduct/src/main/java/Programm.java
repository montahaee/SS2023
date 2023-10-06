import IO.*;
import exception.FileAccessException;
import exception.InvalidCommandLineArgumentsException;
import framework.CommandLineArguments;
import framework.CommandLineArgumentsParser;
import framework.Handleable;
import minimizer.ServiceStationMinimizer;

import java.time.LocalDateTime;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This programm minimize the number of the train service station and
 * gives the best optional station to this end.
 * <P>
 * It works with input data in the form of .in files. Data is provided
 * in regular intervals of 0.05 seconds. Multiple Factors influence the
 * duration of processing and outputting the data, therefore concurrency
 * was implemented in the form of three Threads.
 * <P>
 * <U>The user will parse the File or Directory path when invoking the programm with command prompt.</U>
 */
public class Programm {

    public static void main(String[] args) {
        String source;
        System.out.println("Start of Program: " + LocalDateTime.now());

        try {
            System.out.println(System.lineSeparator());
            System.out.println("Reading Command Line Arguments");
            CommandLineArguments arguments = CommandLineArgumentsParser.parse(args);
            source = arguments.getSourceFile();
            System.out.println("Command Line Arguments read successfully");

            DataStream<TrainConnectionJob> connectionsDataQueue = new DataStream<>();
            FileSupplier producer = new FileSupplier(source, connectionsDataQueue);

            LinkedBlockingQueue<Handleable.Data<TrainConnectionJob, Minimization>> handleQueue = new LinkedBlockingQueue<>() ;
            ServiceStationMinimizer serviceStationMinimizer = new ServiceStationMinimizer(connectionsDataQueue, handleQueue);

            FileConsumer consumer = new FileConsumer(handleQueue);

            Thread reading = new Thread(producer,"Reading input data");
            Thread processing = new Thread(serviceStationMinimizer,"Processing of data");
            Thread writing = new Thread(consumer,"Writing the output data");

            reading.start();
            processing.start();
            writing.start();

        } catch (InvalidCommandLineArgumentsException | FileAccessException e) {
            System.err.println(e.getMessage());
        } finally {
            System.out.println(System.lineSeparator());
            System.out.println("End of Program: " + LocalDateTime.now());

        }

    }
}
