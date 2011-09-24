package com.topcoder.commons.utils;

/**
 * This is a static helper class that provides methods for constructing
 * exception instances using reflection. It is used by ValidationUtility,
 * PropertiesUtility and JDBCUtility.
 * 
 * Thread Safety: This class is immutable and thread safe when array parameters
 * passed to it are used by the caller in thread safe manner.
 * 
 * @author frogcherry 周晓龙 frogcherry@gmail.com
 * @version 1.0.0
 * @since 1.0.0
 */
class ExceptionHelper {
    /**
     * Empty private constructor.
     * 
     * Implementation Notes: Do nothing.
     */
    private ExceptionHelper() {
    }

    /**
     * Constructs an exception of the specified type with the given message.
     * 
     * Generic Parameters: T - the type of the exception to be created
     * 
     * Parameters: exceptionClass - the exception class message - the message
     * 
     * Returns: the constructed exception instance (not null)
     * 
     * Throws: Exception if any error occurred when using the reflection
     * 
     * Implementation Notes: 1. Constructor<T> constructor =
     * exceptionClass.getConstructor(String.class); 2. T result =
     * constructor.newInstance(message); 3. Return result.
     * 
     * Note: exceptions are populated without wrapping in this method.
     * 
     * @throws Exception
     *             if any error occurred when using the reflection.
     *             Here not point the exceptions out. You should control them yourself.
     * @param message
     *            the message
     * @param exceptionClass
     *            the exception class
     * @return the constructed exception instance (not null)
     */
    public static <T extends Throwable> T constructException(
            Class<T> exceptionClass, String message) {
    	Throwable e = new Throwable(message);
        return exceptionClass.cast(e);
    }

    /**
     * Constructs an exception of the specified type with the given message and
     * cause.
     * 
     * Generic Parameters: T - the type of the exception to be created
     * 
     * Parameters: exceptionClass - the exception class message - the message
     * cause - the exception cause
     * 
     * Returns: the constructed exception instance (not null)
     * 
     * Throws: Exception if any error occurred when using the reflection
     * 
     * Implementation Notes: 1. Constructor<T> constructor =
     * exceptionClass.getConstructor(String.class, Throwable.class); 2. T result
     * = constructor.newInstance(message, cause); 3. Return result.
     * 
     * Note: exceptions are populated without wrapping in this method.
     * 
     * @throws Exception
     *             if any error occurred when using the reflection.
     *             Here not point the exceptions out. You should control them yourself.
     * @param message
     *            the message
     * @param cause
     *            the exception cause
     * @param exceptionClass
     *            the exception class
     * @return the constructed exception instance (not null)
     */
    public static <T extends Throwable> T constructException(
            Class<T> exceptionClass, String message, Throwable cause) {
    	Throwable e  = new Throwable(message, cause);
        return exceptionClass.cast(e);
    }
}
