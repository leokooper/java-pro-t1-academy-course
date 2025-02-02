package ru.leonchenko.steamapi.emuns;

public enum Position {
    ENGINEER("Инженер"),
    MANAGER("Менеджер");

    private final String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
