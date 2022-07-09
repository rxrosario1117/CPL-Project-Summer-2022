package user_exceptions;

public class SumBeforeEqualsOperatorException extends Exception{

    public SumBeforeEqualsOperatorException(){
        super("SumBeforeEqualsOperatorException: Sum operator (+) detected before equals operator (=). The \"+=\" does not exist");
    }
}
