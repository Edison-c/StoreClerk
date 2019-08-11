package com.example.storeclerk;

import java.util.HashMap;

class AdjustmentItem extends HashMap<String, Object> {
    AdjustmentItem(String item, String quantity, String reason,String delete) {
        this.put("item", item);
        this.put("quantity", quantity);
        this.put("reason", reason);
        this.put("delete", delete);
    }
}
