package Bed_reserve;

import java.util.Scanner;

public class GetBedInfo {
    private int normalVacant;
    private int normalOccupied;
    private int normalTotal;
    private int icuVacant;
    private int icuOccupied;
    private int icuTotal;
    private int oxygenVacant;
    private int oxygenOccupied;
    private int oxygenTotal;
    private int ventilatorVacant;
    private int ventilatorOccupied;
    private int ventilatorTotal;
    Scanner sc = new Scanner(System.in);

    public void getBedDetails() {
        System.out.println("\nKindly enter the following bed details...");
        System.out.println("\n\tNormal Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        normalVacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        normalOccupied = sc.nextInt();
        normalTotal = normalVacant + normalOccupied;
        System.out.println("\n\tOxygen Supported Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        oxygenVacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        oxygenOccupied = sc.nextInt();
        oxygenTotal = oxygenVacant + oxygenOccupied;
        System.out.println("\n\tICU Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        icuVacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        icuOccupied = sc.nextInt();
        icuTotal = icuVacant + icuOccupied;
        System.out.println("\n\tVentilator Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        ventilatorVacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        ventilatorOccupied = sc.nextInt();
        ventilatorTotal = ventilatorVacant + ventilatorOccupied;
    }

    public BedDetailsContainer getNormal() {
        BedDetailsContainer normal = new BedDetailsContainer(normalVacant, normalOccupied, normalTotal);
        return normal;
    }

    public BedDetailsContainer getIcu() {
        BedDetailsContainer icu = new BedDetailsContainer(icuVacant, icuOccupied, icuTotal);
        return icu;
    }

    public BedDetailsContainer getOxygen() {
        BedDetailsContainer oxygen = new BedDetailsContainer(oxygenVacant, oxygenOccupied, oxygenTotal);
        return oxygen;
    }

    public BedDetailsContainer getVentilator() {
        BedDetailsContainer ventilator = new BedDetailsContainer(ventilatorVacant, ventilatorOccupied, ventilatorTotal);
        return ventilator;
    }

}
