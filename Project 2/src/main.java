import java.sql.*;
     
public class main  {

  public static void main(String args[]) {
	System.out.println("Query 1");
    runSQL("Select dept_name,\r\n"
    		+ "	  avg(if(employees.gender = 'F', salaries.salary,0)) /avg(if(employees.gender = 'M', salaries.salary,0)) as 'Ratio'\r\n"
    		+ "	  from employees inner join dept_emp on employees.emp_no = dept_emp.emp_no\r\n"
    		+ "	  inner join salaries on salaries.emp_no = employees.emp_no\r\n"
    		+ "	  join departments on  departments.dept_no = dept_emp.dept_no\r\n"
    		+ "	  group by dept_name\r\n"
    		+ "	  order by ratio desc; ");
    
    System.out.println("Query 2");
    runSQL("Select employees.first_name,employees.last_name, datediff(dept_manager.to_date,dept_manager.from_date) as 'Duration'\r\n"
    		+ "From dept_manager join employees\r\n"
    		+ "where dept_manager.emp_no = employees.emp_no\r\n"
    		+ "Order by Duration Desc;\r\n"
    		+ "");
    
    System.out.println("Query 3");
    runSQL("SELECT DISTINCT departments.dept_name, count(employees.emp_no), AVG(salaries.salary),\r\n"
    		+ "ROUND(YEAR(employees.birth_date), -1) AS birth_date \r\n"
    		+ "FROM employees\r\n"
    		+ "JOIN salaries ON employees.emp_no = salaries.emp_no\r\n"
    		+ "JOIN dept_emp ON dept_emp.emp_no = employees.emp_no \r\n"
    		+ "JOIN departments ON dept_emp.dept_no = departments.dept_no\r\n"
    		+ "GROUP BY departments.dept_name, ROUND(YEAR(employees.birth_date), -1);\r\n"
    		+ "");
    
    System.out.println("Query 4");
    runSQL("Select employees.first_name, employees.last_name, birth_date, salary, gender\r\n"
    		+ "From employees join salaries on employees.emp_no = salaries.emp_no\r\n"
    		+ "join dept_manager on dept_manager.emp_no = employees.emp_no\r\n"
    		+ "and salary>80000 and gender = 'F' and birth_date < cast('1990-01-01' as date)\r\n"
    		+ "group by first_name;\r\n"
    		+ "");
    
    
    
  
  
}


public static void runSQL(String query) {
	String url = "jdbc:mysql://localhost:3306/employees";
    Connection con;
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
	      System.out.println();
	      stmt.close();
	      con.close();
	    } catch(SQLException ex) {
	      System.err.print("SQLException: ");
	      System.err.println(ex.getMessage());
	    }  
	  }
}

	



