package com.argenischacon.gui.common;

import com.argenischacon.dto.owner.OwnerListDto;

import javax.swing.*;
import java.util.List;

public class PaginatedListModel extends AbstractListModel {
    private List<OwnerListDto> pageData;
    private final int rowsPerPage;
    private long totalRecords;

    public PaginatedListModel(List<OwnerListDto> pageData, int rowsPerPage, long totalRecords){
        this.pageData = pageData;
        this.rowsPerPage = rowsPerPage;
        this.totalRecords = totalRecords;
    }

    public void updateData(List<OwnerListDto> pageData, long totalRecords){
        this.pageData = pageData;
        this.totalRecords = totalRecords;
        fireContentsChanged(this, 0, getSize());
    }

    public int getTotalPages(){
        return (int) Math.ceil((double) totalRecords / rowsPerPage);
    }

    @Override
    public int getSize() {
        return pageData.size();
    }

    @Override
    public Object getElementAt(int index) {
        return pageData.get(index);
    }
}
