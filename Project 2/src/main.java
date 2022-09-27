import java.sql.*;
import java.util.Scanner;
     
public class main  {

  public static void main(String args[]) {
      
    String url = "jdbc:mysql://localhost:3306/employees";
    Connection con;
	System.out.println("Enter SQL Query: "); // Do not press enter for new line. Pressing enter will submit the query.
    Scanner scan = new Scanner(System.in);
    
    String query = scan.nextLine();
    Statement stmt;
  
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
  
    } catch(java.lang.ClassNotFoundException e) {
      System.err.print("ClassNotFoundException: "); 
      System.err.println(e.getMessage());
    }

    try {
      con = DriverManager.getConnection(url, 
                   "root", "Dyc30600.1"); // Enter your server credentials
  
      stmt = con.createStatement();              
  
      ResultSet rs = stmt.executeQuery(query);
      ResultSetMetaData rsmd = rs.getMetaData();
  
      System.out.println("");
  
      int numberOfColumns = rsmd.getColumnCount();
  
      for (int i = 1; i <= numberOfColumns; i++) {
        if (i > 1) System.out.print(",  ");
        String columnName = rsmd.getColumnName(i);
        System.out.print(columnName);
      }
      System.out.println("");
  
      while (rs.next()) {
        for (int i = 1; i <= numberOfColumns; i++) {
          if (i > 1) System.out.print(",  ");
          String columnValue = rs.getString(i);
          System.out.print(columnValue);
        }
        System.out.println("");  
      }
  
      stmt.close();
      con.close();
    } catch(SQLException ex) {
      System.err.print("SQLException: ");
      System.err.println(ex.getMessage());
    }  
  }
}
	

