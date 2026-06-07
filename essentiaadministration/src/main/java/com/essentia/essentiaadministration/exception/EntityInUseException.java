package com.essentia.essentiaadministration.exception;

/**
 * Thrown when a delete is attempted on an entity that is still referenced
 * by other entities (e.g. a Brand that has perfumes, a Note used in perfumes).
 * Results in HTTP 409 Conflict.
 */
public class EntityInUseException extends RuntimeException {

    public EntityInUseException(String message) {
        super(message);
    }
}
