package customData;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.util.List;

public class CustomDatabase {
    private Map<String, List<String>> tables;
    private Map<String, Map<String, String>> tableSchemas; 

    public CustomDatabase() {
        tables = new HashMap<>();
        tableSchemas = new HashMap<>();
    }
    public void createTableWithStructure(String tableName, String tableStructure) {
        if (!tables.containsKey(tableName)) {
            tables.put(tableName, new ArrayList<>());
            tableSchemas.put(tableName, createTableSchema(tableStructure));
            System.out.println("Table '" + tableName + "' created with the specified structure.");
        } else {
            System.out.println("Table '" + tableName + "' already exists.");
        }
    }

    public Map<String, String> createTableSchema(String tableStructure) {
        Map<String, String> schema = new HashMap<>();
        String[] columns = tableStructure.split("\\|"); // Split the table structure by pipes

        for (String column : columns) {
            schema.put(column, "INTEGER");
             schema.put(column, "STRING");
            schema.put(column, "VARCHAR(255)");
            schema.put(column, "DATE");
        }

        return schema;
    }
        public void storeTablesInTextFile() {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("tables.txt"))) {
                for (Map.Entry<String, List<String>> tableEntry : tables.entrySet()) {
                    String tableName = tableEntry.getKey();
                    List<String> columnNames = tableEntry.getValue();

                    // Write table name
                    writer.write("CREATE TABLE " + tableName);
                    writer.newLine();

                    // Write table structure (columns)
                    for (String columnName : columnNames) {
                        writer.write(columnName + " DATA_TYPE"); // Replace DATA_TYPE with the actual data type
                        writer.newLine();
                    }

                    // Add a separator or identifier between tables if needed
                    writer.write("-----"); // Example separator
                    writer.newLine();
                }
            } catch (IOException e) {
            }
        }
    public void executeSQLQuery(String query) {
        String[] tokens = query.split(" ");
        String queryType = tokens[0].toUpperCase();

        if ("CREATE".equals(queryType)) {
            handleCreateTableQuery(query);
        }
//        } else if ("SELECT".equals(queryType)) {
//            handleSelectQuery(query);
//        } else if ("INSERT".equals(queryType)) {
//            handleInsertQuery(query);
//        } else if ("UPDATE".equals(queryType)) {
//            handleUpdateQuery(query);
//        } else if ("DELETE".equals(queryType)) {
//            handleDeleteQuery(query);
//        } else {
//            System.out.println("Unsupported query: " + query);
//        }
    }
    private void handleCreateTableQuery(String query) {
        // Check if the query starts with "CREATE TABLE" and contains " ("
        if (query.startsWith("CREATE TABLE") && query.contains(" (")) {
            // Extract table name
            String tableName = query.substring("CREATE TABLE".length(), query.indexOf(" (")).trim();

            // Extract column definitions inside the parentheses
            String columnsPart = query.substring(query.indexOf(" (") + 2, query.lastIndexOf(")"));

            // Split the columns by commas
            String[] columnDefinitions = columnsPart.split(",");

            // Trim and process each column definition
            List<String> columnNames = new ArrayList<>();
            List<String> columnDataTypes = new ArrayList<>();

            for (String columnDefinition : columnDefinitions) {
                // Split column definition into name and datatype
                String[] parts = columnDefinition.trim().split(" ");

                if (parts.length == 2) {
                    columnNames.add(parts[0]);
                    columnDataTypes.add(parts[1]);
                }
            }

            // Now you have the table name, column names, and data types
            System.out.println("Creating table: " + tableName);
            System.out.println("Columns: " + columnNames);
            System.out.println("Data Types: " + columnDataTypes);

            // Implement the logic to create the table structure in your database
            // You can use the table name, column names, and data types to create the table
            // This may involve creating a data structure to store the table structure
        } else {
            System.out.println("Invalid CREATE TABLE query format.");
        }
    }



}
