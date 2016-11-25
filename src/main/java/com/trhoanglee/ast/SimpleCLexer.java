package com.trhoanglee.ast;

import java.io.FileReader;
import java.io.PrintStream;

import java_cup.runtime.Symbol;

/**
 * Simple test driver for the java lexer. Just runs it on some input files and
 * produces debug output. Needs Symbol class from parser.
 */
public class SimpleCLexer {

    public static void main(String argv[]) {
        try {
            if (argv.length == 1 || argv.length == 2) {
                System.out.println("Lexing [" + argv[0] + "]");

                PrintStream dfout = System.out;
                if (argv.length == 2) {
                    System.setOut(new PrintStream(argv[1]));

                }
                Scanner scanner = new Scanner(new FileReader(argv[0]));
                Symbol s;
                do {
                    s = scanner.debug_next_token();
                } while (s.sym != sym.EOF);

                System.setOut(dfout);

                System.out.println("No errors.");
            } else {
                System.out.println("Input error!");
                System.out.println("Usage: command <input> <output>");
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(1);
        }
    }
}
