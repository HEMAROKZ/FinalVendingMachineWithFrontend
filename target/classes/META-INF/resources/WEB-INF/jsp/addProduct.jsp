<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<%@include file="./Base.jsp"%>
<title>Add Product in Vending Machine  </title>
</head>
<body>
	<div class="container mt-3">
		<div class="row">
			<div class="col-md-6 offset-md-3">
		<h2 >Add Product in Vending Machine</h2>
		<br> <br><br>
				<h3>Fill The Inventry product Details</h3>
				<form action="add-Inventryitem" method="post">

                    <div class="form-group">
						<label>Product id</label> <input type="number"   class="form-control"
							name="productId">
					</div>

					<div class="form-group">
						<label>Product Name</label> <input type="text" class="form-control"
							name="name">
					</div>

					<div class="form-group">
						<label>Product Price</label> <input type="number" class="form-control"
							name="productPrice">
					</div>

					<div class="form-group">
						<label>Product Inventry Count</label> <input type="number" class="form-control"
							name="productInventoryCount">
					</div>

					<div class="container text-center">
						<button type="submit" class="btn btn-primary">Save</button>
					</div>
				</form>
			</div>

		</div>

	</div>
</body>
</html>