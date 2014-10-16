package org.jlisp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.lisp4j.Interpreter;

public class Main {
    
    public static void main(final String... args) {
        
        Interpreter interpreter = new Interpreter();
        List<String> output = new ArrayList<String>();
        while (!interpreter.isHalted()) {
            System.out.print(">> ");
            String input = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = reader.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            
            try {
                output = interpreter.execute(input);
                for (final String line : output) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
