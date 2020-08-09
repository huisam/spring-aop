package cglib;

import lombok.Getter;

@Getter
public class Key {
    private final String previous;

    public Key(String previous) {
        this.previous = previous;
    }
}
