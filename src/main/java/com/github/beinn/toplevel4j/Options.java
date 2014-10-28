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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class Options {

    @Option(name = "-version", aliases={"-v","--version"}, usage = "display version")
    private boolean version = false;

    @Option(name = "-e", usage = "execute lisp files provided and quits without user interaction.")
    private boolean executeOnly = false;

    @Option(name = "-h", aliases = "--help", usage = "shows this help text")
    private boolean showHelp = false;
    
    @Option(name = "-q", usage = "quit mode")
    private boolean quiet = false;

    @Argument
    private List<File> files = new ArrayList<File>();

    public boolean isVersion() {
        return version;
    }

    public boolean isExecuteOnly() {
        return executeOnly;
    }

    public boolean isQuiet() {
        return quiet;
    }


    public List<File> getFiles() {
        return files;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

}
