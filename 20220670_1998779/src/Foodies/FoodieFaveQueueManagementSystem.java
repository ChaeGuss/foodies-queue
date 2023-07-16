package Foodies;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

import static Foodies.FoodQueue.addNewCustomer;

public class FoodieFaveQueueManagementSystem {
    private static final Scanner in = new Scanner(System.in);
    private static final int[] qMax = { 2, 3, 5 };
    private static final int maxBurgerStock = 50;

    private static final int priceOfBurger = 650;
    private static String[][] queues;

    public static void main(String[] args) throws InterruptedException, IOException {
        setQueues();

        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            Menu();
            String option = scanner.nextLine();

            if (option.equals("100") || option.equals("VFQ")) {
                viewAllQueues();
            } else if (option.equals("101") || option.equals("VEQ")) {
                viewAllEmptyQueues();
            } else if (option.equals("102") || option.equals("ACQ")) {
                addCustomer();
            } else if (option.equals("103") || option.equals("RCQ")) {
                removeCustomer();
            } else if (option.equals("104") || option.equals("PCQ")) {
                removeServedCustomer();
            } else if (option.equals("105") || option.equals("VCS")) {
                viewCustomersSortedAlphabetically();
            } else if (option.equals("106") || option.equals("SPD")) {
                storeProgramDataToFile();
            } else if (option.equals("107") || option.equals("LPD")) {
                loadProgramDataFromFile();
            } else if (option.equals("108") || option.equals("STK")) {
                viewRemainingBurgerStock();
            } else if (option.equals("109") || option.equals("AFS")) {
                addBurgersToStock(scanner);
            } else if (option.equals("110") || option.equals("IFQ")) {
                viewIncomeOfEachQueue();
            } else if (option.equals("999") || option.equals("EXT")) {
                System.out.println("Exiting Foodies Fave Food Center");
                exit = true;
            } else {
                System.out.println("Invalid");
            }
        }
    }

    private static void setQueues() {
        queues = new String[qMax.length][];

        for (int i = 0; i < qMax.length; i++) {
            queues[i] = new String[qMax[i]];
        }
    }

    private static void Menu() {
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue.");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order.");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("110 or IFQ: View Income of each queue");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your choice: ");
    }

    private static void viewAllQueues() {
        System.out.println("Cashier Queues:");
        System.out.println("********************");
        System.out.println("*     Cashiers     *");
        System.out.println("********************");
        for (int i = 0; i < qMax.length; i++) {
            for (int j = 0; j < qMax[i]; j++) {
                if (j < queues[i].length) {
                    if (queues[i][j] != null) {
                        if (false) {
                            System.out.print("- ");
                        } else {
                            System.out.print("O ");
                        }
                    } else {
                        System.out.print("X ");
                    }
                } else {
                    System.out.print("X ");
                }
            }
            System.out.println();
        }
    }
    public static void viewAllEmptyQueues() {

    }

    public static String getName(){
        Scanner input = new Scanner(System.in);

        System.out.print("Enter customer's first name: ");
        String firstName = input.next();

        System.out.print("Enter customer's first name: ");
        String lastName = input.next();

        String name;
        //validating if name not a number
        while(true){
            name = in.next();
            try{
                if(Integer.parseInt(name) >= 0 || Integer.parseInt(name) <= 0)
                    throw new InputMismatchException();
            }
            catch (NumberFormatException ne){
                break;
            }
            catch (InputMismatchException ie){
                System.out.println("Text required");
            }

        }
        return name;
    }

    public static void addCustomer() {
        Scanner input = new Scanner(System.in);
        int n = getQueueNumber();
        String name = getName();

        System.out.print("Enter number of Burgers: ");
        int numOfBurgers = input.nextInt();

        Customer Customer = new Customer(name, numOfBurgers);

        if (n == 1) {
            if (addNewCustomer(name, queues[0])) {
                System.out.println(name + " is in queue 1");
                getBurgerStock();
            }
            else
                System.out.println("Queue 1 is full");
        }
        else if (n == 2) {
            if (addNewCustomer(name, queues[1])) {
                System.out.println(name + " is in queue 2");
                getBurgerStock();
            }
            else
                System.out.println("Queue 2 is full");
        }
        else{
            if (addNewCustomer(name, queues[2])) {
                System.out.println(name + " is in queue 3");
                getBurgerStock();
            }
            else
                System.out.println("Queue 3 is full");
        }
        System.out.println();
    }


    public static void removeCustomer() {
        int queue = getQueueNumber();
        int slot = getQueueSlot();
        if (queue == 1) {
            removeFromArray(slot, queues[1],1);
            getBurgerStock();
        }
        if (queue == 2) {
            removeFromArray(slot, queues[2],2);
            getBurgerStock();

        }
        if (queue == 3) {
            removeFromArray(slot, queues[3],3);
            getBurgerStock();
        }
        System.out.println();

    }

    public static void removeFromArray(int slot, String[] array,int que) {
        if(array[slot]==null)
            System.out.println("Already empty slot");
        else{
            for(int i = slot; i < array.length; i++) {
                if (i != (array.length - 1))
                    array[i] = array[i + 1];
                else
                    array[i] = null;
            }
            System.out.println(getName() + "removed from queue " + que);
        }
    }

    public static void removeServedCustomer(){
        int num = getQueueNumber();
        if (num == 1)
            removeServedArray(queues[1]);
        else if (num == 2)
            removeServedArray(queues[2]);
        else if (num == 3)
            removeServedArray(queues[3]);
        System.out.println();

    }

    public static void removeServedArray(String[] array) {
        for (int i = 0; i < array.length; i++) {
            if (i != (array.length - 1))
                array[i] = array[i + 1];
            else
                array[i] = null;
        }
        System.out.println("Served Customer removed successfully");
    }

    private static void viewCustomersSortedAlphabetically() throws InterruptedException, IOException {
        clearConsole();
        String[] totalCustomers = new String[getTotCustomers()];

        int position = 0;
        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    totalCustomers[position++] = customer;
                }
            }
        }

        if (position == 0) {
            System.out.println("No customers in the queues.");
            return;
        }

        Arrays.sort(totalCustomers);

        System.out.println("Customers Sorted in alphabetical order:");
        for (String customer : totalCustomers) {
            System.out.println(customer);
        }
    }

    private static void storeProgramDataToFile() throws InterruptedException, IOException {
        clearConsole();

        try {
            PrintWriter myWriter = new PrintWriter("data.txt");
            for (int i = 0; i < qMax.length; i++) {
                myWriter.println("Foodies Fave Food Center");
                myWriter.print("Cashier " + i + ": ");
                for (int j = 0; j < qMax[i]; j++) {
                    if (queues[i][j] != null) {
                        myWriter.print("O ");
                    } else {
                        myWriter.print("X ");
                    }
                }
                myWriter.println();
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }

    private static void loadProgramDataFromFile() throws InterruptedException, IOException {
        clearConsole();
        try {
            FileReader reader = new FileReader("data.txt");
            int data = reader.read();
            while (data != -1) {
                System.out.print((char) data);
                data = reader.read();
            }
            reader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void viewRemainingBurgerStock() {
        System.out.println("Remaining stock: " + getRemainingBurgers());
    }

    private static void addBurgersToStock(Scanner scanner) throws InterruptedException, IOException {
        System.out.print("Enter the number of burgers: ");
        int numOfBurgers = scanner.nextInt();
        scanner.nextLine();

        if (numOfBurgers <= 0) {
            System.out.println("Invalid number of burgers.");
            return;
        }

        updateStock(numOfBurgers);

        System.out.println("Added " + numOfBurgers + " burgers.");
    }

    private static int getRemainingBurgers() {
        int servedBurgers = maxBurgerStock - getBurgerStock();
        return Math.max(0, servedBurgers);
    }

    private static int getBurgerStock() {
        int stock = 4; //Assuming a customer buys 5 burgers
        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    stock++;
                }
            }
        }
        return stock;
    }

    private static void updateStock(int quantity) throws IOException, InterruptedException {
        clearConsole();
        int currentBurgerStock = getBurgerStock();
        int newStock = currentBurgerStock + quantity;

        if (newStock < 0) {
            newStock = 0;
        } else if (newStock > maxBurgerStock) {
            newStock = maxBurgerStock;
        }

        int remainingStock = newStock - currentBurgerStock;

        if (remainingStock < 0) {
            removeCustomersFromQueues(-remainingStock);
        } else if (remainingStock > 0) {
            addCustomer();
        }
    }

    private static void viewIncomeOfEachQueue(){
        System.out.println("Price Of a Burger: $" + priceOfBurger);
        for (int i = 0; i < qMax.length; i++) {
            int queueIncome = queues[i].getTotalBurgers() * priceOfBurger;
            System.out.println("Income of Queue " + (i + 1) + ": $" + queueIncome);
        }
    }

    private static void removeCustomersFromQueues(int numCustomers) {
        int remCustomers = numCustomers;
        for (int i = queues.length - 1; i >= 0; i--) {
            String[] queue = queues[i];
            for (int j = queue.length - 1; j >= 0; j--) {
                if (queue[j] != null) {
                    queue[j] = null;
                    remCustomers--;
                    if (remCustomers == 0) {
                        return;
                    }
                }
            }
        }
    }
    public static int numValidation(){
        int num;
        while(true){
            String temp = in.next();
            try{
                num = Integer.parseInt(temp);
                break;
            }
            catch (Exception e){
                System.out.print("Please enter a number: ");
            }
        }
        return num;
    }
    public static int getQueueSlot(){
        System.out.print("Enter slot number: ");
        int slot = numValidation();
        while(slot<1||slot>6){
            System.out.println("Please enter slot between 1-6: ");
            slot = numValidation();
        }
        return (slot-1);
    }

    public static int getQueueNumber(){
        System.out.print("Enter queue number ( 1 or 2 or 3 ):");
        int qnum = numValidation();

        while(qnum<1 || qnum>3){
            System.out.print("Invalid queue number.Please enter 1 or 2 or 3: ");
            qnum = numValidation();
        }
        return qnum;
    }

    private static void clearConsole() throws InterruptedException, IOException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    private static int getTotCustomers() {
        int totCustomers = 0;
        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    totCustomers++;
                }
            }
        }
        return totCustomers;
    }

    private static int getShortestQueueIndex() {
        int shortestQIndex = -1;
        int shortestQLength = Integer.MAX_VALUE;

        for (int i = 0; i < queues.length; i++) {
            int queueLength = queues[i].getQueueLength();
            if (queueLength < qMax[i] && queueLength < shortestQLength) {
                shortestQIndex = i;
                shortestQLength = queueLength;
            }
        }

        return shortestQIndex;
    }


}
