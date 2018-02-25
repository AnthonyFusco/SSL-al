package com.unice.dsl.builders;

import kernel.datasources.laws.DataSource;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApplicationBuilder {
    private static final DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
    private Date startDate;
    private Date endDate;
    private List<EntityBuilder<DataSource>> builders;

    public ApplicationBuilder start(String startDateString) {
        try {
            this.startDate = format.parse(startDateString);
        } catch (ParseException e) {
            printError("The start date must be formatted as dd/MM/yyyy HH:mm:ss");
        }
        return this;
    }

    public ApplicationBuilder end(String endDateString) {
        try {
            this.endDate = format.parse(endDateString);
        } catch (ParseException e) {
            printError("The end date must be formatted as dd/MM/yyyy HH:mm:ss");
        }
        return this;
    }

    public ApplicationBuilder play(EntityBuilder<DataSource>... builder) {
        this.builders = Arrays.asList(builder);
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<EntityBuilder<DataSource>> getBuilders() {
        return builders;
    }

    private void printError(String message) {
        System.out.println("\u001B[31m" + message + "\u001B[37m");
    }
}
