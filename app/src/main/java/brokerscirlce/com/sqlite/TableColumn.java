package brokerscirlce.com.sqlite;

//This class represent one table column*/
/*
* This class represent a column in table, it stores column name and value. It is used when insert and update table.
    One table row is a list of TableColumn instances.
* */
public class TableColumn {

    // This is the table column name.
    private String columnName;

    // This is the table column value.
    private String columnValue;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

}
