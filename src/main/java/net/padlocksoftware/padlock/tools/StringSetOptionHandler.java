package net.padlocksoftware.padlock.tools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

public class StringSetOptionHandler extends OptionHandler<Collection<String>> {
    public StringSetOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Collection<String>> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        int counter = 0;
        Set<String> collection = new HashSet<String>();
        while (true) {
            String param;
            try {
                param = params.getParameter(counter);
            } catch (CmdLineException ex) {
                break;
            }
            if (param.startsWith("-")) {
                break;
            }
           
            collection.add(param);
            counter++;
        }// while true

        setter.addValue(collection);
        
        return counter;
    }

    @Override
    public String getDefaultMetaVariable() {
        return null;
    }
}
