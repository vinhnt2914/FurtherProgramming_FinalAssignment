package ClaimManagementSystem.Model;

import ClaimManagementSystem.DataManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Customer implements ClaimProcessManager{
    private String id;
    private String name;
    private InsuranceCard insuranceCard;
    private List<Claim> claims;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        claims = new ArrayList<>();
    }

    public void addInsuranceCard(InsuranceCard card) {
        this.insuranceCard = card;
    }

    public void addClaim(Claim claim) {
        this.claims.add(claim);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public List<Claim> getClaims() {
        return claims;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this instanceof PolicyHolder) {
            sb.append("Customer Details (Policy Holder): \n");
        } else if (this instanceof Dependant) {
            sb.append("Customer Details (Dependant): \n");
        }
        sb.append(String.format("  - ID: %s\n", id));
        sb.append(String.format("  - Name: %s\n", name));
        sb.append(String.format("  - Insurance Card: %s\n", insuranceCard == null ? "None" : insuranceCard.getCardNumber()));
        sb.append("  - Claims:\n");
        if (claims.isEmpty()) {
            sb.append("      None\n");
        } else {
            for (Claim claim : claims) {
                sb.append(String.format("      - %s\n", claim));
            }
        }
        return sb.toString();
    }

    public String toData() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",");
        sb.append(name).append(",");
        sb.append(insuranceCard == null ? null : insuranceCard.getCardNumber()).append(",");
        for (int i = 0; i < claims.size(); i++) {
            sb.append(claims.get(i).getId());
        }
        return sb.toString();
    }

    @Override
    public void add(Claim claim) {
        claims.add(claim);
    }

    @Override
    public void update(Claim claim) {

    }

    @Override
    public void delete(Claim claim) {
        claims.remove(claim);
        claim.setInsuredPerson(null);
        DataManager.getClaims().remove(claim.getId());
    }

    @Override
    public Claim getOne(Claim claim) {
        for (Claim c : claims) {
            if (c == claim) return c;
        }
        return null;
    }

    @Override
    public List<Claim> getAll() {
        return claims;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Customer customer = (Customer) obj;
        return Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
