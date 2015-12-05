package com.ruishengtech.rscc.crm.ui.mw.command;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yaoliceng on 2014/10/27.
 */
public class Commands {

    public static String build(String command, String... params) {
        for (String param : params) {
            command = StringUtils.replaceOnce(command, "?", param);
        }
        return command;
    }
}
