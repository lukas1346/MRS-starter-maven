package dk.easv.mrs.BE;

public record Rating(int id, int movieId, int userId, String name, int score, String review) {}
