package file_reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {

//    String fileText;

    public String readAFile(String fileName) throws IOException {

//        fileText = "";
//        fileText = new String(Files.readAllBytes(Paths.get(fileName)));

        return new String(Files.readAllBytes(Paths.get(fileName)));

    }

//    Test readAFile
    public static void main(String[] args) throws IOException {

        Reader reader = new Reader();

        String file = reader.readAFile("test2.txt");

        System.out.println(file);

    }


}
