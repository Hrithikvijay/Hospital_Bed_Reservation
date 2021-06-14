package Bed_reserve;
public class InjectDatabase{
    private static Database database=Database.getInstance();
    private static GovernmentDb governmentDb=GovernmentDb.getInstance();

    public static Database getDatabase() {
        return database;
    }
    public static GovernmentDb getGovernmentDb() {
        return governmentDb;
    }
}