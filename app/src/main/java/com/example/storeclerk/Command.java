package com.example.storeclerk;

import org.json.JSONObject;

public class Command {
    protected AsyncToServer.IServerResponse callback;
    protected String context;
    protected String endPt;
    protected JSONObject data;
    protected String target;

    Command(AsyncToServer.IServerResponse callback,
            String context, String endPt, JSONObject data,String target)
    {
        this.callback = callback;
        this.context = context;
        this.endPt = endPt;
        this.data = data;
        this.target = target;
    }
}
