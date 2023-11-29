<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOC TYPE html>
<head>
<meta charset="ISO-8859-1">
<%@include file="./Base.jsp"%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">

<title>Add Product in Vending Machine  </title>
</head>
<body >

	<div class="container mt-3">
		<div class="row">
			<div class="col-md-6 offset-md-3">
		<h2 >Purchase Product in Vending Machine</h2>
		<br> <br><br>
				<h3>Enter the  product Id and cash amount:</h3>
				<form action="purchase-Inventryitem" method="post">

                    <div class="form-group">
						<label>Product id</label> <input type="number"   class="form-control"
							name="productId">
					</div>

	            <div class="form-group">
						<label> number of product</label> <input type="number" class="form-control"
							name="countOfProduct">
					</div>
					<div class="form-group">
						<label> Input cash amount</label> <input type="number" class="form-control"
							name="price">
					</div>


					<div class="container text-center">
						<button type="submit" class="btn btn-primary">Purchase</button>
						<a href="/home" class="btn btn-outline-warning">Go Back</a>
					</div>
				</form>
			</div>

		</div>

	</div>
</body>
</html>