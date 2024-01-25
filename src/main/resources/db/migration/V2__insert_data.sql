
INSERT INTO productlist (productId, name, productPrice, productInventoryCount)
VALUES (456, 'DIET COKE', 35, 32),
       (238, 'THUMBS UP', 43, 18),
       (832, 'COLD COFFEE', 37, 12),
       (645, 'LAYS', 25, 29),
       (562, 'CHEETOS', 10, 7),
       (611, 'STRING', 32, 15);




-- Inserting denominations data
INSERT INTO denominations (denominationType, count) VALUES
  ('FIFTY_RUPEE', 20),
  ('TWENTY_RUPEE', 30),
  ('TEN_RUPEE', 30),
  ('FIVE_RUPEE', 30),
  ('TWO_RUPEE', 50),
  ('ONE_RUPEE', 100);


INSERT INTO orders
(order_id,order_time, customerInputAmount, balanceAmount, vendingMachineBalance)
VALUES
(0,000, 0, 0, 2250);




INSERT INTO order_line
(order_id,line_num, product_id)
VALUES
(0,0,0);