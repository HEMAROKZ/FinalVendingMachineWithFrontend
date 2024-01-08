

CREATE TABLE productlist (
  productId INT  NOT NULL,
  name VARCHAR(45) NOT NULL,
  productPrice INT NOT NULL,
  productInventoryCount INT NOT NULL,
  PRIMARY KEY (productId)
);


CREATE TABLE Denomination (
indexId INT  NOT NULL,
  fiftyRupee INT  NOT NULL,
  twentyRupee INT NOT NULL,
  tenRupee INT NOT NULL,
  fiveRupee INT NOT NULL,
  twoRupee INT NOT NULL,
  oneRupee INT NOT NULL,
  PRIMARY KEY (indexId)
);

CREATE TABLE purchasehistory_table (
  id INT IDENTITY(1,1) NOT NULL,
  transactionId INT NOT NULL,
  productId VARCHAR(255) NOT NULL,
  customerInputAmount INT NOT NULL,
  changeAmount INT NOT NULL,
  vendingMachineBalance INT NOT NULL,
  PRIMARY KEY (id)
);
