package com.ruishengtech.rscc.crm.ui.mw.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruishengtech.framework.core.db.ISolution;
import com.ruishengtech.framework.core.db.PageResult;
import com.ruishengtech.framework.core.db.condition.ICondition;
import com.ruishengtech.framework.core.db.service.BaseService;
import com.ruishengtech.framework.core.util.Beans;
import com.ruishengtech.rscc.crm.ui.mw.condition.ReportCondition;

/**
 * Created by yaoliceng on 2014/10/25.
 */
public abstract class BaseConfigService<T> extends BaseService {

    public void saveOrUpdate(T t) {

        if (Beans.getProperty(t, "id") == null) {
            save(t);
        } else {
            update(t);
        }
    }

    public void remove(Long id) {
        deleteById(getClazz(), id);

    }

    protected abstract Class<T> getClazz();

    public abstract void deploy();

    public PageResult<T> page(ICondition condition) {
        return queryPage(getSolution(), condition, getClazz());
    }

    public T get(Long id) {
        return getById(getClazz(), id);
    }
    
    protected abstract ISolution getSolution();



}
