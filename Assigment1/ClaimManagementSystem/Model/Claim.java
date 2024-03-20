package ClaimManagementSystem.Model;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Claim {
    private String id;
    private LocalDate claimDate;
    private Customer insuredPerson;
    private String cardNumber;
    private LocalDate examDate;
    private List<String> documents;
    private double claimAmount;
    private ClaimStatus status;
    private String bankName;
    private String receiverName;
    private String bankNumber;
    public enum ClaimStatus {
        New,
        Processing,
        Done
    }

    public Claim(String id, LocalDate claimDate, Customer insuredPerson, String cardNumber,
                 LocalDate examDate, List<String> documents, double claimAmount,
                 ClaimStatus status, String bankName, String receiverName, String bankNumber) {
        this.id = id;
        this.claimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.cardNumber = cardNumber;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        this.bankName = bankName;
        this.receiverName = receiverName;
        this.bankNumber = bankNumber;
        insuredPerson.getClaims().add(this);
    }

    public String getId() {
        return id;
    }

    public LocalDate getClaimDate() {
        return claimDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public ClaimStatus getStatus() {
        return status;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public String getBankName() {
        return bankName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setClaimDate(LocalDate claimDate) {
        this.claimDate = claimDate;
    }

    public void setInsuredPerson(Customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public void setStatus(ClaimStatus status) {
        this.status = status;
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Claim Details:\n");
        sb.append(String.format("  - ID: %s\n", id));
        sb.append(String.format("  - Claim Date: %s\n", claimDate));
        sb.append(String.format("  - Insured Person: %s\n", insuredPerson.getName()));
        sb.append(String.format("  - Card Number: %s\n", cardNumber));
        sb.append(String.format("  - Exam Date: %s\n", examDate));
        sb.append(String.format("  - Claim Amount: %.2f\n", claimAmount));
        sb.append(String.format("  - Status: %s\n", status));
        return sb.toString();
    }

    public String toData() {
        return String.format("%s,%s,%s,%s,%s,%.2f,%s",
                id,
                claimDate,
                insuredPerson.getId(),
                cardNumber,
                examDate,
                claimAmount,
                status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Claim claim = (Claim) obj;
        return id.equals(claim.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
