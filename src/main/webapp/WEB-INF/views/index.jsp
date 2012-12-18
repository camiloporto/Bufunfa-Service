<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bufunfa.com</title>
</head>
<body>
	<header>
		<h1>Bufunfa.com</h1>
	</header>
	<section>
		<form id="loginForm">
		</form>
		
		<form id="newUserForm">
			<label>
				<span>email:</span>
				<input type="text" id="email" name="email">
			</label>
			<label>
				<span>password:</span>
				<input type="password" id="password" name="password">
			</label>
			<label>
				<span>repeat password:</span>
				<input type="password" id="repeatPassword" name="repeatPassword">
			</label>
			<input type="submit" value="Create my free account">
		</form>
		
	</section>
	<footer>
		<span>Copyright Bufunfa.com</span>
	</footer>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			alert("Opa");
		});
	</script>
</body>
</html>