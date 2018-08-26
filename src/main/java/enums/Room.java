package enums;

public enum Room {

    SINGLE("Single"), FAMILY("Family"), BUSINESS("Business");

    private final String value;

    Room(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}