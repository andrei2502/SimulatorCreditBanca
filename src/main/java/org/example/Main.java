package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final int MONTH_IN_YEAR = 12;
    static  final int PERCENT = 100;
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        CsvWriter csvWriter;

        try{
            FileWriter writer = new FileWriter(FileProvider.getFile());
            csvWriter = new CsvWriter(writer);
            csvWriter.writeHeader();
        }catch (IOException e){
            System.out.println("Some error occured when initializing the CsvWriter: " + e.getMessage());
            return;
        }

        int amount;
        System.out.print("Please enter the amount: ");
        try{
            amount = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("The amount is to be numeric!");
            return;
        }

        int period;
        System.out.print("Please enter the period in year: ");
        try{
            period = Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("The period is to be numeric!");
            return;
        }

        double interestRate;
        System.out.print("Please enter the interest rate:");
        try{
            interestRate = Double.parseDouble(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("The interest rate is to be numeric!");
            return;
        }

        double balance = amount;
        for(int month=1; month<=period * MONTH_IN_YEAR; month++){
            double lastMonthBalance = balance;
            double monthlyMortgage = calculateMortgage(amount, period, interestRate);
            double monthlyInterest = calculateInterest(lastMonthBalance, interestRate);
            double paidAmount = monthlyMortgage - monthlyInterest;

            balance = (lastMonthBalance - paidAmount) < 0 ? 0 : (lastMonthBalance - paidAmount);

//            System.out.println("Month: " + month +
//                    " | Mortage: " + monthlyMortgage +
//                    " | Interest: " + monthlyInterest +
//                    "  | Paid Amount: " + paidAmount +
//                    "  | Balace: " + balance);
            try {
                csvWriter.writeRecord(month, monthlyMortgage, balance, monthlyInterest, paidAmount);
            }catch (IOException e){
                System.out.println("Error while writing the csv file: " + e.getMessage());
            }
        }

        try {
            csvWriter.closeFile();
        }catch (IOException e){
            System.out.println("Something went wrong when trying to close file: " + e.getMessage());
        }
    }

    //Rata lunara
    private static double calculateMortgage(int amount, int period, double interestRate){
        double monthlyRate = interestRate / PERCENT / MONTH_IN_YEAR;
        return (monthlyRate * amount) / (1 - Math.pow(1 + monthlyRate, (-period * MONTH_IN_YEAR)));
    }

    //Dobanda pe o luna
    private static double calculateInterest(double balance, double interestRate){
        double interestPerYear = balance * interestRate / PERCENT;
        return interestPerYear / MONTH_IN_YEAR;
    }
}