/*
 *  toplevel4j - Toplevel Lisp Interpreter for Java
 *  Copyright (C) 2014 Javier Romo
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.beinn.toplevel4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import com.github.beinn.lisp4j.Interpreter;
import com.github.beinn.lisp4j.exceptions.LispException;

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
        final InputStream stdin = System.in;
        final OutputStream stdout = System.out;
        final OutputStream stderr = System.err;
        
        final Options options = new Options();
        final CmdLineParser parser = new CmdLineParser(options);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar jLisp.jar [files...] [options...]");
            parser.printUsage(stderr);
        }
        if (options.isShowHelp()) {
            System.out.println("java -jar jLisp.jar [files...] [options...]");
            parser.printUsage(stdout);
            System.exit(0);
        }

        if (options.isVersion()) {
            System.out.println("Java Lisp Interpreter 0.1");
            System.out.println("Licence: GNU GPLv2 - (C)beinn 2014");
            System.exit(0);
        }

        try {
            new Main().console(options, stdin, stdout, stderr);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
        System.exit(0);
    }

    private void console(final Options options, final InputStream stdin, final OutputStream stdout, final OutputStream stderr) throws IOException {
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
                            stdout.write(outLine.getBytes());
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

            stdout.write(prompt.getBytes());
            String input = "";
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
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
                System.err.println(e);
            }
        }
    }
}
