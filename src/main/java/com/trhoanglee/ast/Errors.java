package com.trhoanglee.ast;

class Errors {
    static void fatal(int lineNum, int charNum, String msg) {
        System.err.println("At line: " + lineNum + ", column: " + charNum
                + " **ERROR** " + msg);
        FACTAL_ERROR = true;
    }

    static void warn(int lineNum, int charNum, String msg) {
        System.err.println("At line: " + lineNum + ", column: " + charNum
                + " **WARNING** " + msg);
    }

    static boolean FACTAL_ERROR = false;
}
