package Bed_reserve;

import java.util.Scanner;

public class Update_bed_details {
    private int n_vacant;
    private int n_occupied;
    private int n_total;
    private int i_vacant;
    private int i_occupied;
    private int i_total;
    private int o_vacant;
    private int o_occupied;
    private int o_total;
    private int v_vacant;
    private int v_occupied;
    private int v_total;
    Scanner sc = new Scanner(System.in);

    public void get_bed_details() {
        System.out.println("\nKindly enter the following bed details...");
        System.out.println("\n\tNormal Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        n_vacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        n_occupied = sc.nextInt();
        n_total = n_vacant + n_occupied;
        System.out.println("\n\tOxygen Supported Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        o_vacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        o_occupied = sc.nextInt();
        o_total = o_vacant + o_occupied;
        System.out.println("\n\tICU Bed Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        i_vacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        i_occupied = sc.nextInt();
        i_total = i_vacant + i_occupied;
        System.out.println("\n\tVentilator Details:-");
        System.out.print("\t\tEnter the NO. of Vacant beds   : ");
        v_vacant = sc.nextInt();
        System.out.print("\t\tEnter the NO. of Occupied beds : ");
        v_occupied = sc.nextInt();
        v_total = v_vacant + v_occupied;
    }

    public Bed_details getNormal() {
        Bed_details normal = new Bed_details(n_vacant, n_occupied, n_total);
        return normal;
    }

    public Bed_details getIcu() {
        Bed_details icu = new Bed_details(i_vacant, i_occupied, i_total);
        return icu;
    }

    public Bed_details getOxygen() {
        Bed_details oxygen = new Bed_details(o_vacant, o_occupied, o_total);
        return oxygen;
    }

    public Bed_details getVentilator() {
        Bed_details ventilator = new Bed_details(v_vacant, v_occupied, v_total);
        return ventilator;
    }

}
