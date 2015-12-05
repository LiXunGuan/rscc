package com.ruishengtech.framework.core.db.handler;

import com.ruishengtech.framework.core.db.TableDefinition;
import com.ruishengtech.framework.core.util.Beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yaoliceng on 14-3-19.
 */
public abstract class SimpleHandler implements ISqlResultHandler {

    @Override
    public <T> List<T> doReturn(List<Map<String, Object>> ret, Class<T> c, TableDefinition tableDefinition) {

        List<T> rets = new ArrayList<T>();
        for (Map<String, Object> map : ret) {
            T o = null;
            try {
                if (!Beans.isWrapClass(c)) {
                    o = c.newInstance();
                    doConvert(o, map, tableDefinition);
                    rets.add(o);
                } else {
                    rets.add((T) map.values().iterator().next());
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return rets;
    }

    protected <T> void doConvert(T o, Map<String, Object> map, TableDefinition tableDefinition) {
        //do nothing
    }

}
