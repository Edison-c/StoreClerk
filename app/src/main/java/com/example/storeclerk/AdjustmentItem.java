package com.example.storeclerk;

import java.util.HashMap;

class AdjustmentItem extends HashMap<String, Object> {
    AdjustmentItem(String item, String quantity, String reason,String delete) {
        this.put("ItemNumber", item);
        this.put("QuantityAdjusted", quantity);
        this.put("Reason", reason);
        this.put("delete", delete);
    }
}
