<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <%@include file="./Base.jsp"%>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" integrity="sha384-GLhlTQ8iKu1rCdpp6F6W9BjNvlHbI8K1MT+5VuSpqQOKLo/G8r+fnQ5dQT+6Exbw" crossorigin="anonymous">

    <title>Vending Machine Product List</title>

    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .navbar {
            background-color: black;
            height: 80px;
        }

        .navbar-brand {
            font-family: sans-serif;
            font-size: 28px;
            color: white;
        }

        .navbar-nav .nav-link {
            font-family: sans-serif;
            font-size: 18px;
            color: white;
        }

        .navbar-nav .nav-item.active {
            border-bottom: 2px solid white;
        }

        .container {
            margin-top: 20px;
        }

        .card {
            background: rgba(255, 255, 255, 0.7);
            border: none;
            transition: transform 0.3s;
        }

        .card:hover {
            transform: scale(1.05);
        }

        h5.card-title {
            font-size: 1.5rem;
            font-weight: bold;
        }

        .btn-danger,
        .btn-primary {
            margin-top: 10px;
        }

        .btn-outline-warning,
        .btn-outline-success {
            margin-top: 20px;
        }
    </style>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-dark">
        <a class="navbar-brand" href="#">Vending Machine Product List</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">

                <li class="nav-item">
                    <a class="nav-link" href="/logout">Logout</a>
                </li>
            </ul>
        </div>
    </nav>


    <fieldset>
        <div class="container mt-4">
            <div class="row">
                <c:forEach var="l" items="${list}">
                    <div class="col-md-4 mb-4">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title">Product Id: ${l.productId}</h5>
                                <p class="card-text">Name: ${l.name}</p>
                                <p class="card-text">Product Price: ${l.productPrice}</p>
                                <p class="card-text">Product Inventory Count: ${l.productInventoryCount}</p>
                                <div class="text-center">
                                    <a href="/delete/${l.productId}" class="btn btn-danger btn-sm" role="button">
                                        Delete <i class="fas fa-trash"></i>
                                    </a>
                                    <a href="<%=request.getContextPath()%>/update/user/${l.productId}" class="btn btn-primary btn-sm" role="button">
                                        Update <i class="fas fa-pen"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="container text-center mt-4">
            <a href="/home" class="btn btn-outline-warning">Go Back</a>
            <a href="/addinventoryitem" class="btn btn-outline-success">Add Product</a>
        </div>
    </fieldset>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</body>

</html>
