package org.pluralsight.services;

import org.junit.jupiter.api.Test;
import org.pluralsight.models.SalesContract;
import org.pluralsight.models.Vehicle;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ContractFileManagerTest
{
    @Test
    public void testSaveContractFileExists() {
        Vehicle vehicleSold = new Vehicle(123456, 2022, "Toyota", "Camry", "Sedan", "Blue", 50000, 25000.00);

        SalesContract sale = new SalesContract("2024-05-17", "John Doe", "john@example.com", vehicleSold,
                 500.00, 1375.00, 100.00, 295.00, true);
        ContractFileManager contractFileManager = new ContractFileManager();
        contractFileManager.saveContract(sale);
        File file = new File("files/contracts.csv");
        assertTrue(file.exists(), "Contract file exists");
    }
}