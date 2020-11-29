package ru.todoapp.services;

import ru.todoapp.models.Task;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/*
Utility class for exporting data in csv format
 */

public class DataExportService {

    private static final String CSV_SEPARATOR = ",";

    public static void writeToCSV(ArrayList<Task> taskList) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("tasks.csv")
                            , StandardCharsets.UTF_8));
            for (Task task : taskList) {
                StringBuffer oneLine = new StringBuffer();
                oneLine.append(task.getId().toString());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(task.getDescription());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(task.getUser().getName());
                oneLine.append(CSV_SEPARATOR);
                oneLine.append(task.getProject().getName());
                bw.write(oneLine.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        } catch (IOException e) {
        }
    }
}
