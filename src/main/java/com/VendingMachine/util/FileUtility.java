package com.VendingMachine.util;

import com.VendingMachine.customeexception.CustomIOException;
import com.VendingMachine.customeexception.ProductUnavialableException;
import com.VendingMachine.dto.PurchaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Component
public class FileUtility {

   public    final String BILLING_COUNTER_SESSION_KEY = "billingCounter";
    public   final int MAX_BILLING_COUNTERS = 3;
    private static final Logger log = LoggerFactory.getLogger(FileUtility.class);

    public static  void generateBill(String relativePath, String fileName, String content) {
    String projectPath = System.getProperty("user.dir");
    String filePath = projectPath + "/" + relativePath + "/" + fileName;

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write(content);
    } catch (IOException e) {
        throw new CustomIOException("error occurred while downloading the bill-->>"+e);
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


    public  int assignBillingCounter(HttpSession session) {
        Set<Integer> occupiedCounters = (Set<Integer>) session.getServletContext().getAttribute("occupiedBillingCounters");
        log.info(occupiedCounters + "-----------------occupiedCounters------------in assignBillingCounter");

        if (occupiedCounters == null) {
            occupiedCounters = new HashSet<>();
        }

        // Find the first available billing counter
        int billingCounter = 1;
        while (occupiedCounters.contains(billingCounter)) {
            billingCounter++;
            if (billingCounter > MAX_BILLING_COUNTERS) {
                throw new ProductUnavialableException("All billing counters are currently in use");
            }
        }

        // Mark the counter as occupied
        occupiedCounters.add(billingCounter);

        // Update session and application attributes
        session.setAttribute(BILLING_COUNTER_SESSION_KEY, billingCounter);
        session.getServletContext().setAttribute("occupiedBillingCounters", occupiedCounters);
        log.info(occupiedCounters + "-----------------occupiedCounters------------in assignBillingCounter before leaving method");
        log.info("-------------In assignBillingCounter: ---------  value is == " + billingCounter);
        return billingCounter;
    }

    public  void releaseBillingCounter(HttpSession session) {
        // Get the current billing counter
        Integer billingCounter = (Integer) session.getAttribute(BILLING_COUNTER_SESSION_KEY);
        log.info(billingCounter + "-----------------billingCounter------------ releaseBillingCounter");

        if (billingCounter != null) {
            // Mark the billing counter as available
            Set<Integer> occupiedCounters = (Set<Integer>) session.getServletContext().getAttribute("occupiedBillingCounters");
            log.info(occupiedCounters + "-----------------occupiedCounters------------ outside");

            if (occupiedCounters != null) {
                occupiedCounters.remove(billingCounter);
                log.info(occupiedCounters + "-----------------occupiedCounters------------ inside");
                session.getServletContext().setAttribute("occupiedBillingCounters", occupiedCounters);
            }
            // Clear the billing counter from the session
            session.removeAttribute(BILLING_COUNTER_SESSION_KEY);
        }

        // Additional logic: Invalidate the session if it has timed out
        if (session != null && session.getMaxInactiveInterval() > 0 && System.currentTimeMillis() - session.getLastAccessedTime() > session.getMaxInactiveInterval() * 1000) {
            log.info("Session has timed out. Invalidating the session.");
            session.removeAttribute(BILLING_COUNTER_SESSION_KEY);
            session.invalidate();
        }
        log.info("Released Billing Counter: " + billingCounter);
    }

}
