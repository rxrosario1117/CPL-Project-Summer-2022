/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, OpenParenthesisMissingException.java
 */

package user_exceptions;

public class OpenParenthesisMissingException extends Exception{

    public OpenParenthesisMissingException(){
        super("OpenParenthesisMissingException: Left parenthesis after function name is missing.");
    }
}
