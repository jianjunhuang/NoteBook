package com.jinjunhuang.notebook.contract;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IView;
import com.demo.jianjunhuang.mvptools.utils.AfterGetData;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/8.
 */

public interface NoteListContract {
    public interface View<T> extends IView {
        void onSuccess(List<T> notes);

        void onDelSuccess();

        void onDelFailed(String reason);

        void onFailed();
    }

    public interface Model<T> extends IModel {

        void getData(int type);

        void delete(String noteId);

        void setAfterGetDatas(AfterGetData<T> afterGetDatas);
    }

    public interface AfterGetData<T> extends com.demo.jianjunhuang.mvptools.utils.AfterGetDatas {

        void onDelSuccess();

        void onDelFailed(String reason);
    }
}
