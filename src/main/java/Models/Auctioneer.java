package Models;

public class Auctioneer extends User {
    private int numberOfResolvedDisputes;
    private int numberOfAuctions;

    public Auctioneer(int id, String name, String cnic, String email, String password, double balance, int numberOfResolvedDisputes, int numberOfAuctions) {
        super(id, name, cnic, email, password, UserRole.AUCTIONEER);
        this.numberOfResolvedDisputes = numberOfResolvedDisputes;
        this.numberOfAuctions = numberOfAuctions;
    }

    // Getters and Setters
    public int getNumberOfResolvedDisputes() {
        return numberOfResolvedDisputes;
    }

    public void setNumberOfResolvedDisputes(int numberOfResolvedDisputes) {
        this.numberOfResolvedDisputes = numberOfResolvedDisputes;
    }

    public int getNumberOfAuctions() {
        return numberOfAuctions;
    }

    public void setNumberOfAuctions(int numberOfAuctions) {
        this.numberOfAuctions = numberOfAuctions;
    }
}
