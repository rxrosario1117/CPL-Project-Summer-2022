package user_exceptions;

public class ClosedParenthesisMissingException extends Exception{

    public ClosedParenthesisMissingException(){
        super("ClosedParenthesisMissingException: Right parenthesis after function name is missing.");
    }
}
