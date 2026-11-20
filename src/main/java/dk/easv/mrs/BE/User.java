package dk.easv.mrs.BE;

public record User(Integer id, String name, String password, UserRole role) {

    public User(int id, String name) {
        this(id, name, null, null);
    }

    public User(String name, String password) {
        this(null, name, password, null);
    }

    public User(String name, UserRole role) {
        this(null, name, null, role);
    }
}