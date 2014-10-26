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
import org.lisp4j.exceptions.LispException;

/**
 * 
 * @author beinn
 *
 */
public class Main {

    /**
     * 
     * @param args
     */
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

        if (options.isVersion()) {
            System.out.println("Java Lisp Interpreter 0.1");
            System.out.println("Licence: GNU GPLv2 - (C)beinn 2014");
            System.exit(0);
        }

        final Interpreter interpreter = new Interpreter();

        for (File file : options.getFiles()) {
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
        String prompt = "> ";
        while (!interpreter.isHalted()) {

            System.out.print(prompt);
            String input = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                input = reader.readLine();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (":help".equalsIgnoreCase(input)) {
                System.out.println(":abort");
            } else if (":abort".equalsIgnoreCase(input)) {
                System.out.println("Top level.");
                prompt = "> ";
            } else if (input.trim().startsWith(":")) {
                System.err.println(input + " is not break command");
            }
            try {
                final List<String> output = interpreter.execute(input);
                for (final String line : output) {
                    System.out.println(line);
                }
            } catch (LispException e) {
                System.err.println(e.getMessage());
                prompt = ">> ";
            } catch (Exception e) {
                System.err.println("INTERPRETER PANIC!");
                System.err.println(e.getMessage());
            }
        }
    }
}
