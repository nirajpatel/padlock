package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.StringWriter;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class LicenseValidatorOptions {
    private final CmdLineParser parser;
    private String errorMessage;
    
    @Option(name="-k", metaVar="Key File", usage="Key File", required=true)
    private File keyFile;
    
    @Option(name="-l", metaVar="License File", usage="License File", required=true)
    private File licenseFile;
    
    public LicenseValidatorOptions(String[] args) {
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

    public File getLicenseFile() {
        return licenseFile;
    }
}
