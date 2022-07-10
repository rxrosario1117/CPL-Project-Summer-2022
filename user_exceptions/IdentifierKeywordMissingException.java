/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, IdentifierKeywordMissingException.java
 */

package user_exceptions;

public class IdentifierKeywordMissingException extends Exception{

    public IdentifierKeywordMissingException(){

        super("--- IdentifierKeywordMissingException: Function is not defined ---");
    }
}
