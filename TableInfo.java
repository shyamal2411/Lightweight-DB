package customData;

import java.util.List;

public class TableInfo {
    private String tableName;
    private List<String> columnNames;

    public TableInfo(String tableName, List<String> columnNames) {
        this.tableName = tableName;
        this.columnNames = columnNames;
    }

    public String getTableName() {
        return tableName;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }
}
