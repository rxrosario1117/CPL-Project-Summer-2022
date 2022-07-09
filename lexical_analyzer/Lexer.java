/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P1 Lexer, Lexer.java
 */


package lexical_analyzer;

import java.util.ArrayList;
import java.util.Scanner;


public class Lexer {

//    Scanner to take input from the user
    public Scanner sc = new Scanner(System.in);

//    String to store fileText
    public String fileText;

//    Stores tokens and lexemes in their respective array lists
    ArrayList<String> tokens = new ArrayList<>();
    ArrayList<String> lexemes = new ArrayList<>();

    Lexer(String textFromFile){

        fileText = textFromFile;

//        Removes all unnecessary characters from the file text to be left with spaces only
        fileText = fileText.replace("\r", "");
        fileText = fileText.replace("\t", "");
        fileText = fileText.replace("\n", " ");

        performLexicalAnalysis();

//        prints out the table to show lexemes tokens
        printTable();

    }

    public void performLexicalAnalysis(){

//        Begin searching for tokens and lexemes
        analyzeTextFile();

    }

//    Looks through each character of the file text and identifies the tokens and lexemes and adds them to
//      the token and lexeme array lists respectively
    public void analyzeTextFile(){

//        Array to hold the julia program
        char[] fileTextAsCharArray = fileText.toCharArray();

//        Tracks the current lexeme
        String currLex = "";

//        Identifies the lexeme and adds it to the lexeme array list and adds the respective token to the token array list
        for(int i = 0; i < fileTextAsCharArray.length; i++) {

//            if character is a letter or digit, it's appended onto currLex
            if(Character.isLetter(fileTextAsCharArray[i]) || Character.isDigit(fileTextAsCharArray[i])) {
                currLex += fileTextAsCharArray[i];
            }
            else{

                if(currLex.length() != 0) {

//                    Checks if a number is a negative value
                    if(Character.isDigit(currLex.charAt(0)) || currLex.charAt(0) == '-'){
                        tokens.add("literal_integer");
                    }
//                    Sends the currLex to decipherLetters to find assign the lexeme to the correct token
                    else{
                        tokens.add(assignToken(currLex));
                    }
//                    add the lexeme to the lexeme array list then reset currLex to empty string
                    lexemes.add(currLex);
                    currLex = "";

                }

                if(fileTextAsCharArray[i] != ' '){

//                    handles negative values
                    if(fileTextAsCharArray[i] == '-' && Character.isDigit(fileTextAsCharArray[i+1]) && currLex.length() == 0){
                        currLex += "-";
                    }
                    else{
                        i += checkForOperators(fileTextAsCharArray, i);
                    }
                }

            }
        }

//        Last thing added is the end token and lexeme
        tokens.add("END");
        lexemes.add("end");

    }

//        Switch on currLex to return the token to be added to the tokens array list
    String assignToken(String currLex){

        switch (currLex){
            case "function" : {
                return "FUNCTION";
            }
            case "if" : {
                return "IF";
            }
            case "then" : {
                return "THEN";
            }
            case "else" : {
                return "ELSE";
            }
            case "do" : {
                return "DO";
            }
            case "while" : {
                return "WHILE";
            }
            case "end" : {
                return "END";
            }
            case "print" : {
                return "PRINT";
            }
            default:
                return "IDENTIFIER";
        }
    }

//    Checks for operators with two characters
    int checkForOperators(char[] fileTextAsCharArray, int index) {

//        Strings together the two characters to make the operator
        String nextLexeme = fileTextAsCharArray[index] + "" + fileTextAsCharArray[index + 1];

//        Switch on nextLexeme to return the number of index to skip so there's no double counting of characters
        switch(nextLexeme){

            case "<=": {
                tokens.add("le_operator");
                lexemes.add(nextLexeme);
                return 1;
            }

            case ">=": {
                tokens.add("ge_operator");
                lexemes.add(nextLexeme);
                return 1;
            }

            case "==": {
                tokens.add("eq_operator");
                lexemes.add(nextLexeme);
                return 1;
            }

            case "~=": {
                tokens.add("ne_operator");
                lexemes.add(nextLexeme);
                return 1;
            }
            default: {
//                If no two character operator is found, then look for a single operator
                getSingleOperator(fileTextAsCharArray[index]);
            }
        }
        return 0;
    }

//    Switch on currLex to add corresponding token and lexeme to the tokens and lexemes array lists
    void getSingleOperator(char currLex) {

        switch (currLex) {

            case '<': {
                tokens.add("lt_operator");
                lexemes.add("<");
                break;
            }
            case '>': {
                tokens.add("gt_operator");
                lexemes.add(">");
                break;
            }
            case '+': {
                tokens.add("add_operator");
                lexemes.add("+");
                break;
            }
            case '-': {
                tokens.add("sub_operator");
                lexemes.add("-");
                break;
            }
            case '*': {
                tokens.add("mul_operator");
                lexemes.add("*");
                break;
            }
            case '/': {
                tokens.add("div_operator");
                lexemes.add("/");
                break;
            }
            case '=': {
                tokens.add("assignment_operator");
                lexemes.add("=");
                break;
            }
            case '(': {
                tokens.add("OPEN_PARENTHESIS");
                lexemes.add("(");
                break;
            }
            case ')': {
                tokens.add("CLOSED_PARENTHESIS");
                lexemes.add(")");
                break;
            }
            default: {
                tokens.add("Unrecognized Character: " + currLex);
            }

        }
    }

//        prints out array lists, lexemes and tokens, in a nicely formatted table to show lexemes and tokens
    void printTable(){

        System.out.println("===========================================");
        System.out.println("Lexeme\t\t\t\t\tTokens");
        System.out.println("-------------------------------------------");

        for(int i = 0; i < tokens.size(); i++){
            if (lexemes.get(i).equals("function")){
                System.out.print(lexemes.get(i) + "\t\t\t\t");
            }
            else if(lexemes.get(i).equals("then") || lexemes.get(i).equals("else") || lexemes.get(i).equals("print") || lexemes.get(i).equals("while")){
                System.out.print(lexemes.get(i) + "\t\t\t\t\t");
            }
            else {
                System.out.print(lexemes.get(i) + "\t\t\t\t\t\t");
            }

            if (tokens.get(i).equals("function")){
                System.out.println(tokens.get(i));
            }
            else {
                System.out.println(tokens.get(i));
            }
        }

        System.out.println("Success!");
    }

}

