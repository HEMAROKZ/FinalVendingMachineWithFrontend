package com.VendingMachine.VendingMachine01.util;

import com.VendingMachine.VendingMachine01.billGeneration.PurchaseItem;
import com.VendingMachine.VendingMachine01.dto.PurchaseResult;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FileUtility {

//    public static void generateBill(String fileName, String content) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
//            writer.write(content);
//        } catch (IOException e) {
//            e.printStackTrace(); // Handle the exception based on your application's needs
//        }
//    }
public static void generateBill(String relativePath, String fileName, String content) {
    String projectPath = System.getProperty("user.dir");
    String filePath = projectPath + "/" + relativePath + "/" + fileName;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(content);
    } catch (IOException e) {
        e.printStackTrace(); // Handle the exception based on your application's needs
    }
}

    public static String generateBillContent(PurchaseResult result) {
        StringBuilder content = new StringBuilder();

        // Add date and time to the bill
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        content.append("Date and Time: ").append(now.format(formatter)).append("\n\n");

        // Add total change to the bill
        content.append("Total Change: ").append(result.getChange()).append(" Rupees\n\n");

        // Add denominations to the bill
        content.append("Denominations:\n");
        for (Map.Entry<Integer, Integer> entry : result.getDenominationMap().entrySet()) {
            if (entry.getValue() > 0) {
                content.append(entry.getKey()).append(" Rupees: ").append(entry.getValue()).append(" notes/coins\n");
            }
        }
        content.append("\n");
// Add details for each product to the bill
        content.append("Details for Each Product:\n");
        for (Map<String, Object> item : result.getItems()) {
            content.append("Product Name: ").append(item.get("name")).append("\n");
            content.append("Product Price: ").append(item.get("price")).append(" Rupees\n");
            content.append("Number Of Quantity Purchased: ").append(item.get("quantity")).append("\n\n");
        }


        return content.toString();
    }
}
