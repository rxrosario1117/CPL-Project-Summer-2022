package user_exceptions;

public class IdentifierKeywordMissingException extends Exception{

    public IdentifierKeywordMissingException(){

        super("--- IdentifierKeywordMissingException: Function is not defined ---");
    }
}
