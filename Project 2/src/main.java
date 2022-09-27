import java.sql.*;
import java.util.Scanner;
     
public class main  {

  public static void main(String args[]) {
/* Queries 
1. Select dept_name,
avg(if(employees.gender = 'F', salaries.salary,0)) /avg(if(employees.gender = 'M', salaries.salary,0)) as 'Ratio'
from employees inner join dept_emp on employees.emp_no = dept_emp.emp_no
inner join salaries on salaries.emp_no = employees.emp_no
join departments on  departments.dept_no = dept_emp.dept_no
group by dept_name
order by ratio desc;

2. Select employees.first_name,employees.last_name, datediff(dept_manager.to_date,dept_manager.from_date) as 'Duration'
From dept_manager join employees
where dept_manager.emp_no = employees.emp_no
Order by Duration Desc;

3. SELECT DISTINCT departments.dept_name, count(employees.emp_no), AVG(salaries.salary),
ROUND(YEAR(employees.birth_date), -1) AS birth_date 
FROM employees
JOIN salaries ON employees.emp_no = salaries.emp_no
JOIN dept_emp ON dept_emp.emp_no = employees.emp_no 
JOIN departments ON dept_emp.dept_no = departments.dept_no
GROUP BY departments.dept_name, ROUND(YEAR(employees.birth_date), -1);

4. Select employees.first_name, employees.last_name, birth_date, salary, gender
From employees join salaries on employees.emp_no = salaries.emp_no
join dept_manager on dept_manager.emp_no = employees.emp_no
and salary>80000 and gender = 'F' and birth_date < cast('1990-01-01' as date)
group by first_name;




	   
*/
	  
      
    String url = "jdbc:mysql://localhost:3306/employees";
    Connection con;
	System.out.println("Enter SQL Query: "); // Do not press enter for new line. Pressing enter will submit the query.
    Scanner scan = new Scanner(System.in);
    
    String query = " "; // Copy and paste queries here!
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
	

