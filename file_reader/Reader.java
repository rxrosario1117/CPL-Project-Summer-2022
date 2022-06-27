package file_reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {

//    String fileText;

    public String readAFile(String fileName) {

//        fileText = "";
//        fileText = new String(Files.readAllBytes(Paths.get(fileName)));

        try{

            return new String(Files.readAllBytes(Paths.get(fileName)));

        }catch (IOException e) {

            System.out.println("File: " + fileName + " not found");

        }

        return "";

    }

////    Test readAFile
//    public static void main(String[] args) {
//
//        Reader reader = new Reader();
//
//        String file = reader.readAFile("test.txt");
//
//        System.out.println(file);
//
//    }


}
