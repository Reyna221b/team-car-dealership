package org.pluralsight.services;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminLogger
{
    private final String LOG_DIRECTORY_PATH = "logs";
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("kk:mm:ss");

    private String fileName;
    private String filePath;

    public AdminLogger(String fileName)
    {

        File directory = new File(LOG_DIRECTORY_PATH);
        if(!directory.exists())
        {
            directory.mkdir();
        }


        this.fileName = fileName;
        this.filePath = LOG_DIRECTORY_PATH + "/" + fileName;
        if(!this.filePath.toLowerCase().endsWith(".log"))
        {
            this.filePath += ".log";
        }
    }

    public static void logMessage(String event, String message)
    {
        File file = new File("files/application.log");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("kk:mm:ss");

        try(
                FileWriter fileWriter = new FileWriter(file, true);
                PrintWriter writer = new PrintWriter(fileWriter)
        )
        {
            writer.printf("%s | %s | %s | %s\n", now.format(dateFormatter), now.format(timeFormatter),event, message);
        }
        catch (Exception ex)
        {

        }
    }
}
