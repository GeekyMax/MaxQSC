package com.geekymax.maxschedule.util;

import com.geekymax.maxschedule.database.Deadline;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by max on 2017/9/17.
 */

public class DatabaseUtil {
    public static final List<Deadline> getAllDeadline(){
        return DataSupport.findAll(Deadline.class);
    }
}
