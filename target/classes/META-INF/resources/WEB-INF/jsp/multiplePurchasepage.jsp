<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Vending Machine Billing</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            min-height: 100vh;
        }

        .billing-container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 600px;
            width: 80%;
        }

        h2 {
            color: #007bff;
            text-align: center;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }

        th {
            background-color: #007bff;
            color: #fff;
        }

        .total-cost {
            margin-top: 20px;
            font-size: 1.5em;
            color: #333;
            text-align: right;
        }

        .denomination-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin-top: 20px;
            justify-content: center;
        }

        .denomination-card {
            width: 120px;
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            transition: transform 0.3s;
            cursor: pointer;
            text-align: center;
        }

        .denomination-card:hover {
            transform: scale(1.05);
        }

        .denomination-input {
            width: 60px;
            text-align: center;
            border: 1px solid #ddd;
            border-radius: 4px;
            padding: 8px;
            font-size: 16px;
            outline: none;
        }

        .quantity-buttons {
            display: flex;
            gap: 10px;
            justify-content: center;
        }

        .quantity-button {
            background-color: #007bff;
            color: #fff;
            padding: 10px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .calculate-button {
            background-color: #007bff;
            color: #fff;
            padding: 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 20px;
            width: 100%;
        }

        /* Hide spinner arrows in number input */
        input::-webkit-inner-spin-button,
        input::-webkit-outer-spin-button {
            -webkit-appearance: none;
            margin: 0;
        }

        input[type="number"] {
            -moz-appearance: textfield;
        }
    </style>

</head>
<body>
    <div class="billing-container">
        <h2>Vending Machine Billing</h2>
                <a href="/home" class="btn btn-outline-warning">Go Back</a>
<div class="container text-left">
   <h4>Assigned Billing Counter: ${billingCounter}</h4>
 </div>

        <form action="multiplePurchaseOfProductFinal" method="post">
           <table>
               <thead>
                   <tr>
                       <th>Product ID</th>
                       <th>Name</th>
                       <th>Quantity</th>
                       <th>Unit Price</th>
                       <th>Total Cost</th>
                       <!-- Add additional headers if needed -->
                   </tr>
               </thead>
               <tbody>
                   <c:forEach var="item" items="${list}">
                       <tr>
                           <td>${item.productId}</td>
                           <td>${item.name}</td>
                           <td>${item.quantity}</td>
                           <td>${item.price}</td>
                           <td>${item.quantity * item.price}</td>
                           <!-- Add hidden fields for other details -->
                           <input type="hidden" name="productIds" value="${item.productId}">
                           <input type="hidden" name="names" value="${item.name}">
                           <input type="hidden" name="prices" value="${item.price}">
                           <input type="hidden" name="quantities" value="${item.quantity}">
                          <input type="hidden" name="billingCounter" value="${billingCounter}">

                           <!-- Assuming countsOfProduct is productInventoryCount -->
                           <input type="hidden" name="countsOfProduct" value="${item.countOfProduct}">
                       </tr>
                   </c:forEach>
               </tbody>
           </table>


            <p class="total-cost">Total Cost: ${totalCost}</p>
            <input type="hidden" name="totalCost" value="${totalCost}">

            <div class="denomination-container">
                <div class="denomination-card">
                    <label for="denomination50">50:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination50')">-</button>
                        <input type="number" id="denomination50" name="denomination50" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination50')">+</button>
                    </div>
                </div>

                <div class="denomination-card">
                    <label for="denomination20">20:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination20')">-</button>
                        <input type="number" id="denomination20" name="denomination20" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination20')">+</button>
                    </div>
                </div>

                <div class="denomination-card">
                    <label for="denomination10">10:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination10')">-</button>
                        <input type="number" id="denomination10" name="denomination10" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination10')">+</button>
                    </div>
                </div>

                <div class="denomination-card">
                    <label for="denomination5">5:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination5')">-</button>
                        <input type="number" id="denomination5" name="denomination5" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination5')">+</button>
                    </div>
                </div>

                <div class="denomination-card">
                    <label for="denomination2">2:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination2')">-</button>
                        <input type="number" id="denomination2" name="denomination2" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination2')">+</button>
                    </div>
                </div>

                <div class="denomination-card">
                    <label for="denomination1">1:</label>
                    <div class="quantity-buttons">
                        <button type="button" class="quantity-button" onclick="decrementQuantity('denomination1')">-</button>
                        <input type="number" id="denomination1" name="denomination1" class="denomination-input" min="0" value="0">
                        <button type="button" class="quantity-button" onclick="incrementQuantity('denomination1')">+</button>
                    </div>
                </div>
            </div>

            <button type="submit" class="calculate-button">pay</button>
        </form>
    </div>


    <script>
        function incrementQuantity(denominationId) {
            var inputElement = document.getElementById(denominationId);
            inputElement.value = parseInt(inputElement.value) + 1;
        }

        function decrementQuantity(denominationId) {
            var inputElement = document.getElementById(denominationId);
            if (parseInt(inputElement.value) > 0) {
                inputElement.value = parseInt(inputElement.value) - 1;
            }
        }
    </script>
</body>
</html>
