package lexical_analyzer;

import file_reader.Reader;

import java.util.Scanner;

public class Lexer {

    public static String fileText;
    // counter to track the fileText char that's being looked at
    public static int lexLength = 0;
    public static Reader reader = new Reader();

    public static String doLexing(){

        fileText = reader.readAFile("test1.txt");

//        Array to hold the julia program
        char[] arr = fileText.toCharArray();

        int counter = lexLength;
        for(int i = 0; i < arr.length; i++) {
            if(Character.isLetter(arr[lexLength])){
                lexLength++;
            }
            else if(arr[lexLength] == ' '){
                lexLength++;
                return fileText.substring(counter, lexLength);
            }
        }

        return fileText.substring(counter, lexLength);

    }

//    returns the next keyword or symbol
//    public static String getNext(){
//
////        Array to hold the julia program
//        char[] arr = fileText.toCharArray();
//
//        int counter = lexLength;
//        for(int i = 0; i < arr.length; i++) {
//            if(Character.isLetter(arr[lexLength])){
//                lexLength++;
//            }
//            else if(arr[lexLength] == ' '){
//                lexLength++;
//                return fileText.substring(counter, lexLength);
//            }
//        }
//
//        return fileText.substring(counter, lexLength);
//    }

    public static void main(String[] args) {

        doLexing();
        doLexing();
        doLexing();
    }


}
