/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P1 Lexer, Reader.java
 */

package lexical_analyzer;

import java.io.IOException;
import java.nio.file.*;

public class Reader {

    public String readAFile(String fileName) {

        try{

            return new String(Files.readAllBytes(Paths.get(fileName)));

        }catch (IOException e) {

            System.out.println("File: " + fileName + " not found");

        }

        return "";

    }
}
