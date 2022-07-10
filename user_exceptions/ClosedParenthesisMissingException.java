/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, ClosedParenthesisMissingException.java
 */

package user_exceptions;

public class ClosedParenthesisMissingException extends Exception{

    public ClosedParenthesisMissingException(){
        super("ClosedParenthesisMissingException: Right parenthesis after function name is missing.");
    }
}
