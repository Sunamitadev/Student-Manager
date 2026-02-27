package student.management.backend.domain.usecase;

public interface Login {
    String execute(String username, String password);
}
