package dk.easv.mrs.BE;

public record Movie(int id, String title, int year) {
    @Override
    public String toString() {
        return title + " (" + year + ")";
    }
}
