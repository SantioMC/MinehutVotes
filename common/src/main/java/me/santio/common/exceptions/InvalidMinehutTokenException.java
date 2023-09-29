package me.santio.common.exceptions;

import lombok.experimental.StandardException;

/**
 * Represents if the token used to contact the Minehut API is invalid or not supplied.
 */
@StandardException
public class InvalidMinehutTokenException extends MinehutException {}
