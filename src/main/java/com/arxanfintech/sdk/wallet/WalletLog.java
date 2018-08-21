package com.arxanfintech.sdk.wallet;

import java.util.logging.Logger;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class WalletLog {
    public WalletLog(String filename) throws SecurityException, IOException {
        log = Logger.getLogger(filename);
        log.setLevel(Level.INFO);

        fileHandler = new FileHandler(filename);
        fileHandler.setLevel(Level.INFO);

        fileHandler.setFormatter(new LogHander());
        log.addHandler(fileHandler);
    }

    private Logger log;
    private FileHandler fileHandler;

    class LogHander extends Formatter {
        @Override
        public String format(LogRecord record) {
            return record.getLevel() + ":" + record.getMessage() + "\n";
        }
    }
}
