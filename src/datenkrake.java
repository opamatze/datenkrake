import java.sql.*;
import java.util.Random;

public class datenkrake {

    public final static String host = "jdbc:mysql://localhost:3306/datenkrakedb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public final static String uName = "root";
    public final static String uPass = "";

    public datenkrake() {
    }

    public static void main(String[] args) throws SQLException {

        System.out.println("Datenkrake start.");

        try {
            Connection con = DriverManager.getConnection(host, uName, uPass);

            ResultSet result;
            result = fireStatement(con,"SELECT COUNT(`wort`) AS rowcount FROM wortliste","select");
            result.next();
            int resultSize = result.getInt("rowcount");
            result = fireStatement(con,"SELECT wort FROM wortliste", "select");

            int firstWordPosition = (int) (Math.random() * resultSize);
            int secondWordPosition = (int) (Math.random() * resultSize);
            int iterator = 0;
            String neuesWort = "";

            while (result.next()) {
                String currentWort = result.getString("wort");
                if(iterator == firstWordPosition || iterator == secondWordPosition) {
                    neuesWort+=currentWort;
                }
                System.out.println(currentWort);

                iterator++;
            }

            System.out.println("Nächstes einzufügendes Wort: "+ neuesWort);

            result = fireStatement(con,"INSERT INTO `wortliste` (`wort`) VALUES ('" + neuesWort + "') ","update");

            finishConnection(con);


        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }

    }

    private static void finishConnection(Connection con) throws SQLException {
        con.close();
    }
    private static ResultSet fireStatement(Connection con, String sqlStatement, String type) throws SQLException {
        Statement stmt = con.createStatement();
        ResultSet rs = null;
        if(type.equals("update")){
            stmt.executeUpdate(sqlStatement);
        } else if(type.equals("select")) {
            rs = stmt.executeQuery(sqlStatement);
        }
        return rs;
    }
}
