package osama.assignment;

import java.util.Scanner;

public class MainWeek02WithFunctions {

  // FIXME (next step):
  //  The program assumes that the user will enter valid numbers for the PIN, menu options and for the amounts, and does not handle invalid input.

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

    byte userNumOfTries = 1;
    double customerBalance = 2500.75;
    short userInputPin;

    // Get user PIN
    do {
      printDigitPinMsg();
      userInputPin = scanner.nextShort();
      if (userNumOfTries == MAX_NUM_OF_ATTEMPTS) {
        printAccLockedMsg();
        break;
      }
      userNumOfTries++;
    } while (userInputPin != CORRECT_PIN);

    // User PIN is correct? show menu
    if (userInputPin == CORRECT_PIN) {

      byte userInputChoice = 0;
      while (userInputChoice != NUM_TO_EXIT) {

        printMenuMsg();
        userInputChoice = scanner.nextByte();

        switch (userInputChoice) {
          case 1 -> printBalance(customerBalance);

          case 2 -> {
            printAmountToDepOrWithdrawMsg("deposit");
            double depositAmount = scanner.nextDouble();
            if (depositAmount <= 0) {
              printInvalidDepositAmountMsg();
            } else {
              customerBalance = deposit(customerBalance, depositAmount);
              printBalance(customerBalance);
            }
          }

          case 3 -> {
            printAmountToDepOrWithdrawMsg("withdraw");
            double withdrawAmount = scanner.nextDouble();
            if (withdrawAmount == 0) {
              printTransactionCancelledMsg();
            } else if (isDepositAmountNotValid(withdrawAmount, customerBalance)) {
              printInsufficientBalanceMsg();
            } else {
              customerBalance = withdraw(customerBalance, withdrawAmount);
              printBalance(customerBalance);
              if (customerBalance == 0) {
                printBalanceWarningMsg();
              }
            }
          }

          case 4 -> printAccountStatus(getAccountStatus(customerBalance));
          case 5 -> printExitMsg();
          default -> printInvalidOptionMsg();
        }
      }
    }
  }


  private static String getAccountStatus(double customerBalance) {
    if (customerBalance >= ACCOUNT_STATUS_VIP_THRESHOLD) {
      return ACCOUNT_STATUS_VIP;
    } else if (customerBalance >= ACCOUNT_STATUS_REG_THRESHOLD) {
      return ACCOUNT_STATUS_REG;
    }
    return ACCOUNT_STATUS_LOW;
  }

  private static double deposit(double balance, double amount) {
    balance += amount;
    return balance;
  }

  private static double withdraw(double balance, double amount) {
    balance -= amount;
    return balance;
  }

  private static boolean isDepositAmountNotValid(final double amount, final double balance) {
    return amount < 0 || amount > balance;
  }

  private static void printMenuMsg() {
    System.out.printf("""
            ========= ATM =========
            1. Check Balance
            2. Deposit
            3. Withdraw
            4. Show Account Status
            5. Exit
            =======================%n""");
    printSelectOptionMsg();
  }

  private static void printSelectOptionMsg() {
    System.out.println("Please select an option: ");
  }

  private static void printDigitPinMsg() {
    System.out.println("Please enter a 4-digit PIN: ");
  }

  private static void printAmountToDepOrWithdrawMsg(final String depoOrWithdraw) {
    System.out.println("Enter the amount to " + depoOrWithdraw + ": ");
  }

  private static void printInvalidDepositAmountMsg() {
    System.out.println("Please enter a valid amount greater than 0 and try again...");
  }

  private static void printExitMsg() {
    System.out.println("Thank you for using our ATM.");
  }

  private static void printInvalidOptionMsg() {
    System.out.println("Invalid option. Please try again...");
  }

  private static void printInsufficientBalanceMsg() {
    System.out.println("Invalid amount or Insufficient balance. Please try again...");
  }

  private static void printAccLockedMsg() {
    System.out.println("Your account has been locked. You have exceeded the " + MAX_NUM_OF_ATTEMPTS + " attempts you have.");
  }

  private static void printBalanceWarningMsg() {
    System.out.println("Warning: Your account is empty!");
  }

  private static void printAccountStatus(final String status) {
    System.out.println("Account Status: " + status);
  }

  private static void printBalance(final double balance) {
    System.out.println("Your current balance is: " + balance);
  }

  private static void printTransactionCancelledMsg() {
    System.out.println("Invalid amount to withdraw. Transaction cancelled...");
  }

}
