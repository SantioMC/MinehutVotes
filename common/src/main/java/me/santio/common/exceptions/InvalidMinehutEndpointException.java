package me.santio.common.exceptions;

import lombok.experimental.StandardException;

/**
 * Represents if the endpoint used to contact the Minehut API was invalid.
 */
@StandardException
public class InvalidMinehutEndpointException extends MinehutException {}
