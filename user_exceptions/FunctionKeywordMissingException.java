/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, FunctionKeywordMissingException.java
 */

package user_exceptions;

public class FunctionKeywordMissingException extends Exception{

    public FunctionKeywordMissingException(){
        super("--- FunctionKeywordMissingException: To begin a function in Julia, the FUNCTION keyword must be utilized ---");
    }
}
