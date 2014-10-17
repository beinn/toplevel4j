package org.jlisp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.lisp4j.Interpreter;

public class Main {
    
    public static void main(final String... args) {
        final Options options = new Options();
        final CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar jLisp.jar [files...] [options...]");
            parser.printUsage(System.err);
        }
        if (options.isShowHelp()) {
            System.out.println("java -jar jLisp.jar [files...] [options...]");
            parser.printUsage(System.out);
            System.exit(0);
        }
        final Interpreter interpreter = new Interpreter();
        
        for (File file:options.getFiles()) {
            if (file.exists() && file.isFile() && file.canRead()) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    while (line != null) {
                        List<String> output = interpreter.execute(line);
                        for (final String outLine : output) {
                            System.out.println(outLine);
                        }
                        if (interpreter.isHalted()) {
                            System.exit(0);
                        }
                        line = reader.readLine();
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        
        if (options.isExecuteOnly()) {
            System.exit(0);
        }
        
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
                final List<String> output = interpreter.execute(input);
                for (final String line : output) {
                    System.out.println(line);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
