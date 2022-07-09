package syntax_analyzer;

import user_exceptions.ClosedParenthesisMissingException;
import user_exceptions.FunctionKeywordMissingException;
import user_exceptions.IdentifierKeywordMissingException;
import user_exceptions.OpenParenthesisMissingException;

import java.util.ArrayList;

import static syntax_analyzer.Keywords.*;

public class Parser {

//    Stores tokens and lexemes from the lexer
    ArrayList<String> tokens;
    ArrayList<String> lexemes;
    String parsingLine = "<program> -> function id ( ) <block> end";
    static int counter = 4;

//    Upon the creation of a Parser object the tokens and lexemes are imported to this class
    public Parser(ArrayList<String> tokens, ArrayList<String> lexemes){

        this.tokens = tokens;
        this.lexemes = lexemes;
    }

    public void parse() {

//        Try catch to ensure the beginning of the Julia file is set up correctly
        try{
            if(!tokens.get(0).equals("FUNCTION")){
                throw new FunctionKeywordMissingException();
            }
            if(!tokens.get(1).equals("IDENTIFIER")){
                throw new IdentifierKeywordMissingException();
            }
            if(!tokens.get(2).equals("OPEN_PARENTHESIS")){
                throw new OpenParenthesisMissingException();
            }
            if(!tokens.get(3).equals("CLOSED_PARENTHESIS")){
                throw new ClosedParenthesisMissingException();
            }

            printParsingLine();

//            Replaces id with the function identifier
            parsingLine = parsingLine.replace("id", lexemes.get(1));

            printParsingLine();

//            replaces the nonterminal "<block>" with <statement> | <statement> <block>
            replaceBlock();

            while(counter < tokens.size()){

//                Works through the <assignment_statement> grammar rule
                if(isAssignmentOperation()){
                    replaceStmt();

                    replaceAsgmtStmt();

                    replaceID();

                    replaceAsgmtOp();

                    replaceArithmeticExpr();

                    replaceLiteralInt();

                    replaceBlock();
                }

//                Works through print stmt
                if(isPrintStmt()){
                    replaceStmt();

                    replacePrintStmt();

                    replaceArithmeticExpr();

                    replaceID();
                }

                counter++;
            }


        } catch (FunctionKeywordMissingException | IdentifierKeywordMissingException | OpenParenthesisMissingException |
                 ClosedParenthesisMissingException e){
            System.out.println(e.getMessage());
        }
    }

    void replaceLiteralInt(){
        parsingLine = parsingLine.replaceFirst("<literal_integer>", lexemes.get(counter));
        System.out.println(parsingLine);
        counter++;
    }

    void replaceID(){
        parsingLine = parsingLine.replaceFirst("id", lexemes.get(counter));
        System.out.println(parsingLine);
        counter++;
    }

