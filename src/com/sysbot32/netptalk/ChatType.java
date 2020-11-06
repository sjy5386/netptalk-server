package com.sysbot32.netptalk;

public enum ChatType {
    TEXT("text"),
    IMAGE("image");

    public final String type;

    ChatType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
