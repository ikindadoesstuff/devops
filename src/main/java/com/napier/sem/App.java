package com.napier.sem;

import java.sql.*;

public class App
{
    private Connection con = null;

    public static void main(String[] args) {
        App app = new App();

        app.connect();

        Employee emp = app.getEmployee(255530);
        app.displayEmployee(emp);

        app.disconnect();
    }

    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 100;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/employees?allowPublicKeyRetrieval=true&useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                // Wait a bit
                Thread.sleep(10000);
                // Exit for loop
                break;
            } catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            } catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    public Employee getEmployee(int ID)
    {
        try
        {
            Statement statement = con.createStatement();

            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, titles.title, " +
                            "salaries.salary, departments.dept_name, m.first_name AS manager_first_name, " +
                            "m.last_name AS manager_last_name "
                    + "FROM employees "
                    + "LEFT JOIN titles on employees.emp_no = titles.emp_no AND titles.to_date = '9999-01-01' "
                    + "LEFT JOIN salaries on employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                    + "LEFT JOIN dept_emp on employees.emp_no = dept_emp.emp_no AND dept_emp.to_date = '9999-01-01' "
                    + "LEFT JOIN departments on dept_emp.dept_no = departments.dept_no "
                    + "LEFT JOIN dept_manager on dept_emp.dept_no = dept_manager.dept_no AND dept_manager.to_date = '9999-01-01' "
                    + "LEFT JOIN employees as m on dept_manager.emp_no = m.emp_no "
                    + "WHERE employees.emp_no = " + ID;

            ResultSet resultSet = statement.executeQuery(strSelect);

            if (resultSet.next())
            {
                Employee employee = new Employee();
                employee.emp_no = resultSet.getInt("emp_no");
                employee.first_name = resultSet.getString("first_name");
                employee.last_name = resultSet.getString("last_name");
                employee.title = resultSet.getString("title");
                employee.salary = resultSet.getInt("salary");
                employee.dept_name = resultSet.getString("dept_name");
                employee.manager =
                        resultSet.getString("manager_first_name") +
                        resultSet.getString("manager_last_name");
                return employee;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee employee)
    {
        if (employee != null)
        {
            System.out.println(
                    "\n"
                    + employee.emp_no + " "
                    + employee.first_name + " "
                    + employee.last_name + "\n"
                    + "Title: " + employee.title + "\n"
                    + "Salary: $" + employee.salary + "\n"
                    + "Department: " + employee.dept_name + "\n"
                    + "Manager: " + employee.manager + "\n"
            );
        }
    }
}

//SELECT employees.emp_no, employees.first_name, employees.last_name, titles.title, salaries.salary, departments.dept_name, m.first_name AS manager_first_name, m.last_name AS manager_last_name
//FROM employees
//LEFT JOIN titles on employees.emp_no = titles.emp_no AND titles.to_date = '9999-01-01'
//LEFT JOIN salaries on employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01'
//LEFT JOIN dept_emp on employees.emp_no = dept_emp.emp_no AND dept_emp.to_date = '9999-01-01'
//LEFT JOIN departments on dept_emp.dept_no = departments.dept_no
//LEFT JOIN dept_manager on dept_emp.dept_no = dept_manager.dept_no AND dept_manager.to_date = '9999-01-01'
//LEFT JOIN employees as m on dept_manager.emp_no = m.emp_no
//WHERE employees.emp_no = 255530