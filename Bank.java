
package javaapplication1;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Bank {

    Scanner sc = new Scanner(System.in);

    public String account_number;
    public String name;
    public String User_name;
    public String Passwoard;
    public long balance;
    public String FileName;
    public String affiliation;

    public String getAccountNumber() {
        return account_number;
}
    //constructor
    public Bank() {
    }
 public void newAccount() {
        try {
            String fname, lname;

            System.out.print("What FileName Do you Want To save Your Data on it: ");
            FileName = sc.next();

            System.out.print("\nPlease Enter Account No: ");
            account_number = sc.next();

            System.out.print("Please Enter First Name: ");
            fname = sc.next();

            System.out.print("Please Enter Last Name: ");
            lname = sc.next();

            name = fname + " " + lname;

            System.out.print("Please Enter User Name: ");
            User_name = sc.next();

            System.out.print("Please Enter Password: ");
            Passwoard = sc.next();

            System.out.print("Please Enter Your Affiliation (FCIT/non-FCIT): ");
            affiliation = sc.next().toUpperCase();

            // Input validation for balance
            while (true) {
                try {
                    System.out.print("Enter Balance: ");
                    balance = sc.nextLong();
                    if (balance < 0) {
                        System.out.println("Balance cannot be negative. Please enter a valid amount.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid numeric value for balance.");
                    sc.next(); // consume the invalid input
                }
            }

            System.out.println("");
            InsertRow(account_number, name, balance, affiliation);
        } catch (Exception e) {
            System.err.println("Error creating a new account: " + e.getMessage());
            e.printStackTrace();
        }
    }


     public void deposit() {
        try {
            long deposit;

            // Input validation for deposit
            while (true) {
                try {
                    System.out.print("How much do you want to deposit: ");
                    deposit = sc.nextLong();
                    if (deposit < 0) {
                        System.out.println("Deposit amount cannot be negative. Please enter a valid amount.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid numeric value for deposit.");
                    sc.next(); // consume the invalid input
                }
            }

            System.out.print("Successful to deposit " + deposit + "\t\t");
            balance = balance + deposit;
        } catch (Exception e) {
            System.err.println("Error depositing money: " + e.getMessage());
            e.printStackTrace();
        }
    }

      public void withdrawal() {
        try {
            long amount_of_width;

            // Input validation for withdrawal amount
            while (true) {
                try {
                    System.out.print("Enter the amount you want to withdraw: ");
                    amount_of_width = sc.nextLong();

                    if (amount_of_width < 0) {
                        System.out.println("Withdrawal amount cannot be negative. Please enter a valid amount.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid numeric value for withdrawal amount.");
                    sc.next(); // consume the invalid input
                }
            }

            if (balance >= amount_of_width) {
                balance = balance - amount_of_width;
                System.out.print("Balance after withdrawal: " + balance + "\t\t");
            } else {
                System.out.println("Your balance is less than " + amount_of_width + "\tTransaction failed...!!");
            }
        } catch (Exception e) {
            System.err.println("Error withdrawing money: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // -----------method to transfer money between two accounts------
    
    public void transfer(Bank destinationAccount) {
        try {
            long transferAmount;

            // Input validation for transfer amount
            while (true) {
                try {
                    System.out.print("Enter the amount to transfer: ");
                    transferAmount = sc.nextLong();

                    if (transferAmount < 0) {
                        System.out.println("Transfer amount cannot be negative. Please enter a valid amount.");
                    } else {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid numeric value for transfer amount.");
                    sc.next(); // consume the invalid input
                }
            }

            if (balance >= transferAmount) {
                balance -= transferAmount;
                destinationAccount.balance += transferAmount;
                System.out.println("Transfer successful!");
            } else {
                System.out.println("Insufficient funds for the transfer. Transaction failed.");
            }
        } catch (Exception e) {
            System.err.println("Error transferring money: " + e.getMessage());
            e.printStackTrace();
        }
    
    }
    // ---------------- method to request a loan
    public void requestLoan() {
        try {
            if ("FCIT".equalsIgnoreCase(affiliation)) {
                long loanAmount;

                // Input validation for loan amount
                while (true) {
                    try {
                        System.out.print("Enter the loan amount: ");
                        loanAmount = sc.nextLong();

                        if (loanAmount < 0) {
                            System.out.println("Loan amount cannot be negative. Please enter a valid amount.");
                        } else {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid numeric value for loan amount.");
                        sc.next(); // consume the invalid input
                    }
                }

                balance += loanAmount;
                System.out.println("Loan of " + loanAmount + " successfully credited to your account.");
            } else {
                System.out.println("Only FCIT students are eligible for a loan.");
            }
        } catch (Exception e) {
            System.err.println("Error processing loan request: " + e.getMessage());
            e.printStackTrace();
        }
    }

 // Implementation to find the destination account based on account number
    public static Bank findDestinationAccount(String accountNumber) {
        try {
            // Assuming the files are stored in a specific directory
            File folder = new File("path_to_directory_containing_files");

            // Loop through each file in the directory
            for (File file : folder.listFiles()) {
                if (file.isFile()) {
                    // Read the content of each file
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        // Assuming the account number is stored in a specific format in the file
                        if (line.contains("Account Number: " + accountNumber)) {
                            // Parse other details from the file and create a Bank object
                            Bank account = parseAccountDetailsFromFile(file);
                            reader.close();
                            return account;
                        }
                    }
                    reader.close();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading files: " + e.getMessage());
        }
        return null; // Return null if the account is not found
    }
		//method to parse account details from a file
   private static Bank parseAccountDetailsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String accountNumber = null;
            String name = null;
            long balance = 0;
            String affiliation = null;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Account Number:")) {
                    accountNumber = line.substring("Account Number:".length()).trim();
                } else if (line.startsWith("Name:")) {
                    name = line.substring("Name:".length()).trim();
                } else if (line.startsWith("Balance:")) {
                    balance = Long.parseLong(line.substring("Balance:".length()).trim());
                } else if (line.startsWith("Affiliation:")) {
                    affiliation = line.substring("Affiliation:".length()).trim();
                }
            }

		/*
	public static Bank parseAccountDetailsFromFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Read lines from the file and extract account details
            // Example: Read lines, split data, and create a Bank object
            String line = reader.readLine();
            String[] data = line.split(",");  // Adjust based on your file format

            Bank bank = new Bank();
            bank.account_number = data[0];
            bank.name = data[1];
            bank.balance = Long.parseLong(data[2]);
            bank.affiliation = data[3];

            return bank;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error parsing account details from file: " + e.getMessage());
            return null;
        }
    }	
	*/
            // Check if all necessary details are found
            if (accountNumber != null && name != null && affiliation != null) {
                // Create and return a Bank object
                Bank account = new Bank();
                account.account_number = accountNumber;
                account.name = name;
                account.balance = balance;
                account.affiliation = affiliation;

                return account;
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error parsing account details: " + e.getMessage());
        }

        return null; // Return null if there was an error or if required details are missing
   }

    public void showAccount() {
        String fmt = "%5s %16s %12s";
        String fmt2 = "%6s %18s %12s";
        System.out.printf(fmt, "\nAccount_Name", "  Account_Number", "Balance\n");

        System.out.printf(fmt, "------------", "  -------------", "  ------------");
        printDate();
        System.out.printf(fmt2, name, "SA" + account_number, balance);
        System.out.println();
    }

    public String printDate() {
        Date d = new Date();

        SimpleDateFormat sd = new SimpleDateFormat("\t\t\t\t\t hh:mm a -------");
        System.out.print(sd.format(d));

        SimpleDateFormat sd2 = new SimpleDateFormat(" dd/mm/yyyy");
        System.out.println(sd2.format(d));

        return d.toString();
    }

    public void printFile() {
        try {
            FileOutputStream fos = new FileOutputStream(
                    "C:\\Users\\m7md\\OneDrive - SFC Hackathon\\Desktop\\" + FileName + ".txt", true);
            OutputStreamWriter fw = new OutputStreamWriter(fos);

            fw.write("--------- FCIT Bank ---------\n");
            fw.write("\t\n Welcome User: " + name);
            fw.write("\t\t\t\t\t\t " + printDate() + "\n");
            fw.write("\t\n Your Account Number :" + "SA" + account_number + "\n");
            fw.write("\t Your Balance :" + balance + "\n");
            fw.write("\t Affiliation :" + affiliation + "\n");
            fw.write("\n-------------------------------------------------------\n");
            fw.write("\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
		 e.printStackTrace();
        }
    }

    public void ExitMessage() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(
                    "C:\\Users\\m7md\\OneDrive - SFC Hackathon\\Desktop\\" + FileName + ".txt", true);
            OutputStreamWriter fw = new OutputStreamWriter(fos);
            fw.write("\t\n ** USER HAS EXIT FROM PROGRAM **");
            fw.write("\t\t\t\t\tin -->" + printDate() + "\n");
            fw.write("\n");
            fw.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public void ResetFile() throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(
                    "C:\\Users\\m7md\\OneDrive - SFC Hackathon\\Desktop\\" + FileName + ".txt");
            fos.close();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
		//Inserts a new row into the SQL database with user information.
     public void InsertRow(String Accountnum, String name, long B, String affiliation) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = SQL_Bank_Server.connect();
            String s = "INSERT INTO USER(AccountNumber, Name, Balance, Affiliation) VALUES(?,?,?,?)";
            ps = con.prepareStatement(s);
            ps.setString(1, Accountnum);
            ps.setString(2, name);
            ps.setLong(3, B);
            ps.setString(4, affiliation);
            ps.execute();
        } catch (SQLException ex) {
            System.err.println("Database error: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException ex) {
                System.err.println("Error closing resources: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
    void check_Username_password(String answerUser, String answerPass) {
        // Simple implementation for demonstration
        if (answerUser.equals("admin") && answerPass.equals("admin")) {
            System.out.println("Login successful.");
        } else {
            System.out.println("Login failed.");
        }
    }
}