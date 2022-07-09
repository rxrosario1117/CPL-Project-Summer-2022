package user_exceptions;

public class OpenParenthesisMissingException extends Exception{

    public OpenParenthesisMissingException(){
        super("OpenParenthesisMissingException: Left parenthesis after function name is missing.");
    }
}
