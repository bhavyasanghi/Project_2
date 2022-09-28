import java.sql.*;		
import java.util.*;
public class main  {
	public static String e1;
	public static String e2;
  public static void main(String args[]) {
	Scanner sc = new Scanner(System.in);			//scans user input 
	int input = 0;
	while(input !=10){					//Loops through print statements until 10 is taken in as input 
		System.out.println("Which Query do you want to test? (1-6)");
		System.out.println("Enter 10 to exit");
	input = sc.nextInt();
	switch(input)						//switch cases for user to choose from 6 different queries and to exit 
	{
		case 1:						//case for Query 1 
			System.out.println("Query 1");		//Contributor: Bhavya Sanghi
    		runSQL("Select dept_name,\r\n"			//SQL query that gives the department with the maximum ratio of female to male salaries
    		+ "	  avg(if(employees.gender = 'F', salaries.salary,0)) /avg(if(employees.gender = 'M', salaries.salary,0)) as 'Ratio'\r\n"
    		+ "	  from employees inner join dept_emp on employees.emp_no = dept_emp.emp_no\r\n"
    		+ "	  inner join salaries on salaries.emp_no = employees.emp_no\r\n"
    		+ "	  join departments on  departments.dept_no = dept_emp.dept_no\r\n"
    		+ "	  group by dept_name\r\n"
    		+ "	  order by ratio desc Limit 1; ");
			break;
		case 2:						//case for Query 2: lists the manager who held office the longest duration
			System.out.println("Query 2");		//Contributor: David Chen 
			runSQL("Select employees.first_name,employees.last_name, datediff(dept_manager.to_date,dept_manager.from_date) as 'Duration'\r\n"
				+ "From dept_manager join employees\r\n"
				+ "where dept_manager.emp_no = employees.emp_no\r\n"
				+ "Order by Duration Desc Limit 1;\r\n"
				+ "");
			break;
		case 3:						//case for Query 3: list of employees born in each decade and their salaries 
			System.out.println("Query 3");		//Contributor: David Chen
			runSQL("SELECT DISTINCT departments.dept_name, count(employees.emp_no) AS Count, AVG(salaries.salary) AS Average_Salary,\r\n"
				+ "ROUND(YEAR(employees.birth_date), -1) AS Decade \r\n"
				+ "FROM employees\r\n"
				+ "JOIN salaries ON employees.emp_no = salaries.emp_no\r\n"
				+ "JOIN dept_emp ON dept_emp.emp_no = employees.emp_no \r\n"
				+ "JOIN departments ON dept_emp.dept_no = departments.dept_no\r\n"
				+ "GROUP BY departments.dept_name, ROUND(YEAR(employees.birth_date), -1);\r\n"
				+ "");
				break;
		case 4:						//case for Query 4: list of employees born before 1990 and make more than 80K
			System.out.println("Query 4");		//Contributor: Lakshmi Yetukuri
			runSQL("Select employees.first_name, employees.last_name, birth_date, salary, gender\r\n"
				+ "From employees join salaries on employees.emp_no = salaries.emp_no\r\n"
				+ "join dept_manager on dept_manager.emp_no = employees.emp_no\r\n"
				+ "and salary>80000 and gender = 'F' and birth_date < cast('1990-01-01' as date)\r\n"
				+ "group by first_name;\r\n"
				+ "");
				break;
		case 5:						//case for Query 5: 1 degree of seperation between 2 employees 
			System.out.println("Query 5");		//Contributor: Bhavya Sanghi

			System.out.println("Give Employee 1 id: ");
			e1 = sc.next();
			System.out.println("Give Employee 2 id: ");
			e2 = sc.next();
			//e1 = "10009";
			//e2 = "11545";
			String query = "Select dept_no From employees inner join dept_emp on employees.emp_no = dept_emp.emp_no where employees.emp_no = E1 and dept_emp.dept_no in (Select dept_no from dept_emp inner join employees on employees.emp_no = dept_emp.emp_no where employees.emp_no = E2) limit 100;";

			query = query.replace("E1",e1);
			query = query.replace("E2",e2);
			query5(query);
			break;
		case 6:						//case for Query 6: 2 degrees of seperation between 2 employees
			System.out.println("Query 6:");		//Contributors: Bhavya Sanghi and Lakshmi Yetukuri
			System.out.println("Give Employee 1 id: ");
			e1 = sc.next();
			System.out.println("Give Employee 2 id: ");
			e2 = sc.next();
			String command = "Select t1.dept_no AS 'D1', t1.emp_no as 'E3', t2.dept_no as 'D2' from (Select emp_no, dept_no from dept_emp where dept_no in (Select dept_no from dept_emp where emp_no = E1)) t1 join (Select emp_no, dept_no from dept_emp where dept_no in (Select dept_no from dept_emp where emp_no = E2)) t2 on t1.emp_no = t2.emp_no where t1.dept_no != t2.dept_no Limit 100;";
			command = command.replace("E1",e1);
			command = command.replace("E2",e2);
			query6(command);
			break;
		case 10:
			input = 10;
		}
	}	
	
}

//Executing Query 1-4
public static void runSQL(String query) {			//method that helps output the result for queries 1 to 4 
	String url = "jdbc:mysql://localhost:3306/employees";	//SQL serveer connection
    Connection con;
    Statement stmt;
	try {							//Try-Catch exception handeling 
	      Class.forName("com.mysql.cj.jdbc.Driver");
	  
	    } catch(java.lang.ClassNotFoundException e) {
	      System.err.print("ClassNotFoundException: "); 	//error message for Class Not Found Exception
	      System.err.println(e.getMessage());
	    }

	    try {
	      con = DriverManager.getConnection(url, 
	                   "root", "Sanghi@009*"); 		// Enter your server credentials
	  
	      stmt = con.createStatement();              
	  
	      ResultSet rs = stmt.executeQuery(query);
	      ResultSetMetaData rsmd = rs.getMetaData();
	  
	  
	      int numberOfColumns = rsmd.getColumnCount();	
	  
	      for (int i = 1; i <= numberOfColumns; i++) {	//print statement for the queries 
	        if (i > 1) System.out.print("  |  ");		//print statement that seperates columns in output 
	        String columnName = rsmd.getColumnName(i);
	        System.out.print(columnName);
	      }
	      System.out.println("");
	  
	      while (rs.next()) {				//loops prints the different columns for a query 
	        for (int i = 1; i <= numberOfColumns; i++) {
	          if (i > 1) System.out.print("  |  ");
	          String columnValue = rs.getString(i);		
	          System.out.print(columnValue);
	        }
	        System.out.println("");  
	      }
	      System.out.println();
	      stmt.close();
	      con.close();
	    } catch(SQLException ex) {
	      System.err.print("SQLException: ");		//Error message for SQL Exception
	      System.err.println(ex.getMessage());
	    }  
	  }
//Executing Query 5
	  public static void query5(String query) {			//method that helps output the result for query 5 
		String url = "jdbc:mysql://localhost:3306/employees";
		Connection con;
		Statement stmt;
		try {
			  Class.forName("com.mysql.cj.jdbc.Driver");
		  
			} catch(java.lang.ClassNotFoundException e) {		//Class Not Found Eception error message 
			  System.err.print("ClassNotFoundException: "); 
			  System.err.println(e.getMessage());
			}
	
			try {
			  con = DriverManager.getConnection(url, 
						   "root", "Sanghi@009*"); 	// Enter your server credentials
		  
			  stmt = con.createStatement();              
		  
			  ResultSet rs = stmt.executeQuery(query);
			  ResultSetMetaData rsmd = rs.getMetaData();
		  
		  
			  int numberOfColumns = rsmd.getColumnCount()+2;	//prints the different columns 
			  for (int i = 1; i <= numberOfColumns; i++) {
				if (i > 1) System.out.print("  |  ");		//seperates the column data with a |
				if(i ==1 )System.out.print("emp_no");
				if(i==3) System.out.print("emp_no");
				if(i==2){
				String columnName = rsmd.getColumnName(1);
				System.out.print(columnName);
				}
				
			  }
			  System.out.println("");
		  
			  while (rs.next()) {
				for (int i = 1; i <= numberOfColumns; i++) {
				  if (i > 1) System.out.print("  |  ");
				  if(i ==1 )System.out.print(e1);		//Outputs Empployee 1 data in column 
				  if(i==3) System.out.print(e2);		//Outputs Employee 2 data in column 
				  if(i==2){
				  String columnValue = rs.getString(1);
				  System.out.print(columnValue);
				  }
				}
				System.out.println("");  
			  }
			  System.out.println();
			  stmt.close();
			  con.close();
			} catch(SQLException ex) {
			  System.err.print("SQLException: ");			//SQL Exception error message 
			  System.err.println(ex.getMessage());
			}  
		  }

// Executing Query 6
public static void query6(String query) {					//method that helps output the result for query 6
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
					   "root", "Sanghi@009*"); // Enter your server credentials
	  
