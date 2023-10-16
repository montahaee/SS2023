package IO;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;

/**
 * The FilenameUtils class provides utility methods for working with file paths.
 * It is used in the {@link FileSupplier} and {@link FileConsumer} classes.
 */
public class FilenameUtils {

    /**
     * This method generates an output filename based on the given path.
     * It is used in the FileConsumer class.
     *
     * @param path The input file path.
     * @return The output filename.
     * @see FileConsumer
     */
    static String generateOutputFilename(String path) {
        int indexOfChild = path.lastIndexOf(File.separator) +1;
        String outputFilename = path.substring(0, indexOfChild);
        outputFilename += "optimized_";
        outputFilename += path.substring(indexOfChild, path.lastIndexOf("."));
        return outputFilename;
    }

    /**
     * This method checks if a file exists in the same directory as the current file,
     * regardless of its extension. It is used in the FileSupplier class.
     *
     * @param current The path of the file to check.
     * @return True if a file with the same name (ignoring extension) exists, false otherwise.
     * @see FileSupplier
     */
    static boolean isFileExist(@NotNull Path current) {
        File[] listOfFiles = current.getParent().toFile().listFiles();
        for(File file : (listOfFiles != null) ? listOfFiles : new File[0]) {
            if (file.isFile()){

                String filename = file.getAbsolutePath().split("\\.(?=[^.]+$)")[0];
                String filenameWithoutExtension = generateOutputFilename(current.toString());
                if (filename.equalsIgnoreCase(filenameWithoutExtension)
                        || filenameWithoutExtension.equals(filename) ) {
                    return true;
                }
            }
        }
        return false;
    }

}
