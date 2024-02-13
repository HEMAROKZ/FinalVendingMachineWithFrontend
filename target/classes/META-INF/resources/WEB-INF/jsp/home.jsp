<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="ISO-8859-1">
    <%@include file="./Base.jsp"%>
    <title>Hemanth vending machine simulation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #B2BEB5; /* Light Blue Background */
            color: white;
        }

        .container {
            margin-top: 50px;
        }

        .card {
            background: rgba(0, 0, 0, 0.7);
            border: none;
            transition: transform 0.3s;
        }

        .card:hover {
            transform: scale(1.05);
        }

        h1 {
            font-size: 3rem;
             color: 0D0D0A;
        }

        .list-group-item {
            background: transparent;
            border: none;
            border-bottom: 1px solid white;
            color: white;
        }

        .list-group-item h3 {
            margin-bottom: 0;
        }
    </style>
</head>

<body>
    <div class="container text-center">
        <h1> Vending Machine Simulation</h1>
        <br> <br>
        <div class="card-deck">
            <div class="card">
                <a href="/getAllInventory" class="list-group-item list-group-item-action">
                    <div class="card-body">
                        <h3 class="card-title">Admin Process</h3>
                    </div>
                </a>
            </div>
            <div class="card">
                <a href="/purchaseproduct" class="list-group-item list-group-item-action">
                    <div class="card-body">
                        <h3 class="card-title">Customer Process</h3>
                    </div>
                </a>
            </div>
        </div>
    </div>
</body>

</html>
