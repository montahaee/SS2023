package IO;

import exception.FileAccessException;
import framework.Consumable;
import framework.Handleable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.InputMismatchException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * FileConsumer takes data and writes it to a  file with ".out" format.
 * <p>
 * The class can be used as thread an implements the {@link #run()} method.
 * It also contains a one parameter method for writing files.
 */
public class FileConsumer implements Consumable<Handleable.Data<TrainConnectionJob, Minimization>>, Runnable {

    private final LinkedBlockingQueue<Handleable.Data<TrainConnectionJob, Minimization>> resultQue;

    /**
     * Creates FileConsumer with a ConcurrentLinedQueue that transfers the data.
     * @param resultQue ConcurrentLinedQueue that holds data
     */
    public FileConsumer(LinkedBlockingQueue<Handleable.Data<TrainConnectionJob, Minimization>> resultQue) {
        this.resultQue = resultQue;
    }


    /**
     * Takes data and writes it to the specified location.
     * <p></p>
     *
     * @param data the data that has to be transferred.
     * @throws FileAccessException as filepath could be incorrect.
     */
    @Override
    public void write(Handleable.Data<TrainConnectionJob, Minimization> data) throws FileAccessException {
        TrainConnectionJob in = data.in;
        Minimization out = data.out;
        // The output files will be saved in the same directory where input file/s is/are.
        String inputPath = in.getFilepath();
        if (inputPath.indexOf(".") <= 0) {
            throw new InputMismatchException("The input data doesn't have extension.");
        }

        String targetPath = FilenameUtils.generateOutputFilename(inputPath);

        StringBuilder sb = new StringBuilder();
        if (out.stations().isEmpty()){
            targetPath += ".err";
            sb.append(in.getError());
        } else {
            targetPath += ".out";
            sb.append("Servicestation in: ");

            out.stations().forEach(station -> sb.append(station).append(";"));
            sb.deleteCharAt(sb.length() - 1);
        }

        Path outputPath = Path.of(targetPath);
        try(BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath)) {
            bufferedWriter.write(sb.toString());
        } catch (IOException e) {
            throw new FileAccessException("Could not write file to path: " + outputPath.toAbsolutePath(), e);
        }
    }

    /**
     * Starts the {@link #write(Handleable.Data)} Method and runs until an empty input is received.
     */
    @Override
    public void run() {
        while (true) {
            try {
                if (resultQue.peek() == null) {
                    continue;
                }
                Handleable.Data<TrainConnectionJob, Minimization> data = this.resultQue.poll();
                if (data.in == null) {
                    break;
                }
                this.write(data);
            } catch (FileAccessException fe) {
                System.err.println(fe.getMessage());
            }
        }
    }
}
