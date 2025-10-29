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
                    "SELECT emp_no, first_name, last_name "
                    + "FROM employees "
                    + "WHERE emp_no = " + ID;

            ResultSet resultSet = statement.executeQuery(strSelect);

            if (resultSet.next())
            {
                Employee employee = new Employee();
                employee.emp_no = resultSet.getInt("emp_no");
                employee.first_name = resultSet.getString("first_name");
                employee.last_name = resultSet.getString("last_name");
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
                    employee.emp_no + " "
                    + employee.first_name + " "
                    + employee.last_name + "\n"
                    + employee.title + "\n"
                    + "Salary: " + employee.salary + "\n"
                    + employee.dept_name + "\n"
                    + "Manager: " + employee.manager + "\n"
            );
        }
    }



}
