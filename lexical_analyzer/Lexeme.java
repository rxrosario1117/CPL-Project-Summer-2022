package lexical_analyzer;

public class Lexeme {

    String lexeme, token;

    Lexeme(String newLexeme){

        switch(newLexeme){

            case "<=": {
                lexeme = newLexeme;
                token = "le_operator";
                break;
            }

            case "<": {
                lexeme = newLexeme;
                token = "lt_operator";
                break;
            }

            case ">=": {
                lexeme = newLexeme;
                token = "ge_operator";
                break;
            }

            case ">": {
                lexeme = newLexeme;
                token = "gt_operator";
                break;
            }

            case "==": {
                lexeme = newLexeme;
                token = "eq_operator";
                break;
            }

            case "~=": {
                lexeme = newLexeme;
                token = "ne_operator";
                break;
            }

            case "+": {
                lexeme = newLexeme;
                token = "add_operator";
                break;
            }

            case "-": {
                lexeme = newLexeme;
                token = "sub_operator";
                break;
            }

            case "*": {
                lexeme = newLexeme;
                token = "mul_operator";
                break;
            }

            case "/": {
                lexeme = newLexeme;
                token = "div_operator";
                break;
            }
            default: {
                isParentheses(newLexeme);
            }
        }

    }

    void isParentheses(String newLexeme){
        switch (newLexeme) {
            case "(" -> {
                lexeme = newLexeme;
                token = "OPEN_PARENTHESIS";
                break;
            }
            case ")" -> {
                lexeme = newLexeme;
                token = "CLOSED_PARENTHESIS";
                break;
            }
        }
    }

}
