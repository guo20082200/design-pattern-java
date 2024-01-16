package com.zishi.pattern.structure.adapter;

import java.util.Objects;

public class LogAdapter implements LogFactory {
    private NbLogger nbLogger;

    public LogAdapter(NbLogger nbLogger) {
        this.nbLogger = nbLogger;
    }

    @Override
    public void debug(String tag, String message) {
        Objects.requireNonNull(nbLogger);
        nbLogger.d(1, message);
    }
}