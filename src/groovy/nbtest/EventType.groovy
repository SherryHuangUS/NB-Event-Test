package nbtest

public enum EventType {
    ENTER("enter"),
    COMMENT("comment"),
    HIGHFIVE("highfive"),
    LEAVE("leave")

    private final String val

    EventType(String val) {
        this.val = val
    }

    String value() {
        return val
    }
}
