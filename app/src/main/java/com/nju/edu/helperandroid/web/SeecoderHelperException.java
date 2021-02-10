package com.nju.edu.helperandroid.web;

public class SeecoderHelperException extends Exception {
    public static final SeecoderHelperException UNAHTORIZATION_ERROR = new SeecoderHelperException("403", "Unauthorization Error");

    public static final SeecoderHelperException NOT_FOUND_ERROR = new SeecoderHelperException("404", "Not Found Error");

    public static final SeecoderHelperException INTERNAL_SERVER_ERROR = new SeecoderHelperException("500", "Internal Server Error");

    public static final SeecoderHelperException UNKNOWN_ERROR = new SeecoderHelperException("-1", "Unknown Error");

    public static final SeecoderHelperException IO_ERROR = new SeecoderHelperException("500","IO Error");

    private String code;

    private String msg;

    public SeecoderHelperException(String code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
