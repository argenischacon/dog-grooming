package gui.common;

import javax.swing.table.DefaultTableModel;

public class NonEditableTableModel extends DefaultTableModel {

    public NonEditableTableModel() {
        super();
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false; // All cells are non-editable
    }
}
