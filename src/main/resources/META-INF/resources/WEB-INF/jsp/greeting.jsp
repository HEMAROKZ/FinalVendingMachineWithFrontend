<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="./Base.jsp"%>
<title>Vending Machine Product List</title>
<style>
table {
  border-collapse: collapse;
  width: 100%;
}

th, td {
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {background-color: #f2f2f2;}
</style>
</head>
<body>
<div style="font-family: sans-serif; font-size: 22px ; background-color: black; height: 30px; text-align: center; color: white">Vending Machine Product List
</div>

	<fieldset>
		<table class="table" border="2"
			style="margin-top: 25px; text-align: center">
			<thead class="thead-dark">
				<tr>
					<th scope="col">Product Name</th>
					<th scope="col">Product Price</th>
					<th scope="col">Balance Change Amount</th>

				</tr>
			</thead>
			<tbody>
					<tr>
						<td>${list.item}</td>
						<td>${list.price}</td>
						<td>${list.balance}</td>
					</tr>

			</tbody>
		</table>
		<div class="container text-center">
		<h3
              background-color: lightgrey;
              width: 300px;
              border: 15px solid green;
              padding: 50px;
              margin: 20px;
          >THANK YOU ,HAVE A NICE DAY</h3>
			<a href="/home" class="btn btn-outline-warning">Go Back</a>
		</div>
	</fieldset>
</body>
</html>