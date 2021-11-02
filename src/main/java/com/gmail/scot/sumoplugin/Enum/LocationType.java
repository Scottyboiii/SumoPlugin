package com.gmail.scot.sumoplugin.Enum;

public enum LocationType {
    JOIN("JoinLocation"),
    LOST("LeaveLocation"),
    WINNER("WinnerLocation"),
    POS1("Pos1Location"),
    POS2("Pos2Location");

    private final String databaseName;

    LocationType(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
