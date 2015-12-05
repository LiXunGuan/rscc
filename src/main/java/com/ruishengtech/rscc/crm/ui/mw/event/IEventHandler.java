package com.ruishengtech.rscc.crm.ui.mw.event;

import org.json.JSONObject;

/**
 * Created by yaoliceng on 2015/6/16.
 */
public interface IEventHandler {

    abstract void handler(JSONObject jsonObject);

}
