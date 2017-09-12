package com.jinjunhuang.notebook.common;

import com.demo.jianjunhuang.mvptools.integration.BaseApplication;
import com.jinjunhuang.notebook.db.NoteDbOpenHelper;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public class NoteBookApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        NoteDbOpenHelper.init(this);
    }
}
