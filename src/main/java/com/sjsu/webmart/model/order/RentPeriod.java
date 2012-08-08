package com.sjsu.webmart.model.order;

import java.text.SimpleDateFormat;  
import java.util.Date;  

public class RentPeriod  implements Comparable<RentPeriod>{

    private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");  
  
    private Date begin;  
    private Date end;  
  
    public RentPeriod() {  
  
    }  
  
    public RentPeriod(Date begin, Date end) {  
        this.begin = begin;  
        this.end = end;  
    }  
  
    public void setBegin(Date begin) {  
        this.begin = begin;  
    }  
  
    public Date getBegin() {  
        return this.begin;  
    }  
  
    public void setEnd(Date end) {  
        this.end = end;  
    } 
    public Date getEnd() {  
        return this.end;  
    }  
  
    public boolean contains(Date d) {  
        if (d.after(begin) && d.before(end)) {  
            return true;  
        } 
        // if date matches
        if (d.compareTo(begin)==0) {  
            return true;  
        } 
        return false;  
    }  
  
    @Override  
    public int compareTo(RentPeriod o) {  
        if (end.before(o.end)) {  
            return -1;  
        } 
        return 1;  
    }  
  
    @Override  
    public String toString() {  
        return "RentPeriod [" + sdf.format(begin) + " - " + sdf.format(end)  
                + "]";  
    }  
  
}  