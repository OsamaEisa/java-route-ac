package osama.assignment;

import java.util.Scanner;

public class MainWeek02 {

  // FIXME (next step):
  // The program assumes that the user will enter valid numbers for the PIN, menu options and for the amounts, and does not handle invalid input.

  static final String ACCOUNT_STATUS_VIP = "VIP Customer";
  static final String ACCOUNT_STATUS_REG = "Regular Customer";
  static final String ACCOUNT_STATUS_LOW = "Low Balance";
  static final short ACCOUNT_STATUS_VIP_THRESHOLD = 5000;
  static final short ACCOUNT_STATUS_REG_THRESHOLD = 1000;
  static final short CORRECT_PIN = 1234;
  static final byte MAX_NUM_OF_ATTEMPTS = 3;
  static final byte NUM_TO_EXIT = 5;

  public static void main(String[] args) {

    final Scanner scanner = new Scanner(System.in);

    byte userNumOfTries= 1;
    double customerBalance = 2500.75;

    short userInputPin;
    do {
      System.out.println("Please enter a 4-digit PIN: ");

      if (!scanner.hasNextShort()) {
        System.out.println("Please enter valid numbers");
        scanner.next();
      }
        userInputPin = scanner.nextShort();

      if (userNumOfTries == MAX_NUM_OF_ATTEMPTS) {
        System.out.println("Your account has been locked. You have exceeded the maximum number of attempts.");
        break;
      }
      userNumOfTries++;
    } while (userInputPin != CORRECT_PIN);


    if (userInputPin == CORRECT_PIN) {

      byte userInputChoice = 0;
      while (userInputChoice != NUM_TO_EXIT) {
        System.out.printf("""
                ========= ATM =========
                1. Check Balance
                2. Deposit
                3. Withdraw
                4. Show Account Status
                5. Exit
                =======================%n""");

        System.out.println("Please select an option: ");
        if (!scanner.hasNextByte()) {
          System.out.println("Please enter a valid number: ");
          scanner.next();
        }
        userInputChoice = scanner.nextByte();

        switch (userInputChoice) {
          case 1 -> System.out.println("Your balance is: " + customerBalance);

          case 2 -> {
            System.out.println("Enter the amount to deposit: ");
            double depositAmount = scanner.nextDouble();
            if (depositAmount <= 0) {
              System.out.println("Please enter a valid amount greater than 0: ");
            }
            customerBalance += depositAmount;
            System.out.println("Your new balance is: " + customerBalance);
          }

          case 3 -> {
            System.out.println("Enter the amount to withdraw: ");
            if (!scanner.hasNextDouble()) {
              System.out.println("Please enter a valid amount: ");
            }
            double withdrawAmount = scanner.nextDouble();
            if (withdrawAmount < 0 || withdrawAmount > customerBalance) {
              System.out.println("Insufficient balance.");
            } else {
              withdrawAmount = scanner.nextDouble();
              customerBalance -= withdrawAmount;
              System.out.println("Your new balance is: " + customerBalance);
              if (customerBalance == 0) {
                System.out.println("Warning: Your account is empty.");
              }
            }
          }

          case 4 -> {
            if (customerBalance >= ACCOUNT_STATUS_VIP_THRESHOLD) {
              System.out.println("Account Status: " + ACCOUNT_STATUS_VIP);
            } else if (customerBalance < ACCOUNT_STATUS_VIP_THRESHOLD && customerBalance >= ACCOUNT_STATUS_REG_THRESHOLD) {
              System.out.println("Account Status: " + ACCOUNT_STATUS_REG);
            } else {
              System.out.println("Account Status: " + ACCOUNT_STATUS_LOW);
            }
          }
          case 5 -> System.out.println("Thank you for using our ATM.");
          default -> System.out.println("Invalid option. Please try again...");
        }
      }
    }

  }


}