		  stmt = con.createStatement();              
	  
		  ResultSet rs = stmt.executeQuery(query);
		  ResultSetMetaData rsmd = rs.getMetaData();
	  
	  
		  int numberOfColumns = rsmd.getColumnCount()+2;
		  for (int i = 1; i <= numberOfColumns; i++) {		//for loops to print out different parts of the output 
			if (i > 1) System.out.print("  |  ");
			if(i ==1 )System.out.print("E1");		//names the columns E1 and E2 
			if(i==5) System.out.print("E2");
			if(i==2){
				String columnName = rsmd.getColumnName(1);
				System.out.print(columnName);
			}
			if(i==3){
				String columnName = rsmd.getColumnName(2);
				System.out.print(columnName);
			}
			if(i==4){
				String columnName = rsmd.getColumnName(3);
				System.out.print(columnName);
			}

			
		  }
		  System.out.println("");
	  
		  while (rs.next()) {						//loop formats the columsn of output data 
			for (int i = 1; i <= numberOfColumns; i++) {
				if(i ==1 )System.out.print(e1 + "  |  ");
				if(i==5) System.out.print(e2);
			  	if(i==2){
			  		String columnValue = rs.getString(1);
			  		System.out.print(columnValue + "  | ");
			  	}
			  	if(i==2){
					String columnValue = rs.getString(2);
					System.out.print(columnValue + "  |  ");
				}
				if(i==2){
					String columnValue = rs.getString(3);
					System.out.print(columnValue + "  |  ");
				}
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

	



