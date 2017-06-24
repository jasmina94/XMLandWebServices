package com.ftn.exception;

import com.ftn.model.dto.error.ServiceFault;

/**
 * Created by Alex on 6/22/17.
 */
public class ServiceFaultException extends RuntimeException {

    private ServiceFault serviceFault;

    public ServiceFaultException(String message, ServiceFault serviceFault) {
        super(message);
        this.serviceFault = serviceFault;
    }

    public ServiceFaultException(String message, Throwable e, ServiceFault serviceFault) {
        super(message, e);
        this.serviceFault = serviceFault;
    }

    public ServiceFault getServiceFault() {
        return serviceFault;
    }

    public void setServiceFault(ServiceFault serviceFault) {
        this.serviceFault = serviceFault;
    }
}
