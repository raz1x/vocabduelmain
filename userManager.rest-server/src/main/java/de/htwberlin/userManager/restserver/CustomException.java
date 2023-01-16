package de.htwberlin.userManager.restserver;

import org.restlet.resource.Status;

@Status(value = 400, serialize = true)
public class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}
