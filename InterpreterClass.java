import java.util.ArrayList;
import java.util.Objects;

public class InterpreterClass {

    String theProgram;

    String functionId;

    ArrayList<String> errorsList = new ArrayList<>();

    int counter = 0;
    int endOfFile;

//    ArrayLists to hold variable name and value
    ArrayList<Character> varName = new ArrayList<>();
    ArrayList<Integer> varValue = new ArrayList<>();

    InterpreterClass(String theProgram, ArrayList<String> errorList){

        this.theProgram = theProgram;
        this.errorsList = errorList;
    }

    void interpret(){

        System.out.println("=========================================================");
        System.out.println("--------------------Interpretation-----------------------");
        System.out.println("=========================================================\n");

        endOfFile = theProgram.length() - 3;

        //        halts interpreting if the parser sends error and prints errors
        if (theProgram.equals("error")) {

            for (String s : errorsList) {
                System.out.println(s);
            }
        }
        else {

            while (counter < endOfFile) {

//              sets the functionId to the proper value
                if (isFunctionCall()) {

                    setFunctionId(counter);
                }

//                assigns values to variables
                if (isAsgmtStmt()) {
                    assignValue(counter);
                }

//                prints value
                if (isPrintStmt()) {
                    printValue(counter);
                }

//                works through a do while loop
                if (isWhile(counter)) {
                    System.out.println("- Start of WHILE loop");

                    doWhile(counter);
                }

//                works through an if stmt
                if (isIfStmt(counter)) {
                    System.out.println("- Start of IF statement");

                    ifStmt(counter);
                }

//            indicates the end of the interpreter
                if (isEndOfFile(counter)) {
                    System.out.println("- ...End of Interpreter");
                }

                counter++;
            }
        }

    }

    boolean isFunctionCall(){
        return theProgram.charAt(counter + 2) == '(' && theProgram.charAt(counter + 4) == ')';
    }

    void setFunctionId(int index){
        functionId = String.valueOf(theProgram.charAt(index));

        System.out.println("- Function \"" + functionId + "\" has been called");
    }

    boolean isAsgmtStmt(){

        return theProgram.charAt(counter) == '=' && theProgram.charAt(counter - 1) == ' ' && theProgram.charAt(counter + 1) == ' ';
    }

//    assigns variable to its corresponding value
    void assignValue(int index){

        varName.add(theProgram.charAt(index - 2));

        int varVal = Integer.parseInt(String.valueOf(theProgram.charAt(index + 2)));

        varValue.add(varVal);

        System.out.println("- Variable \"" + theProgram.charAt(index - 2) + "\" has been assigned to " + varVal);

    }

    boolean isPrintStmt(){

        return theProgram.startsWith("print", counter);
    }

    void printValue(int index) {

//        checks to see the variable name matches the one from the array list and prints the correct value
        for(int i = 0; i < varName.size(); i++){
//            (index + 8) pushes past the "print(" to get to the variable between the parenthesis
            if (varName.get(i) == theProgram.charAt(index + 8)){
                System.out.println("- Printed to console: " + varValue.get(i));
            }
        }
    }

    boolean isWhile(int index){

        return theProgram.startsWith("while", counter);
    }

    void doWhile(int index){

//        pushes counter past "while" to get the parts of the boolean expression
        String boolExpr = String.valueOf(theProgram.charAt(index + 6));
        String leftOfBool = String.valueOf(theProgram.charAt(index + 8));
        String rightOfBool = String.valueOf(theProgram.charAt(index + 10));
        int whileCounter = 0;

        int endOfWhile = findEndOfWhile(index);

//        while (x < 4)
        while (whileCounter < endOfWhile) {

//            assigns values to variables
            if (isAsgmtStmt()) {
                assignValue(counter);
            }

//            prints value
            if (isPrintStmt()) {
                printValue(counter);
            }

//            checks for equality "=="
            if (isEqualityCheck(whileCounter)) {

                String leftOfEquality = String.valueOf(theProgram.charAt(whileCounter + 3));
                String rightOfEquality = String.valueOf(theProgram.charAt(whileCounter + 5));

            }

            whileCounter++;
        }

        System.out.println("- ...End of WHILE loop");

    }

    int findEndOfWhile(int index) {

        for ( ; index < theProgram.length(); index++){
            if(theProgram.charAt(index) == 'e' && theProgram.charAt(index+1) == 'n' && theProgram.charAt(index+2) == 'd') {
                return index;
            }
        }
        return index;
    }

    boolean isEqualityCheck(int index) {

        return theProgram.charAt(index) == '=' && theProgram.charAt(index+1) == '=';
    }

    boolean isIfStmt(int index) {
        return theProgram.charAt(index) == 'i' && theProgram.charAt(index+1) == 'f';
    }

    void ifStmt(int index) {
//        pushes counter past "if" to get the parts of the boolean expression

        String boolExpr = String.valueOf(theProgram.charAt(index + 3)) + theProgram.charAt(index + 4);

        char leftOfBool = theProgram.charAt(index + 6);
        char rightOfBool = theProgram.charAt(index + 8);

        if(getTwoCharBoolExpr(index).equals("~=")){

            int leftVarNameIndex = 0;
            int rightVarNameIndex = 0;

            for (int i = 0; i < varName.size(); i++){

                if (varName.get(i).equals(leftOfBool)) {
                    leftVarNameIndex = i;
                }

                if (varName.get(i).equals(rightOfBool)) {

                }
            }

            if (!Objects.equals(varValue.get(leftVarNameIndex), varValue.get(rightVarNameIndex))) {

            }

        }

        System.out.println(boolExpr);
        System.out.println("Left " + leftOfBool);
        System.out.println("Right " + rightOfBool);
    }

    String getTwoCharBoolExpr(int index) {
        String expr = String.valueOf(theProgram.charAt(index + 3)) + theProgram.charAt(index + 4);

        switch (expr) {
            case "<=": {
                return "<=";
            }

            case ">=": {
                return ">=";
            }

            case "==": {
                return "==";
            }

            case "~=": {
                return "~=";
            }
            default: {
//                If no two character operator is found, then look for a single operator
                getOneCharBoolExpr(index);
            }
        }
        return "";
    }

    String getOneCharBoolExpr(int index) {

        String expr = String.valueOf(theProgram.charAt(index));

        switch (expr) {

            case "<": {
                return "<";
            }
            case ">": {
                return ">";
            }
            case "+": {
                return "+";
            }
            case "-": {
                return "-";
            }
            case "*": {
                return "*";
            }
            case "/": {
                return "/";
            }

        }

        return "";
    }

    private boolean isEndOfFile(int index) {
        return index == endOfFile-1;
    }

}
