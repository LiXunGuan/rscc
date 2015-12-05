package com.ruishengtech.framework.core.db.handler;

import com.ruishengtech.framework.core.db.TableDefinition;
import com.ruishengtech.framework.core.db.UUID;

import org.apache.commons.beanutils.BeanUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Map;


public abstract class BeanHandler extends SimpleHandler {

    @Override
    protected <T> void doConvert(T o, Map<String, Object> map, TableDefinition tableDefinition) {

        for (Map.Entry<String, Object> m : map.entrySet()) {

            String key = m.getKey();
            String fieldKey = null;

            //从bean的属性里进行转换
            if (tableDefinition != null) {
                fieldKey = tableDefinition.getColumsToField().get(key);
            }
            if(fieldKey==null) {
                fieldKey = key;
            }

            try {
                //sql时间和java时间类的转换
            	if ("uuid".equals(m.getKey())) {
            		String value = (String) map.get(m.getKey());
            		BeanUtils.setProperty(o, fieldKey, UUID.UUIDFromString(value));
            	} else if (map.get(m.getKey()) instanceof Date) {
                    Date value = (Date) map.get(m.getKey());
                    BeanUtils.setProperty(o, fieldKey, new java.util.Date(value.getTime()));
                } else if (map.get(m.getKey()) instanceof Timestamp) {
                    Timestamp value = (Timestamp) map.get(m.getKey());
                    BeanUtils.setProperty(o, fieldKey, new java.util.Date(value.getTime()));
                } else {
                    BeanUtils.setProperty(o, fieldKey, map.get(m.getKey()));
                }

            } catch (Exception e) {
                //e.printStackTrace();
            }
        }

    }
}
