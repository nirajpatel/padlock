package net.padlocksoftware.padlock.tools;

import java.util.ArrayList;
import java.util.Map;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.MapSetter;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * This is an enhanced version of MapOptionHandler which allows specifying multiple key=value options after
 * the name, rather than just one.
 * 
 * This is basically a copy of StringArrayOptionHandler
 */
public class MapOptionsHandler extends OptionHandler<Map<?, ?>> {
    public MapOptionsHandler(CmdLineParser parser, OptionDef option, Setter<? super Map<?, ?>> setter) {
        super(parser, option, setter);
    }

    @Override
    public int parseArguments(Parameters params) throws CmdLineException {
        MapSetter mapSetter = (MapSetter)setter;

        int counter = 0;
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

            mapSetter.addValue(params.getParameter(counter));

            counter++;
        }// while true

        return counter;
    }

    @Override
    public String getDefaultMetaVariable() {
        return null;
    }
}
