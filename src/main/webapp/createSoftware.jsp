<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ page import="com.tap.model.User"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Create Software</title>

<!-- Bootstrap CSS -->

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-KyZXEJcT+uGFPulI6Z9pD33ngH3uPyT4W8n6ofOgXfV3BiyX2m+rhclbbZNEOjyP"
	crossorigin="anonymous">
	
	
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
	font-family: Arial, sans-serif;
}

.nav-bar {
	background-color: #0277bd;
	padding: 5px 0px;
	
}

.container {
	background-color: #ffffff;
	padding: 40px;
	box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
	border-radius: 8px;
	max-width: 600px;
	border: 2px solid #90caf9;
	margin: 50px auto;
}

.heading {
	color: white;
	
}

h2{
	font-family: "Quicksand", sans-serif;
	font-optical-sizing: auto;
	font-weight: 600;
	font-style: normal;

	
}

.welcom{

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
	color: #0d47a1;
}

.form-control:focus, .form-select:focus {
	border-color: #1976d2;
	box-shadow: 0 0 5px rgba(25, 118, 210, 0.5);
}

textarea {
	resize: vertical;
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
</style>
</head>
<body>

	<!-- Navigation Bar -->
	<nav class="navbar navbar-expand-lg bg-body-tertiary ">
		<div class="container-fluid nav-bar pt-3 text">
				<h2 class="heading ps-4">Software manager</h2>
		</div>
	</nav>

	<!-- Check if the user is an Admin -->
	<c:if test="${sessionScope.role != 'Admin'}">
		<div class="container">
			<div class="alert alert-danger">
				<p>You do not have permission to access this page.</p>
				<a href="index.jsp" class="btn btn-secondary">Go to Home</a>
			</div>
		</div>
	</c:if>

	<!-- Admin Form to Create Software -->
	<c:if test="${sessionScope.role == 'Admin'}">
		<div class="container">
            
            <div class="welcom text-center">
            <%
            HttpSession httpSession = request.getSession(false);
            User user = (httpSession != null) ? (User) httpSession.getAttribute("user") : null;

            if (user == null) {
            %>
			Welcome: Guest
			<%
            } else {
            %>
			Welcome:
			<%= user.getUserName() %>
			<%
            }
            %>
            </div>
            
			

			<h1>Create Software</h1>

			<div class="row mt-3">
					<!-- Software Name field -->
					<div class="col-6">
				       <form action="SoftwareServlet" method="post">
						<label for="name">Software Name</label> <input type="text"
							class="form-control" id="name" name="name" required>
					</div>
 
					<!-- Access Level field -->
					<div class="col-6">
						<label for="accessLevel">Access Level</label> <select
							class="form-control" id="accessLevel" name="accessLevel" required>
							<option value="Admin">Admin</option>
							<option value="Write">Write</option>
							<option value="Read">Read</option>
						</select>
					</div>
			</div>
           <br>
			<!-- Description field -->
			<div class="row" >
			<div class="col-12">
			
			<div class="form-group">
				<label for="description">Description</label>
				<textarea class="form-control" id="description" name="description"
					rows="4" required></textarea>
			</div>
			</div>
			
			
			</div>
			

			<br>
			<button type="submit" class="btn btn-primary btn-block">Create
				Software</button>
			</form>

			<!-- Display error message if there's any -->
			<c:if test="${not empty error}">
				<div class="alert alert-danger mt-4">${error}</div>
			</c:if>

			<!-- Display success message -->
			<c:if test="${not empty success}">
				<div class="alert alert-success mt-4">${success}</div>
			</c:if>
		</div>
	</c:if>

	<!-- Bootstrap JS -->
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
