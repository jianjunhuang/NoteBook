package com.jinjunhuang.notebook.contract;

import com.demo.jianjunhuang.mvptools.mvp.IModel;
import com.demo.jianjunhuang.mvptools.mvp.IView;

import java.util.List;

/**
 * @author jianjunhuang.me@foxmail.com
 *         create on 2017/9/11.
 */

public interface AddNoteContract {

    public interface View<T> extends IView {
        void onAddSuccess();

        void onAddFailed(String reason);

        void onGetDataFailed(String reason);

        void onGetDataSuccess(T t);

    }

    public interface Model<T> extends IModel {
        void addToDb(T t);

        void getData(String noteId);

        void setAfterGetData(AddNoteContract.AfterGetData<T> afterGetData);
    }

    public interface AfterGetData<T> {
        void onAddSuccess();

        void onGetDataSuccess(T data);

        void onAddFailed(String reason);

        void onGetDataFailed(String reason);
    }
}
