package com.gmail.scot.sumoplugin.Enum;

public enum LocationType {
    JOIN("JoinLocation"),
    LOST("LeaveLocation"),
    WINNER("WinnerLocation");

    private final String databaseName;

    LocationType(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
