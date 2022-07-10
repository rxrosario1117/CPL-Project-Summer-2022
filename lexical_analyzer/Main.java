/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, Main.java
 */

package lexical_analyzer;

import syntax_analyzer.Parser;
import java.util.Scanner;

class Main {

    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Reader reader = new Reader();

        System.out.print("Please enter a file name: ");

        String file = sc.nextLine();

//        String file = "Test3.jl";

        String textFromFile = reader.readAFile(file);

//        Calls the Lexer constructor to begin start the lexical analysis
        Lexer lexer = new Lexer(textFromFile);

        Parser parser = new Parser(lexer.tokens, lexer.lexemes);

        System.out.println();

        parser.parse();
    }
}
