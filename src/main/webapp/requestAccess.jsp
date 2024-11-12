<%@ page import="java.util.List"%>
<%@ page import="com.tap.model.Software"%>
<%@ page import="com.tap.dao.SoftwareDao"%>
<%@ page import="com.tap.daoImp.SoftwareDaoImp"%>
<%@ page import="com.tap.model.User"%>
<%@ page import="com.tap.model.Request"%>
<%@ page import="com.tap.dao.RequestDao"%>
<%@ page import="com.tap.daoImp.RequestDaoImp"%>

<%
    // Fetch all software for dropdown
    SoftwareDao softwareDao = new SoftwareDaoImp();
    List<Software> softwareList = softwareDao.getAllSoftware();
   
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Request Software Access</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<!-- ------------------------google-font----------------------- -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@500&display=swap"
	rel="stylesheet">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Barlow+Condensed:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&family=Quicksand:wght@300..700&display=swap"
	rel="stylesheet">
<style>
body {
	background-color: ;
	font-family: Arial, sans-serif;
}

.nav-bar {
	background-color: #0277bd;
	
}

.container {
	background-color: #ffffff;
	padding: 40px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	max-width: 600px;
	border: 2px solid #90caf9;
	align-item: center;
}
.heading{
 color:white;
}

h2 {
	font-family: "Quicksand", sans-serif;
	font-optical-sizing: auto;
	font-weight: 600;
	font-style: normal;
}



label {
	font-weight: bold;
	color: #0d47a1;
}

.form-control, .form-select {
	border-radius: 8px;
	border: 2px solid #64b5f6;
	padding: 10px;
	margin-bottom: 15px;
	color: #0d47a1;
}

.form-control:focus, .form-select:focus {
	border-color: #1976d2;
	box-shadow: 0 0 5px rgba(25, 118, 210, 0.5);
}

/* Custom styling for the textarea */
textarea {
	resize: vertical;
	font-size: 1rem;
	line-height: 1.5;
}

textarea:focus {
	border-color: #1976d2;
	box-shadow: 0 0 5px rgba(25, 118, 210, 0.5);
}

.btn-submit {
	background-color: #006BFF;
	color: white;
	border: none;
	padding: 12px 20px;
	border-radius: 8px;
	font-size: 16px;
	cursor: pointer;
	width: 100%;
}

.btn-submit:hover {
	background-color: #0277bd;
}

.mb-3 {
	margin-bottom: 1.5rem;
}

/* Form layout adjustments */
.form-row {
	display: flex;
	flex-wrap: wrap;
	gap: 20px;
	justify-content: space-between;
}

.form-row .col-6 {
	width: calc(50% - 10px);
}

.form-row .col-12 {
	width: 100%;
}

@media ( max-width : 768px) {
	.form-row .col-6, .form-row .col-12 {
		width: 100%;
	}
}
.submit-container{
background-color: #0277bd;

}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid nav-bar pt-3">
			
				<h2 class="heading ps-4">Request Software Access</h2>
		
			

		</div>
	</nav>
	
	 

	
	<div class="container mt-5">


		<!-- Form to submit the request -->
		<form action="RequestServlet" method="post">
			<!-- Row for Software Name and Access Type -->
			<div class="form-row">
				<!-- Software Name Dropdown -->
				<div class="col-6">
					<label for="software" class="form-label">Software Name:</label> <select
						name="software" id="software" class="form-select" required>
						<option value="">Select Software</option>
						<% for (Software software : softwareList) { %>
						<option value="<%= software.getId() %>"><%= software.getName() %></option>
						<% } %>
					</select>
				</div>

				<!-- Access Type Dropdown -->
				<div class="col-6">
					<label for="accessType" class="form-label">Access Type:</label> <select
						name="accessType" id="accessType" class="form-select" required>
						<option value="Read">Read</option>
						<option value="Write">Write</option>
						<option value="Admin">Admin</option>
					</select>
				</div>
			</div>

			<!-- Row for Reason (full width) -->
			<div class="form-row">
				<div class="col-12">
					<label for="reason" class="form-label">Reason for Request:</label>
					<textarea name="reason" id="reason" class="form-control" rows="4"
						required></textarea>
				</div>
			</div>

			<!-- Submit Button -->
			<div class="d-flex justify-content-center submit-container">
				<input type="submit" value="Submit Request" class="btn btn-submit">
			</div>
		</form>
	</div>

	<!-- Bootstrap JS (optional, for features like dropdown, modals, etc.) -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
		integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
		integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
		crossorigin="anonymous"></script>

</body>
</html>
