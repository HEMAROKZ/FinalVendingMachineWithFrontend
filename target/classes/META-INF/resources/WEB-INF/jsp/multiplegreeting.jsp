<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Multiple Purchase Greeting</title>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body class="bg-light">
<div class="container mt-5">
    <h1 class="mb-4">Purchase Details</h1>
                <a href="/home" class="btn btn-outline-warning">Go Back</a>

    <!-- Display Total Change -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Total Change</h5>
        </div>
        <div class="card-body">
            <p class="lead">${result.change} Rupees</p>
        </div>
    </div>

    <!-- Display Denominations -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Denominations</h5>
        </div>
        <div class="card-body">
            <ul class="list-group">
                <c:forEach var="entry" items="${result.denominationMap}">
                    <c:if test="${entry.value > 0}">
                        <li class="list-group-item">${entry.key} Rupees: ${entry.value} notes/coins</li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>

    <!-- Display Details for Each Product in Card Format -->
    <div class="card">
        <div class="card-header">
            <h5>Details for Each Product</h5>
        </div>
        <div class="card-body">
            <div class="card-text">
                <c:forEach var="item" items="${result.items}">
                    <div class="card mb-3">
                        <div class="card-body">
                            <h6 class="card-subtitle mb-2 text-muted">Product Name: ${item.name}</h6>
                            <p class="card-text">Product Price: ${item.price} Rupees</p>
                            <p class="card-text">Number Of Quantity Purchased: ${item.quantity}</p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
    <!-- Display a link to download the bill -->
    <div class="card mb-4">
        <div class="card-header">
            <h5>Download Bill</h5>
        </div>
        <div class="card-body">
            <a href="/download?fileName=${billFileName}" class="btn btn-primary" download>Download Bill</a>
        </div>
    </div>

</div>

<!-- Include Bootstrap JS and Popper.js -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
        integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
</body>
</html>
