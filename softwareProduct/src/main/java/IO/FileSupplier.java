package IO;


import exception.FileAccessException;
import exception.IncorrectDataFormatException;
import framework.Suppliable;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

/**
 * FileSupplier is used to simulate providing {@link TrainConnectionJob }
 * data from the file.It implements method {@link #read()} from {@link Suppliable}.
 * <p>
 *  The TrainConnectionJob dat provides methods for reading a file and getting files from a directory.
 * <p>
 *  Notice: <u>The file format of input data has to be textuell!</u>
 */
public class FileSupplier implements Suppliable<TrainConnectionJob>, Runnable {


    private Path sourcePath;
    private final DataStream<TrainConnectionJob> dataStream;
    private final List<Path> filepaths;
    public FileSupplier(String sourcePath, DataStream<TrainConnectionJob> dataStream) throws FileAccessException {
        this.sourcePath = Path.of(sourcePath);
        this.dataStream = dataStream;
        this.filepaths = this.getPaths();
    }

    /**
     * Creates a list of files. Traverses the file-tree if a directory is provided.
     * @return all existed input files in a path to minimize their contents in FileConsumer.
     * @see FileConsumer
     */
    private List<Path> getPaths() throws FileAccessException{
        List<Path> paths = new ArrayList<>();
        if (!Files.exists(sourcePath)) {
            throw new FileAccessException("Filepath does not exist.");
        }

        if(Files.isDirectory(sourcePath)){
            try(Stream<Path> fileTree = Files.walk(sourcePath, 1)){

                fileTree.filter(Files::isRegularFile).forEach(p -> {
                    String fileName = p.getFileName().toString().toLowerCase();
                    if(!fileName.startsWith("optimized_") && !paths.contains(p) ){
                        paths.add(p);
                    }

                });
            }catch (IOException e){
                throw new FileAccessException("Could not walk file tree.", e);
            }
        }else if (Files.isRegularFile(sourcePath)){
            paths.add(sourcePath);
        }

        return paths;
    }

    /**
     *
     * @param current  A path of the file that is looking for to its derivations.
     * @param filenameWithoutExtension A file path which can be derived by #current.
     * @return True if the file independent of its extension derived by  #current.
     */
    private boolean isFileExit(@NotNull Path current, String filenameWithoutExtension) {
        File[] listOfFiles = current.getParent().toFile().listFiles();
        for(File file : listOfFiles != null ? listOfFiles : new File[0]) {
            if (file.isFile()){

                String filename = file.getAbsolutePath().split("\\.(?=[^.]+$)")[0];
                if (filename.equalsIgnoreCase(filenameWithoutExtension)
                        || filenameWithoutExtension.equals(filename) ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Reads data from a file in the {@link #sourcePath} and produces specified element.
     * <p>
     *
     * @throws IncorrectDataFormatException as Data should be formatted correctly in order to location.
     */
    @Override
    public TrainConnectionJob read() throws IncorrectDataFormatException {
        String[] lineContents;
        String massage = "";
        List<LinkedList<String>> connections = new ArrayList<>();
        try(BufferedReader bf = new BufferedReader(Files.newBufferedReader(sourcePath))) {
            String line;
            boolean isDuplicated = false;
            boolean isFirstLine = true;
            String abbreviation = "^[a-zA-Z]\\w*$";
            while ((line = bf.readLine()) != null) {
                if (isFirstLine && !line.startsWith("#")) {
                    System.out.println("We could not find a comment line in file: " + sourcePath.getFileName());
                    System.out.println("Reading File will be processed without comment.");
                    isFirstLine = false;
                } else if (isFirstLine) {
                    isFirstLine = false;
                } else {
                    if (line.contains("#")) { // remove the comment signe and text after that inside the line.
                        line = line.substring(0,line.indexOf("#"));
                    }
                    if (line.isEmpty()) {
                        line = line.replaceAll("\\s", "");
                    }
                    if ((lineContents = line.trim().split(";")).length >= 1) {
                        LinkedList<String> temp = new LinkedList<>();
                        for (String content : lineContents) {
                            if (!content.matches(abbreviation)) {
                                System.out.println("Unexpected token in the input file: " + sourcePath.getFileName());
                                System.out.println("An abbreviation is expected!");
                                System.out.println("Reading line will continue to find an abbreviation!");

                                continue;
                            } else if (temp.contains(content)) {
                                System.out.println(System.lineSeparator());
                                System.out.println("Notice: in the input file: " + sourcePath.getFileName());
                                System.out.println("A duplication is found. The abbreviation '" +
                                content + "' will not be added" + System.lineSeparator() +
                                        "in the train line list (Reduction technic 1!)" );
                                System.out.println(System.lineSeparator());

                                continue;
                            }
                            temp.add(content);
                        }
                        if (connections.contains(temp)) {
                            System.out.println("Duplication! the Train line will not be added in the list!");
                        } else if (!temp.isEmpty()) {
                            connections.add(temp);
                        } else {
//                           readable = false;
                           massage = "No abbreviation found in the input File.";
                           System.out.println(System.lineSeparator());
                           System.out.println(massage.replace(".",": ") + sourcePath.getFileName());
                           continue;
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IncorrectDataFormatException(massage,e);
        }
        TrainConnectionJob connectionJob = new TrainConnectionJob(sourcePath.toString(),connections);
        connectionJob.setErrors(massage);
//        return new TrainConnectionJob(sourcePath.toString(),connections);
        return connectionJob;
    }

    /**
     *
     * @return The path of input source files.
     */
    public Path getSourcePath() {
        return this.sourcePath;
    }

//    public boolean isReadable() {
//        return this.readable;
//    }

    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general process of the method {@code run} is that it may
     * take any action whatsoever.
     * <p>
     * Hir therefor the list of files will be iterated  and invoked the
     * {@link #read()} method. Continues until all input files have
     * been processed meanwhile each processed file is transferred to
     * the DataStream.
     * @see Thread#run()
     * @see DataStream
     */
    @Override
    public void run() {
        Set<Path> processedFiles = new HashSet<>();
        while (processedFiles.size() != filepaths.size()) {

            for (Path path : filepaths) {

                String outputFilename = path.toString().substring(0,
                        path.toString().lastIndexOf("\\")+1);
                outputFilename += "optimized_";
                outputFilename +=  path.toString().substring(path.toString().lastIndexOf("\\")+1,
                        path.toString().lastIndexOf("."));

                if (isFileExit(path,outputFilename)) {
                    processedFiles.add(path);

                    continue;
                }
                try {
                    sourcePath = path;
                    this.dataStream.put(read());
                    Thread.sleep(50);
                } catch (InterruptedException | IncorrectDataFormatException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }

            }
        }
        this.dataStream.put(new TrainConnectionJob());
//        readable = true;
    }
}
