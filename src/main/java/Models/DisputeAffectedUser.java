package Models;

public class DisputeAffectedUser {
    private int disputeId;
    private int userId;

    public DisputeAffectedUser() {
        this(0, 0);
    }

    public DisputeAffectedUser(int disputeId, int userId) {
        this.disputeId = disputeId;
        this.userId = userId;
    }

    public int getDisputeId() {
        return disputeId;
    }

    public void setDisputeId(int disputeId) {
        this.disputeId = disputeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}