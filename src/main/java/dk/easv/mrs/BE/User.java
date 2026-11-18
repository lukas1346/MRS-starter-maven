package dk.easv.mrs.BE;

public record User(Integer id, String name, String password) {

    public User(int id, String name) {
        this(id, name, null);
    }

    public User(String name, String password) {
        this(null, name, password);
    }
}