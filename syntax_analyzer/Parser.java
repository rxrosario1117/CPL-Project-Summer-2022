/*
 * Class:       CS 4308 Section W02
 * Term:        Summer 2022
 * Name:        Ray Rosario
 * Instructor:  Professor Sharon Perry
 * Project:     Deliverable P2 Parser, Parser.java
 */

package syntax_analyzer;

import user_exceptions.*;

import java.util.ArrayList;

import static syntax_analyzer.Keywords.*;

public class Parser {

//    Stores tokens and lexemes from the lexer
    ArrayList<String> tokens;
    ArrayList<String> lexemes;

//    parsingLine is what is updated everytime a subprogram runs and replaces a nonterminal
    String parsingLine = "<program> -> function id ( ) <block> end";

//    Counter starts at 4 to move passed the first 4 tokens in the array list because they are always the same format
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

            System.out.println("==================================================");
            System.out.println("--------------------Parsing-----------------------");
            System.out.println("==================================================\n");

            printParsingLine();

//            Replaces id with the function identifier
            parsingLine = parsingLine.replace("id", lexemes.get(1));

            printParsingLine();

//            replaces the nonterminal "<block>" with <statement> | <statement> <block>
            replaceBlock();

//            Each subprogram in the while loop has a brief description where that method is defined below
//            While loop to work through the given program
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

//                Works through the <print_statement> grammar rule
                if(isPrintStmt()){
                    replaceStmt();

                    replacePrintStmt();

                    replaceArithmeticExpr();

                    if(tokens.get(counter).equals(literal_integer)){
                        replaceLiteralInt();
                    }
                    else {
                        replaceID();
                    }
                }

//                Works through a while stmt
                if(isWhileStmt()){

                    replaceStmt();

                    replaceWhileStmt();

                    replaceBoolExpr();

                    replaceRelative_Op();

                    replaceArithmeticExpr();

                    replaceID();

                    replaceArithmeticExpr();

                    replaceLiteralInt();

                    replaceBlock();

                    counter++;

                    replaceStmt();

                    replaceAsgmtStmt();

                    replaceID();

                    replaceAsgmtOp();

                    replaceWhileArithExpr();

                    replaceArithmeticExpr();

                    replaceID();

                    replaceLiteralInt();

                    replaceBlock();
                }

//                Work through an if stmt
                if(isIfStmt()){

                    replaceStmt();

                    replaceIfStmt();

                    replaceBoolExpr();

                    replaceRelative_Op();

                    replaceArithmeticExpr();

                    replaceID();

                    replaceArithmeticExpr();

                    replaceLiteralInt();

                    replaceBlock();

                    replaceBlock();

                    replaceBlock();
                }

                counter++;
            }

        } catch (FunctionKeywordMissingException | IdentifierKeywordMissingException | OpenParenthesisMissingException |
                 ClosedParenthesisMissingException | SumBeforeEqualsOperatorException e){
            System.out.println(e.getMessage());
        }
    }

//    Replaces the <literal_integer> non-terminal with non-terminals based on the grammar rules
    void replaceLiteralInt(){
        parsingLine = parsingLine.replaceFirst("<literal_integer>", lexemes.get(counter));
        System.out.println(parsingLine);
        counter++;
    }

//    Replaces the <id> non-terminal with non-terminals based on the grammar rules
    void replaceID(){
        parsingLine = parsingLine.replaceFirst("id", lexemes.get(counter));
        System.out.println(parsingLine);
        counter++;
    }

//    Replaces the <arithmetic_expression> non-terminal with non-terminals based on the grammar rules
    void replaceArithmeticExpr(){

        if(tokens.get(counter).equals(literal_integer)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "<literal_integer>");
        } else if(tokens.get(counter).equals(IDENTIFIER)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "id");
        } else if(tokens.get(counter+1).equals(literal_integer)){
            parsingLine = parsingLine.replaceFirst("end", "<literal_integer>");
        } else if(tokens.get(counter).equals(add_operator) || tokens.get(counter).equals(sub_operator) || tokens.get(counter).equals(mul_operator) || tokens.get(counter).equals(div_operator)){
            parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "<arithmetic_op> <arithmetic_expression> <arithmetic_expression>");
        }

        System.out.println(parsingLine);
    }

//    Handles the boolean expression, identifier, and literal integer that comes after the while keyword
    void replaceWhileArithExpr(){
        parsingLine = parsingLine.replaceFirst("<arithmetic_expression>", "<arithmetic_expression> <literal_integer>");
    }

//    Replaces the <assignment_operator> non-terminal with non-terminals based on the grammar rules
    void replaceAsgmtOp() throws SumBeforeEqualsOperatorException {

        if (lexemes.get(counter).equals("+")) {
            throw new SumBeforeEqualsOperatorException();
        }

        parsingLine = parsingLine.replaceFirst("<assignment_operator>", lexemes.get(counter));
        counter++;

        System.out.println(parsingLine);
    }

//    Check to see whether to replace <block> with "<statement>" or "<statement> <block>"
    String stmtOrStmtBlock(){

        if(isNextTokenEnd() || isNextTokenElse()){

            return "<statement>";
        }
        else {
            return "<statement> <block>";
        }
    }

//    Counter looks 5 indices ahead in the tokens array list to look passed a given print statement
    boolean isNextTokenElse() {

        return tokens.get(counter+5).equals(ELSE);
    }

