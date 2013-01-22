package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.StringWriter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class KeyMakerOptions {
    private final CmdLineParser parser;
    private String errorMessage;
    
    @Option(name="-k", metaVar="Key File", usage="Key File", required=true)
    private File keyFile;
    
    @Option(name="-j", usage="Print Key Java Fragment")
    private boolean javaFragment;

    public KeyMakerOptions(String[] args) {
        parser = new CmdLineParser(this);
        
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            this.errorMessage = e.getMessage();
            return;
        }
    }
    
    public boolean isValid() {
        return errorMessage == null;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public String getUsage() {
        StringWriter writer = new StringWriter();
        parser.printUsage(writer, null);
        return writer.toString();
    }
    
    public File getKeyFile() {
        return keyFile;
    }
    
    public boolean isJavaFragment() {
        return javaFragment;
    }
}
