package Models;

public enum UserRole {
	USER,
    BUYER,
    SELLER,
    AUCTIONEER;
    	
    public String getRoleName() {
    	return this.name();
    }
    
    public static UserRole getRole(String roleName) {
    	UserRole userRole;

    	if (roleName == BUYER.getRoleName()) userRole = BUYER;
    	else if (roleName == SELLER.getRoleName()) userRole = SELLER;
    	else if (roleName == AUCTIONEER.getRoleName()) userRole = AUCTIONEER;
    	else userRole = USER;
    	
    	return userRole;
    }
}