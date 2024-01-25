

CREATE TABLE productlist (
  productId INT  NOT NULL,
  name VARCHAR(45) NOT NULL,
  productPrice INT NOT NULL,
  productInventoryCount INT NOT NULL,
  PRIMARY KEY (productId)
);



 CREATE TABLE denominations (
     id INT IDENTITY ,
     denominationType VARCHAR(50) NOT NULL,
     count INT NOT NULL
     PRIMARY KEY (id)
 );


CREATE TABLE orders (
  id INT IDENTITY(1,1) NOT NULL,
  order_id INT NOT NULL,
  order_time smalldatetime NOT NULL,
  customerInputAmount INT NOT NULL,
  balanceAmount INT NOT NULL,
  vendingMachineBalance INT NOT NULL,
  PRIMARY KEY (id)
);



CREATE TABLE order_line (
  order_id INT  NOT NULL,
  line_num INT NOT NULL,
  product_id INT NOT NULL,

);
