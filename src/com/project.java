package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class project {

	public Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gbc", "root", "");
			// For testing
			System.out.print("Successfully connected");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public String insertProject(String name, String description, String type, String approval, String startDate, String endDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = "insert into projects(`projectId`,`ProjectName`,`projectDescription`,`projectType`,`approval`,`startDate`,`endDate`)"
					+ "values ( ?,  ?,  ?,  ?,  ?,  ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, description);
			preparedStmt.setString(4, type);
			preparedStmt.setString(5, approval);
			preparedStmt.setString(6, startDate);
			preparedStmt.setString(7, endDate);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newProjects = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
			
		} catch (Exception e) {
			
			 output = "{\"status\":\"error\", \"data\": \"Error while inserting the Project.\"}";
			System.err.println(e.getMessage());
		}
		return output;

	}
	
	public String readProject() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project Name</th><th>Project Description</th><th>Type</th><th>Approval</th><th>Start Date</th><th>End Date</th><th>Update</th><th>Remove</th></tr>"; 
			
			String query = "select * from projects";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String projectId = Integer.toString(rs.getInt("projectId"));
				String projectName = rs.getString("projectName");
				String projectDescription = rs.getString("projectDescription");
				String projectType = rs.getString("projectType");
				String approval = rs.getString("approval");
				String startDate = rs.getString("startDate");
				String endDate = rs.getString("endDate");
				
				// Add into the html table
				
				
				
				output += "<tr><td><input id='hidProjectIDUpdate' name='hidProjectIDUpdate' type='hidden' value='"+ projectId + "'>" + projectName + "</td>";
				output += "<td>" + projectDescription + "</td>";
				output += "<td>" + projectType + "</td>";
				output += "<td>" + approval + "</td>";
				output += "<td>" + startDate + "</td>";
				output += "<td>" + endDate + "</td>";
				// buttons
				
				   output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
	                        + "<td><input name='btnRemove'type='button' value='Remove'class='btnRemove btn btn-danger' data-projectid='"
	                        + projectId + "'>" + "</td></tr>";
				
				
			}
			con.close();
			
			// Complete the html table
			output += "</table>";
		} 
		catch (Exception e) {
			output = "Error while reading the Projects.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateProject(String projectId, String name, String description, String type, String approval, String startDate, String endDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE projects SET projectName=?,projectDescription=?,projectType=?,approval=?,startDate=?, endDate=? WHERE projectId=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, description);
			preparedStmt.setString(3, type);
			preparedStmt.setString(4, approval);
			preparedStmt.setString(5, startDate);
			preparedStmt.setString(6, endDate);
			preparedStmt.setInt(7, Integer.parseInt(projectId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newProjects = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the Project.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String deleteProject(String projectId) {
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			
			// create a prepared statement
			String query = "delete from projects where projectId=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(projectId));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newProjects = readProject();
			output = "{\"status\":\"success\", \"data\": \"" + newProjects + "\"}";
		} 
		catch (Exception e) 
		{
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the Project.\"}";
					System.err.println(e.getMessage());
		}
		return output;
	}

}
