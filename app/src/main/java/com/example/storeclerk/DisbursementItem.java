package com.example.storeclerk;

import java.util.HashMap;

class DisbursementItem extends HashMap<String, Object> {
    DisbursementItem(String dept, String collectionpoint, String collectiondate, String status) {
        this.put("dept", dept);
        this.put("collectionpoint", collectionpoint);
        this.put("collectiondate", collectiondate);
        this.put("status", status);
    }
}

class DisbursementDetail extends HashMap<String, Object> {
    DisbursementDetail(String desc, String quantity, String actual, String remark) {
        this.put("desc", desc);
        this.put("quantity", quantity);
        this.put("actual", actual);
        this.put("remark", remark);
    }
}
