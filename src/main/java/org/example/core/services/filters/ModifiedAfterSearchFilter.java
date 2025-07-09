package org.example.core.services.filters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

class ModifiedAfterSearchFilter implements FileSearchFilter {

    private final LocalDate dateTime;
    private final ZoneId zone;

    public ModifiedAfterSearchFilter(LocalDate dateTime) {
        this(dateTime, ZoneId.systemDefault());
    }

    public ModifiedAfterSearchFilter(LocalDate dateTime, ZoneId zone) {
        this.dateTime = dateTime;
        this.zone = zone;
    }

    @Override
    public boolean test(Path file) throws IOException {
        Instant fileInstant = Files.getLastModifiedTime(file).toInstant();
        LocalDate fileDateTime = fileInstant.atZone(zone).toLocalDate();

        return fileDateTime.isAfter(dateTime);
    }

}


