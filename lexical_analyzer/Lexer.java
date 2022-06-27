package lexical_analyzer;

import file_reader.Reader;

import java.util.ArrayList;

public class Lexer {

    public Reader reader = new Reader();
    public String fileText = reader.readAFile("test1.jl");
    ArrayList<String> tokens = new ArrayList<>();
    ArrayList<String> lexemes = new ArrayList<>();

    Lexer(){

        fileText = fileText.replace("\r", "");
        fileText = fileText.replace("\t", "");
        fileText = fileText.replace("\n", " ");

        doLexing();

        System.out.println("===========================================");
        System.out.println("Lexeme\t\t\t\t\tTokens");
        System.out.println("-------------------------------------------");

//        prints out the table to show lexemes tokens
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

    public void doLexing(){

        analyzeTextFile();

    }

    public void analyzeTextFile(){

//        Array to hold the julia program
        char[] arr = fileText.toCharArray();

        String currLex = "";

        for(int i = 0; i < arr.length; i++) {

            if(Character.isLetter(arr[i]) || Character.isDigit(arr[i])) {
                currLex += arr[i];
            }
            else{

                if(currLex.length() != 0) {

                    if(Character.isDigit(currLex.charAt(0)) || currLex.charAt(0) == '-'){
                        tokens.add("literal_integer");
                    }
                    else{
                        tokens.add(decipherLetters(currLex));
                    }
                    lexemes.add(currLex);
                    currLex = "";

                }

                if(arr[i] != ' '){

//                    handles negative values
                    if(arr[i] == '-' && Character.isDigit(arr[i+1]) && currLex.length() == 0){
                        currLex += "-";
                    }
                    else{
                        i += checkForOperators(arr, i);
                    }
                }

            }
        }

        tokens.add("END");
        lexemes.add("end");

    }

    String decipherLetters(String currLex){
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
            default:
                return "IDENTIFIER";
        }
    }

    int checkForOperators(char[] arr, int index) {

        String nextLexeme = arr[index] + "" + arr[index + 1];

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
                getSingleOperator(arr[index]);
            }
        }
        return 0;
    }

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
                tokens.add("missing something" + currLex);
            }

        }
    }

}

class Main {

    public static void main(String[] args) {

        Lexer lexer = new Lexer();
    }
}
