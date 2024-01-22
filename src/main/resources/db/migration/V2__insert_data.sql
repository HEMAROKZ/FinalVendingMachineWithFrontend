
INSERT INTO productlist (productId, name, productPrice, productInventoryCount)
VALUES (1, 'dietcoke', 35, 3),
       (2, 'ThumbsUp', 40, 10),
       (3, 'coldcoffee', 20, 12),
       (4, 'lays', 10, 20),
       (5, 'cheetos', 10, 0),
       (6, 'cocoCola', 40, 15);




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