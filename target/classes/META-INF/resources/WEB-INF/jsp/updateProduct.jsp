
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="./Base.jsp"%>
<title> Update Vending Machine product </title>
</head>
<body background="D:\backup\3-1-23 trying new jsp page\17.11.12.13pm\VendingMachine01\src\main\resources\META-INF\resources\WEB-INF\jsp\two.jpg">
<div class="container mt-3 text-center">
<div style="font-family: sans-serif; font-size: 22px ; background-color: black; height: 50px ;color: white">Update Vending Machine Product
</div>
		<br> <br>
	<c:if test="${not empty msg}">
        ${msg}
    </c:if>
	<h3>Update Product</h3>
	<fieldset>
	<form method="POST" name="update_user"
		action="<%=request.getContextPath()%>/update/user">
		<input hidden="hidden" name="productId" value="${productId}" type="number" /> product
		Name: <input name="name" value="${inventory.name}" type="text" />
		<br />
		<br /> product price: <input name="productPrice" value="${inventory.productPrice}"
			type="number" /> <br /><br />
			product inventry count: <input name="productInventoryCount"
			value="${inventory.productInventoryCount}" type="number" /><br />
		<br />
		<br /> <input value="Update Product" type="submit" />
	</form>
	</fieldset>
	</div>
</body>
</html>