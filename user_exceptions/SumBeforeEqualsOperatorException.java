/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, SumBeforeEqualsOperatorException.java
 */



package user_exceptions;

public class SumBeforeEqualsOperatorException extends Exception{

    public SumBeforeEqualsOperatorException(){
        super("SumBeforeEqualsOperatorException: Sum operator (+) detected before equals operator (=). The \"+=\" does not exist");
    }
}
