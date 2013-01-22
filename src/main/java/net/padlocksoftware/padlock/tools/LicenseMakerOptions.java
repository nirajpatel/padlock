package net.padlocksoftware.padlock.tools;

import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class LicenseMakerOptions {
    private final CmdLineParser parser;
    
    @Option(name="-k", metaVar="Key File", usage="Key File", required=true)
    private File keyFile;
    
    @Option(name="-o", metaVar="License File", usage="Output License File")
    private File licenseFile;
    
    @Option(name="-O", usage="end output to standard out instead of a file")
    private boolean standardOut;
    
    @Option(name="-s", metaVar="Start", usage="The start of the license validity period, " 
    		+ "different than the current date.  In ms since"
                + "the epoch (1/1/1970)")
    private Long startInMs;
    
    @Option(name="-S", metaVar="Start", handler=DateOptionHandler.class,
        usage="The start of the license validity period, if different then the current date.  " +
        		"The date format is yyyy/MM/dd.")
    private Date startDate;
    
    @Option(name="-e", metaVar="Expiration",
        usage="License expiration date. If this option is omitted the license is perpetual " +
                        "In ms since the epoch (1/1/1970)")
    private Long expirationInMs;
    
    @Option(name="-E", metaVar="Expiration", handler=DateOptionHandler.class,
        usage="The start of the license validity period, if different then the current date.  " +
                        "The date format is yyyy/MM/dd.")
    private Date expirationDate;
    
    @Option(name="-x", metaVar="Expiration Float",
        usage="Number of ms to expire after the initial run")
    private Long expirationFloatInMs;
    
    @Option(name="-p", metaVar="Properties", handler=MapOptionsHandler.class,
        usage="License properties, Expressed as options of the form: key1=value1, key2=value2")
    private Map<String, String> properties;
    
    @Option(name="-h", metaVar="Addresses", usage="Hardware locked addresses, in the form of mac1, mac2, mac3", 
        handler=StringSetOptionHandler.class)
    private Set<String> addresses;
    
    public LicenseMakerOptions(String args[]) throws CmdLineException {
        parser = new CmdLineParser(this);
        parser.parseArgument(args);
        
        if (licenseFile == null && !standardOut) {
            throw new CmdLineException(parser, "License File or Standard Out flag required");
        }
        
        if (startInMs != null && startDate != null) {
            throw new CmdLineException(parser, "Only one of StartDate or StateInMs allowed");
        }
        
        if (expirationInMs != null && expirationDate != null) {
            throw new CmdLineException(parser, "Only one of ExpirationDate or ExpirationInMs allowed");
        }
        
        if (startInMs != null) {
            startDate = new Date(startInMs);
        }
        
        if (expirationInMs != null) {
            expirationDate = new Date(expirationInMs);
        }
        
        if (properties == null) {
            properties = new HashMap<String, String>();
        }
        
        if (addresses == null) {
            addresses = new HashSet<String>();
        }
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

    public boolean isStandardOut() {
        return standardOut;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public Long getExpirationFloatInMs() {
        return expirationFloatInMs;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public Set<String> getAddresses() {
        return addresses;
    }
}