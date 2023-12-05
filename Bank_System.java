package javaapplication1;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Bank_System {
 public static void main(String[] args) throws IOException {
        int counter = 0;
        Scanner sc = new Scanner(System.in);
        Socket s = new Socket();
        try {
            s = new Socket("localhost", 50999);
        } catch (IOException e) {
            System.err.println("Could not connect to server: " + e.getMessage());
            System.exit(1); // Exit the program if the connection cannot be established
        }
        System.out.println("\t\t ** Welcome To FCIT Server ** ");
        System.out.print("Login As: ");
        String n = sc.nextLine();
        System.out.println("");
        String ch = "";
        String msg;
        Bank currentAccount = null;  // Reference to the user's bank account

        while (!ch.equalsIgnoreCase("3")) {

            InputStreamReader inr = new InputStreamReader(s.getInputStream());
            OutputStreamWriter owr = new OutputStreamWriter(s.getOutputStream());

            BufferedReader bfr = new BufferedReader(inr);
            BufferedWriter bfw = new BufferedWriter(owr);

            msg = "";

            System.out.print("\n1. To Talk With Server\n2. To Enter FCIT Bank\n3. Exit\n -----> ");
            ch = sc.nextLine();

            switch (ch) {
                case "1":
                    while (!msg.equalsIgnoreCase("bye")) {

                        if (counter > 0) {
                            bfw.write("** Successful Login **");
                            bfw.newLine();
                            bfw.flush();
                            counter--;
                        }
                        System.out.print("\n\nEnter what do you want to say to Server ('?' FOR INSTRUCTION): ");
                        msg = sc.nextLine();

                        bfw.write(msg);
                        bfw.newLine();
                        bfw.flush();

                        System.out.print("Server: " + bfr.readLine());
                        System.out.println("");
                        if (msg.equalsIgnoreCase("?")) {
                            System.out.println(
                                    "** name: to show name of user\n   \t   date: to show current date\n   \t   hi:   welcome message\n   \t   bye:  Log out \t**");
                        }

                        if (msg.equalsIgnoreCase("bye")) {
                            System.out.println("");
                            break;
                        } else if (msg.equalsIgnoreCase("name")) {
                            System.out.println("' " + n.toUpperCase() + " '");
                        }
                    }
                    counter++;
                    break;

                case "2":
                    while (true) {
                        Bank bank = new Bank();

                        String answerUser;
                        String answerPass;

                        while (true) {

                            System.out.println("\t-- Welcome to FCIT Bank --");

                            System.out.print("Do you have an account (Y/N) ? ");
                            String answer = sc.next();
                            answer = answer.toUpperCase();
                            switch (answer) {
                                case "Y":
                                    System.out.print("Please Enter User Name: ");
                                    answerUser = sc.next();
                                    System.out.print("Please Enter Password: ");
                                    answerPass = sc.next();
                                    bank.check_Username_password(answerUser, answerPass);
                                  //  if (!answerUser.matches(answerUser))
                                    //    System.out.println("Sorry you don't have account");
                                    // Set the currentAccount reference to the user's bank account
                                    currentAccount = bank;
                                    break;

                                case "N":
                                    System.out.print("Would you want to create a new account (y/n) ");
                                    String newAccount = sc.next();

                                    if (newAccount.equalsIgnoreCase("y")) {
                                        bank.newAccount();
                                      //  bank.showAccount();
                                    } else {
                                        bfw.write("exit");
                                        bfw.newLine();
                                        bfw.flush();
                                        System.exit(0);
                                    }
                                    break;
                            }

                            System.out.println("\n\t-- Welcome to FCIT Bank -- : " + bank.User_name);

                            while (true) {
                                System.out.println("\n1. Display all account details \n2. "
                                        + "Print Details in an external file\n3. Deposit the amount \n4. Withdraw the amount \n5. Transfer money\n6. Request a loan\n7. Exit (Back to lobby) ");
                                String answerUser1 = sc.next();

                                switch (answerUser1) {
                                    case "1":
                                        bank.showAccount();
                                        break;

                                    case "2":
                                        bank.printFile();
                                        break;

                                    case "3":
                                        bank.deposit();
                                        bank.printDate();
                                        System.out.println();
                                        break;

                                    case "4":
                                        bank.withdrawal();
                                        bank.printDate();
                                        System.out.println();
                                        break;

                                    case "5":
                                        System.out.println("Enter the account number to transfer money to: ");
                                        String destinationAccountNumber = sc.next();
                                        Bank destinationAccount = Bank.findDestinationAccount(destinationAccountNumber);
                                        if (destinationAccount != null) {
                                            currentAccount.transfer(destinationAccount);
                                        } else {
                                            System.out.println("Destination account not found.");
                                        }
                                        break;

                                    case "6":
                                        currentAccount.requestLoan();
                                        break;

                                    case "7":
                                        break;
                                }

                                System.out.print("\nDo you want to continue (y/n) --> ");
                                String exit = sc.next();

                                if (exit.equalsIgnoreCase("y")) {
                                } else {
                                    break;
                                }
                            }
                            System.out.println();
                            bank.ExitMessage();

                            System.out.print("DO You Want To Reset File Y - N: \n");
                            String fileReset = sc.next();

                            if (fileReset.equalsIgnoreCase("y")) {
                                bank.ResetFile();
                            }
                        }
                    }
            }
        }
        // Close the socket when you're done with it
        s.close();
    }
	/*		//not sure
		private static Bank findDestinationAccount(String accountNumber) {
        File file = new File("C:\\Users\\m7md\\OneDrive - SFC Hackathon\\Desktop\\" + accountNumber + ".txt");
        return Bank.parseAccountDetailsFromFile(file);

}
*/
}