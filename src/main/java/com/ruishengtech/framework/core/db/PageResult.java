package com.ruishengtech.framework.core.db;

import java.util.List;

/**
 * Created by yaoliceng on 14-3-19.
 */
public class PageResult<T> {

	/**
	 * 总记录,之前过滤(即数据库中记录的总数) 
	 */
    private Long iTotalRecords;

    /**
     * 总记录,之后过滤(即过滤后记录的总数已经应用——不仅仅是记录的数量返回结果集) 
     */
    private Long iTotalDisplayRecords;

    private List<T> ret;

    public PageResult(Long iTotalRecords, Long iTotalDisplayRecords, List<T> ret) {
        this.iTotalRecords = iTotalRecords;
        this.iTotalDisplayRecords = iTotalDisplayRecords;
        this.ret = ret;
    }

    public Long getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Long iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Long getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Long iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public List<T> getRet() {
        return ret;
    }

    public void setRet(List<T> ret) {
        this.ret = ret;
    }
}
