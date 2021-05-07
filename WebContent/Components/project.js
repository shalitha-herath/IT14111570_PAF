$(document).ready(function(){
	$("#alertSuccess").hide();
	$("#alertError").hide();
}); 

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	
	// Form validation-------------------
	var status = validateProjectForm();
	if(status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
	
// If valid------------------------
	
	//$("#formProject").submit
	var type = ($("#hidProjectIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "projectAPI",
		type : type,
		data : $("#formProject").serialize(),
		dataType : "text",
		complete : function(response, status) 
		{
			onProjectSaveCompelet(response.responseText, status);
		}
	});
});
	
	function onProjectSaveCompelet(response, status) {
		if (status == "success") 
		{
			var resultSet = JSON.parse(response);
			
			if (resultSet.status.trim() == "success") 
			{
				$("#alertSuccess").text("Successfully saved.");
				$("#alertSuccess").show();
				
				$("#divProjectGrid").html(resultSet.data);
				
			} else if (resultSet.status.trim() == "error") {
				
				$("#alertError").text(resultSet.data);
				$("#alertError").show();
			}
		} else if (status == "error") {
			$("#alertError").text("Error while saving.");
			$("#alertError").show();
		} else {
			$("#alertError").text("Unknown error while saving..");
			$("#alertError").show();
		}
		$("#hidProjectIDSave").val("");
		$("#formProject")[0].reset();
	}
	



//UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
		{
			$("#hidProjectIDSave").val($(this).closest("tr").find('#hidProjectIDUpdate').val());
			$("#projectName").val($(this).closest("tr").find('td:eq(0)').text());
			$("#projectDescription").val($(this).closest("tr").find('td:eq(1)').text());
			$("#projectType").val($(this).closest("tr").find('td:eq(2)').text());
			$("#approval").val($(this).closest("tr").find('td:eq(3)').text());
			$("#startDate").val($(this).closest("tr").find('td:eq(4)').text());
			$("#endDate").val($(this).closest("tr").find('td:eq(5)').text());
		});


//remove
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "projectAPI",
		type : "DELETE",
		data : "projectId=" + $(this).data("projectid"),
		dataType : "text",
		complete : function(response, status) 
		{
			onProjectDeleteComplete(response.responseText, status);
		}
	});
});


function onProjectDeleteComplete(response, status) {
	if (status == "success")
	{
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			
			$("#divProjectGrid").html(resultSet.data);
		
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

//CLIENTMODEL=========================================================================
function validateProjectForm() {
	// NAME
	if ($("#projectName").val().trim() == "") {
		return "Insert Project Name.";
	}
	// Description
	if ($("#projectDescription").val().trim() == "") {
		return "Insert Project Description.";
	}
	// Type-------------------------------
	if ($("#projectType").val().trim() == "") {
		return "Insert Type.";
	}
	// approval------------------------
	if ($("#approval").val().trim() == "") {
		return "Insert Approval.";
	}
	if ($("#startDate").val().trim() == "") {
		return "Insert Start Date.";
	}
	if ($("#endDate").val().trim() == "") {
		return "Insert End Date.";
	}

	
	return true;
}


