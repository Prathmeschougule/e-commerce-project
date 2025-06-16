package com.ecom.project.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    String resourceName;
    String  filed;
    String filedName;
    Long filedId;

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String message, Throwable cause, String filedName, String filed, String resourceName) {
        super(String .format("%s not found with %s: %s" , resourceName , filed , filedName));
        this.filedName = filedName;
        this.filed = filed;
        this.resourceName = resourceName;
    }

    public ResourceNotFoundException( String resourceName, String filed,Long filedId) {
        super(String .format("%s not found with %s: %d" , resourceName , filed , filedId));
        this.filedId = filedId;
        this.filed = filed;
        this.resourceName = resourceName;
    }
}
