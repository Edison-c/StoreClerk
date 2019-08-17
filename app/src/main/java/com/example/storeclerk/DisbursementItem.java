package com.example.storeclerk;

import java.util.Date;
import java.util.HashMap;

class DisbursementItem extends HashMap<String, Object> {
    DisbursementItem(String id,String dept, String collectionpoint, String collectiondate, String status) {
        this.put("id",id);
        this.put("dept", dept);
        this.put("collectionpoint", collectionpoint);
        this.put("collectiondate", collectiondate);
        this.put("status", status);
    }
}

class DisbursementDetail extends HashMap<String, Object> {
    DisbursementDetail(String detailId,String listId,String ItemNumber,String Category,String desc, String quantity, String actual, String remark) {
        this.put("detailId", detailId);
        this.put("listId", listId);
        this.put("ItemNumber", ItemNumber);
        this.put("Category", Category);
        this.put("desc", desc);
        this.put("quantity", quantity);
        this.put("actual", actual);
        this.put("remark", remark);
    }
}
