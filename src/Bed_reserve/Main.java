package Bed_reserve;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("----------Welcome to the application----------");
        while (true) {
            System.out.println("\nPlease select the mode of login : ");
            System.out.println("\n\tFor User Mode     : please enter 1 ");
            System.out.println("\n\tFor Hospital Mode : please enter 2 ");
            System.out.println("\n\t10.Exit");
            System.out.print("\nEnter your choice : ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                // user mode
                UserMode userMode = new UserMode();
                userMode.userMode(InjectDatabase.getDatabase(), InjectDatabase.getDatabase(),
                        InjectDatabase.getGovernmentDb());
            } else if (choice.equals("2")) {
                // Hospital mode
                HospitalMode hospitalMode = new HospitalMode();
                hospitalMode.hospitalMode(InjectDatabase.getDatabase(), InjectDatabase.getDatabase(),
                        InjectDatabase.getDatabase(), InjectDatabase.getGovernmentDb());
            }
            else if(choice.equals("10")){
                //Exit
                System.out.println("\n\t----------Thank You for Using Our Application----------");
                break;
            }
            else {
                System.out.println("\nPlease enter the valid choice");
            }
        }
    }
}
