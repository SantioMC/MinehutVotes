package me.santio.common.exceptions;

import lombok.experimental.StandardException;

/**
 * Represents if the token used to contact the Minehut API isn't allowed to access the specific server id.
 */
@StandardException
public class MismatchedMinehutTokenException extends MinehutException {}
