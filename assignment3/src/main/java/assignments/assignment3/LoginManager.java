package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    // Constructor untuk login manager
    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    // Method untuk mendapatkan system berdasarkan role
    public UserSystemCLI getSystem(String role) {
        if (role.equalsIgnoreCase("admin")) {
            return this.adminSystem;
        } else {
            return this.customerSystem;
        }
    }
}
