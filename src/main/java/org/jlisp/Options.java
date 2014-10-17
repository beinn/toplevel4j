package org.jlisp;

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