//    Check to see if the next keyword token "end"
    boolean isNextTokenEnd(){

        if(tokens.get(counter+4).equals(END)){
            return true;
        } else if(tokens.get(counter+5).equals(END)){
            return true;
        } else if(tokens.get(counter+6).equals(END)){
            return true;
        }

        return false;

    }

//    Replaces the <block> non-terminal with non-terminals based on the grammar rules
    void replaceBlock(){

        parsingLine = parsingLine.replaceFirst("<block>", stmtOrStmtBlock());

        System.out.println(parsingLine);
    }

//    Confirms the current token is an assignment operator
    boolean isAssignmentOperation(){

        return counter != tokens.size() && tokens.get(counter).equals(IDENTIFIER) && tokens.get(counter + 1).equals(assignment_operator);
    }

//    Replaces the <statement> non-terminal with non-terminals based on the grammar rules
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

//    Replaces the <assignment_statement> non-terminal with non-terminals based on the grammar rules
    void replaceAsgmtStmt(){
        parsingLine = parsingLine.replaceFirst("<assignment_statement>", "id <assignment_operator> <arithmetic_expression>");

        System.out.println(parsingLine);
    }

//    Confirms the current token is a print statement
    boolean isPrintStmt(){
        return lexemes.get(counter).equals("print");
    }

//    Replaces the <print_statement> non-terminal with non-terminals based on the grammar rules
    void replacePrintStmt(){
        parsingLine = parsingLine.replaceFirst("<print_statement>", "print ( <arithmetic_expression> )");
        counter += 2;

        System.out.println(parsingLine);
    }

//    Prints out the first line of the parser and then every subsequent line that is called
    void printParsingLine(){

        if(counter == 4){

            System.out.println(parsingLine);
        }
        else {

            System.out.println(parsingLine);
        }
    }

//    Confirms the current token is an if statement
    boolean isIfStmt(){
        return tokens.get(counter).equals(IF);
    }

//    Replaces the <if_statement> non-terminal with non-terminals based on the grammar rules
    void replaceIfStmt(){
        parsingLine = parsingLine.replaceFirst("<if_statement>", "if <boolean_expression> then <block> else <block> end");

        System.out.println(parsingLine);

        counter++;
    }

//    Confirms the current token is a while statement
    boolean isWhileStmt(){

        if(tokens.get(counter).equals(WHILE)){
            return true;
        }
        else
            return false;
    }

//    Replaces the <while_statement> non-terminal with non-terminals based on the grammar rules
    void replaceWhileStmt() {
        parsingLine = parsingLine.replaceFirst("<while_statement>", "while <boolean_expression> do <block> end");

        System.out.println(parsingLine);

        counter++;
    }

//    Confirms the current token is a repeat statement
    boolean isRepeatStmt(){
        return tokens.get(counter).equals(REPEAT);
    }

//    Replaces the <repeat_statement> non-terminal with non-terminals based on the grammar rules
    void replaceRepeatStmt() {
        parsingLine = parsingLine.replaceFirst("<repeat_statement>", "<block> until <boolean_expression>");

        System.out.println(parsingLine);

        counter++;
    }

//    Confirms the current token is a boolean expression
    boolean isBooleanExpr(){
        return tokens.get(counter).equals(le_operator) || tokens.get(counter).equals(lt_operator) || tokens.get(counter).equals(ge_operator) ||
                tokens.get(counter).equals(gt_operator) || tokens.get(counter).equals(eq_operator) || tokens.get(counter).equals(ne_operator);
    }

//    Replaces the <boolean_expression> non-terminal with non-terminals based on the grammar rules
    void replaceBoolExpr() {
        parsingLine = parsingLine.replaceFirst("<boolean_expression>", "<relative_op> <arithmetic_expression> <arithmetic_expression>");

        System.out.println(parsingLine);
    }

//    Replaces the <relative_op> non-terminal with non-terminals based on the grammar rules
    void replaceRelative_Op() {

        if(tokens.get(counter).equals("le_operator")){
            parsingLine = parsingLine.replaceFirst("<relative_op>", le_operator);
        } else if (tokens.get(counter).equals("lt_operator")) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", lt_operator);
        } else if (tokens.get(counter).equals("ge_operator")) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", ge_operator);
        } else if (tokens.get(counter).equals("gt_operator")) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", gt_operator);
        } else if (tokens.get(counter).equals("eq_operator")) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", eq_operator);
        } else if (tokens.get(counter).equals("ne_operator")) {
            parsingLine = parsingLine.replaceFirst("<relative_op>", ne_operator);
        }

        System.out.println(parsingLine);
        counter++;
    }

//    Replaces the <arithmetic_op> non-terminal with non-terminals based on the grammar rules
    void replaceArithmeticOp(){

        if(tokens.get(counter).equals("add_operator")){
            parsingLine = parsingLine.replaceFirst("<arithmetic_op>", add_operator);
        } else if(tokens.get(counter).equals("sub_operator")) {
            parsingLine = parsingLine.replaceFirst("<arithmetic_op>", sub_operator);
        } else if(tokens.get(counter).equals("mul_operator")) {
            parsingLine = parsingLine.replaceFirst("<arithmetic_op>", mul_operator);
        } else if(tokens.get(counter).equals("div_operator")) {
            parsingLine = parsingLine.replaceFirst("<arithmetic_op>", div_operator);
        }
    }
}