    void replaceArithmeticExpr(){
        if(tokens.get(counter).equals(literal_integer)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "<literal_integer>");
        } else if(tokens.get(counter).equals(IDENTIFIER)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "id");
        } else if(tokens.get(counter).equals(add_operator) || tokens.get(counter).equals(sub_operator) || tokens.get(counter).equals(mul_operator) || tokens.get(counter).equals(div_operator)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "<arithmetic_op> <arithmetic_expression> <arithmetic_expression>");
        }

        System.out.println(parsingLine);
    }

    void replaceAsgmtOp(){
        parsingLine = parsingLine.replaceFirst("<assignment_operator>", lexemes.get(counter));
        counter++;

        System.out.println(parsingLine);
    }

    void replaceBlock(){
//        replaces the nonterminal "<block>" with <statement> | <statement> <block>
        parsingLine = parsingLine.replaceFirst("<block>", stmtOrStmtBlock());

        System.out.println(parsingLine);
    }

    boolean isAssignmentOperation(){

        return counter != tokens.size() && tokens.get(counter).equals(IDENTIFIER) && tokens.get(counter + 1).equals(assignment_operator);
    }

    void replaceStmt(){

        if(tokens.get(counter).equals(IDENTIFIER)) {
            parsingLine = parsingLine.replaceFirst("<statement>", "<assignment_statement>");
        } else if(tokens.get(counter).equals(IF)){
            parsingLine = parsingLine.replaceFirst("<statement>", "<if_statement>");
        } else if(tokens.get(counter).equals(WHILE)){
            parsingLine = parsingLine.replaceFirst("<statement>", "<while_statement>");
        }  else if(tokens.get(counter).equals(PRINT)){
            parsingLine = parsingLine.replaceFirst("<statement>", "<print_statement>");
        }  else if(tokens.get(counter).equals(REPEAT)){
            parsingLine = parsingLine.replaceFirst("<statement>", "<repeat_statement>");
        }

        printParsingLine();
    }

    void replaceAsgmtStmt(){
        parsingLine = parsingLine.replaceFirst("<assignment_statement>", "id <assignment_operator> <arithmetic_expression>");

        System.out.println(parsingLine);
    }

    boolean isNextTokenEnd(){

        if(tokens.get(counter+4).equals(END)){
            return true;
        }
        return false;

    }

    String stmtOrStmtBlock(){

        if(isNextTokenEnd()){

            return "<statement>";
        }
        else {
            return "<statement> <block>";
        }
    }

    boolean isPrintStmt(){
        return lexemes.get(counter).equals("print");
    }

    void replacePrintStmt(){
        parsingLine = parsingLine.replaceFirst("<print_statement>", "print ( <arithmetic_expression> )");
        counter += 2;

        System.out.println(parsingLine);
    }

    void printParsingLine(){

        if(counter == 4){
            System.out.println(parsingLine);
        }
        else {

            System.out.println(parsingLine);
        }
    }

    //==========

    boolean isIfStmt(){
        return tokens.get(counter).equals(IF);
    }

    void replaceIfStmt(){
        parsingLine = parsingLine.replaceFirst("<if_statement>", "if <boolean_expression> then <block> else <block> end");

        System.out.println(parsingLine);
    }

    boolean isWhileStmt(){
        return tokens.get(counter).equals(WHILE);
    }

    void replaceWhileStmt() {
        parsingLine = parsingLine.replaceFirst("<while_statement>", "while <boolean_expression> do <block> end");

        System.out.println(parsingLine);
    }

    boolean isRepeatStmt(){
        return tokens.get(counter).equals(REPEAT);
    }

    void replaceRepeatStmt() {
        parsingLine = parsingLine.replaceFirst("<repeat_statement>", "<block> until <boolean_expression>");

        System.out.println(parsingLine);
    }

    boolean isBooleanExpr(){
        return tokens.get(counter).equals(le_operator) || tokens.get(counter).equals(lt_operator) || tokens.get(counter).equals(ge_operator) ||
                tokens.get(counter).equals(gt_operator) || tokens.get(counter).equals(eq_operator) || tokens.get(counter).equals(ne_operator);
    }

    void replaceBoolExpr() {
        parsingLine = parsingLine.replaceFirst("<boolean_expression>", "<relative_op> <arithmetic_expression> <arithmetic_expression>");

        System.out.println(parsingLine);
    }

    void replaceRelative_Op() {
        if(tokens.get(counter).equals(le_operator)){
            parsingLine = parsingLine.replaceFirst("<relative_op>", "le_operator");
        } else if (tokens.get(counter).equals(lt_operator)) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", "lt_operator");
        } else if (tokens.get(counter).equals(ge_operator)) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", "ge_operator");
        } else if (tokens.get(counter).equals(gt_operator)) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", "gt_operator");
        } else if (tokens.get(counter).equals(eq_operator)) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", "eq_operator");
        } else if (tokens.get(counter).equals(ne_operator)) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", "ne_operator");
        }


        System.out.println(parsingLine);
    }


}
