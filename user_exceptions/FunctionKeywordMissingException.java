package user_exceptions;

public class FunctionKeywordMissingException extends Exception{

    public FunctionKeywordMissingException(){
        super("--- FunctionKeywordMissingException: To begin a function in Julia, the FUNCTION keyword must be utilized ---");
    }
}
