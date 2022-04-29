package com.gxun.java.exception;

import com.gxun.core.GxunException;
import com.gxun.core.Status;

/**
 * @author sunny
 */
public class GException extends GxunException {
    String msg;
    public GException(Status status) {
        super(status);
    }

    public GException(int errCode) {
        super(errCode);
    }
    public GException(String msg) {
        super(Status.UNKNOWN);
        this.msg = msg;
    }
    @Override
    public String toString() {
        return "GxunException(" + this.status + "/" + this.errCode + this.msg + "/" + ')';
    }
}
