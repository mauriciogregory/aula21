package com.springDh.aula21.util;

import java.util.Date;
import java.sql.Timestamp;

public class Util {

    public static Timestamp dateToTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

}