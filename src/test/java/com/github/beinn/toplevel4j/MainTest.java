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

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;


public class MainTest {

    @Test
    public void console() throws IOException {
        Main main = new Main();
        Options options = new Options();
        InputStream stdin = new ByteArrayInputStream("(quit)".getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        main.console(options, stdin, stdout, stderr);
        assertEquals("",stderr.toString());
        assertEquals("> Bye!\n",stdout.toString());
    }

    @Test
    public void console2() throws IOException {
        Main main = new Main();
        Options options = new Options();
        InputStream stdin = new ByteArrayInputStream("(+ 2 3)(quit)".getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        main.console(options, stdin, stdout, stderr);
        assertEquals("",stderr.toString());
        assertEquals("> 5.0\nBye!\n",stdout.toString());
    }
    
    @Test
    public void console3() throws IOException {
        Main main = new Main();
        Options options = new Options();
        InputStream stdin = new ByteArrayInputStream("(+ 2 3))".getBytes());
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        ByteArrayOutputStream stderr = new ByteArrayOutputStream();
        main.console(options, stdin, stdout, stderr);
        assertEquals("",stderr.toString());
        assertEquals("> >> ",stdout.toString());
    }
}
