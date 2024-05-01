package assignments.assignment3.systemCLI;

import java.util.Scanner;

public abstract class UserSystemCLI {
    protected Scanner input = new Scanner(System.in);

    // Method digunakan untuk run program berdasarkan role user
    public void run() {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    // Method digunakan untuk menampilkan menu
    abstract void displayMenu();

    // Method digunakan untuk handle menu
    abstract boolean handleMenu(int command);
}
