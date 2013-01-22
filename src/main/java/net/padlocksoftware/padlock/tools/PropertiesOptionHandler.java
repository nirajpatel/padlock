package net.padlocksoftware.padlock.tools;

import java.util.Properties;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

public class PropertiesOptionHandler extends OneArgumentOptionHandler<Properties> {
    public PropertiesOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Properties> setter) {
        super(parser, option, setter);
    }

    @Override
    protected Properties parse(String argument) throws NumberFormatException, CmdLineException {
        Properties props = new Properties();
        String[] pairs = argument.split(",");

        for (String pair : pairs) {
            String[] str = pair.split("=");
            if (str.length != 2) {
                throw new CmdLineException(owner, "\nInvalid properties string: " + argument + "\n");
            }
            props.setProperty(str[0].trim(), str[1].trim());
        }
        return props;
    }
}
