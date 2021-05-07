<%@page import="com.project"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Project Management</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script type="text/javascript" src="Components/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="Components/project.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-7">
				<h1 class="m-3">Project Management</h1>

				<form id="formProject" name="formProject" method="post"
					action="project.jsp">

					Project Name: 
					<input id="projectName" name="projectName"
						type="text" class="form-control form-control-sm"> <br>
					Description: 
					<input id="projectDescription"
						name="projectDescription" type="text"
						class="form-control form-control-sm"> <br> 
					
					    <label for="projectType">Project Type</label>
    <select class="form-control" id="projectType" name="projectType">
      <option value="">-- select --</option>
      <option value="1">Type 1</option>
      <option value="2">Type 2</option>
      <option value="3">Type 3</option>
      <option value="4">Type 4</option>
    </select><br>
					 
					Approval: <input id="approval" name="approval"
						type="text" class="form-control form-control-sm"> <br>
					Start Date: <input id="startDate" name="startDate" type="date"
						class="form-control form-control-sm"> <br> End Date:
					<input id="endDate" name="endDate" type="date"
						class="form-control form-control-sm"> <br> <input
						id="btnSave" name="btnSave" type="button" value="Save"
						class="btn btn-primary"> <input type="hidden"
						id="hidProjectIDSave" name="hidProjectIDSave" value="">

				</form>

				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

			</div>
		</div>




		<br>
		<div id="divProjectGrid">

			<%
			project projectObj = new project();
			out.print(projectObj.readProject());
			%>
		</div>
</body>
</html>