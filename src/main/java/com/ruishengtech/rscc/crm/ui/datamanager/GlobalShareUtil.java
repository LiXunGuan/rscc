package com.ruishengtech.rscc.crm.ui.datamanager;



public class GlobalShareUtil {

	private GlobalShareUtil(){};
	
	private static GlobalShareUtil single = null;
	
	public static GlobalShareUtil getInstance() {  
        if (single == null) {    
	    	synchronized (GlobalShareUtil.class) {    
	            if (single == null) {    
	            	single = new GlobalShareUtil();   
	            }    
	        }      
        }    
       return single;  
    }
	
	public void printInfo() {
		for (int i = 0; i < 5; i++) {   
            System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);   
        } 
    }
	
	public static void main(String[] args) {
		GlobalShareUtil gs1 = GlobalShareUtil.getInstance();
		gs1.printInfo();
		GlobalShareUtil gs2 = GlobalShareUtil.getInstance();
		gs2.printInfo();
	}
	
}
