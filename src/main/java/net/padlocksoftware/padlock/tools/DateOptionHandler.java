package net.padlocksoftware.padlock.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OneArgumentOptionHandler;
import org.kohsuke.args4j.spi.Setter;

public class DateOptionHandler extends OneArgumentOptionHandler<Date> {
    public DateOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Date> setter) {
        super(parser, option, setter);
    }

    // will not support any other format!
    private static final String DATE_FORMAT = "yyyy/MM/dd";
   
    @Override
    protected Date parse(String argument) throws NumberFormatException, CmdLineException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
           return dateFormat.parse(argument);
        } catch (ParseException ex) {
            throw new CmdLineException(owner, ex);
        }
    }
}
