package com.argenischacon.gui.common;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PaginatedTableModel extends AbstractTableModel {
    private List<String[]> pageData;
    private final int rowsPerPage;
    private long totalRecords;
    private final String[] columnNames;

    public PaginatedTableModel(List<String[]> pageData, int rowsPerPage, long totalRecords, String[] columnNames) {
        this.pageData = pageData;
        this.rowsPerPage = rowsPerPage;
        this.totalRecords = totalRecords;
        this.columnNames = columnNames;
    }

    public void updateData(List<String[]> pageData, long totalRecords){
        this.pageData = pageData;
        this.totalRecords = totalRecords;
        fireTableDataChanged();
    }

    public int getTotalPages(){
        return (int) Math.ceil( (double) totalRecords / rowsPerPage);
    }

    @Override
    public int getRowCount() {
        return pageData.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return pageData.get(rowIndex)[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
