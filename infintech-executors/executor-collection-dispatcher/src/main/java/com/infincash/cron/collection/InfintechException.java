package com.infincash.cron.collection;

public class InfintechException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 807018557994813625L;

	public InfintechException() {
		super();
	}

	public InfintechException(String msg) {
		super(msg);
	}

	public InfintechException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public InfintechException(Throwable cause) {
		super(cause);
	}
}
