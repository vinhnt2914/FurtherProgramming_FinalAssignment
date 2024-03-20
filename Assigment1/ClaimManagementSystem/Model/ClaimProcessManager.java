package ClaimManagementSystem.Model;

import java.util.List;

public interface ClaimProcessManager {
    void add(Claim claim);
    void update(Claim claim);
    void delete(Claim claim);
    Claim getOne(Claim claim);
    List<Claim> getAll();
}
