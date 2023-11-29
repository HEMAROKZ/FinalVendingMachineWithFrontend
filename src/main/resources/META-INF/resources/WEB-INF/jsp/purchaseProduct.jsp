<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1">
    <%@include file="./Base.jsp"%>
    <title>Vending Machine Product List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        .navbar {
            background-color: #007bff; /* Blue color */
            height: 80px;
        }

        .navbar-brand {
            font-family: sans-serif;
            font-size: 22px;
            color: white;
        }

        fieldset {
            margin-top: 30px;
            border: 2px solid #ccc;
            border-radius: 8px;
            width: 80%;
            padding: 20px;
            background-color: white;
        }

        .card {
            margin-top: 25px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 8px;
            transition: transform 0.3s;
            background-color: #f8f9fa; /* Light gray color */
        }

        .card:hover {
            transform: scale(1.05);
        }

        .card-title {
            font-size: 1.5rem;
            font-weight: bold;
            color: #007bff; /* Blue color */
        }

        .card-text {
            margin-bottom: 10px;
        }

        label {
            margin-top: 10px;
            display: block;
        }

        .form-control {
            width: 70%;
            margin: auto;
        }

        .btn-primary {
            margin-top: 15px;
        }

        .btn-outline-warning {
            margin-top: 15px;
            margin-left: 10px;
        }
    </style>

    <script>
        function updateQuantityLimit(element) {
            var quantityInput = element.querySelector('.form-control');
            var maxQuantity = parseInt(quantityInput.getAttribute('max'));
            var inventoryCount = parseInt(element.getAttribute('data-inventory-count'));

            if (maxQuantity > inventoryCount) {
                maxQuantity = inventoryCount;
            }

            quantityInput.setAttribute('max', maxQuantity);
        }

        document.addEventListener('DOMContentLoaded', function () {
            var cards = document.querySelectorAll('.card');
            cards.forEach(function (card) {
                updateQuantityLimit(card);
            });
        });
    </script>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Vending Machine Product List For Purchase</a>
    </nav>

    <fieldset>
        <form action="/purchasemultipleproductpage" method="post">
            <div class="container">
                <div class="row">
                    <c:forEach var="product" items="${list}" varStatus="status">
                        <div class="col-md-4">
                            <div class="card" data-inventory-count="${product.productInventoryCount}">
                                <div class="card-body">
                                    <h5 class="card-title">${product.name}</h5>
                                    <p class="card-text">Product ID: ${product.productId}</p>
                                    <p class="card-text">Price: ${product.productPrice}</p>
                                    <p class="card-text">Inventory Count: ${product.productInventoryCount}</p>
                                    <label for="quantity-${status.index}">Quantity:</label>
                                    <input type="number" name="quantities" value="0" min="0"
                                        max="${product.productInventoryCount}" class="form-control">
                                    <!-- Hidden fields for other details and count -->
                                    <input type="hidden" name="names" value="${product.name}">
                                    <input type="hidden" name="productIds" value="${product.productId}">
                                    <input type="hidden" name="prices" value="${product.productPrice}">
                                    <input type="hidden" name="countsOfProduct" value="${product.productInventoryCount}">
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div class="container text-center">
                <button type="submit" class="btn btn-primary">Purchase</button>
                <!-- <a href="/purchaseproductpage" class="btn btn-primary" style="font-size: 20px">Purchase Items</a> -->
                <a href="/home" class="btn btn-outline-warning">Go Back</a>
            </div>
        </form>
    </fieldset>
</body>

</html>
