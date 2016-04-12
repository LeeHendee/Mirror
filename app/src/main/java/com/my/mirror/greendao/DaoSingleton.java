package com.my.mirror.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.my.mirror.base.BaseApplication;


/**
 * Created by dllo on 16/4/12.
 */
public class DaoSingleton {
    private static final String DATABASE_NAME = "daodemo.db";

    private volatile static DaoSingleton instance;

    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private Context context;
    private DaoMaster.DevOpenHelper helper;
    private ClassiFiedDao classiFiedDao;
    private ReUseDao reUseDao;


    private DaoSingleton() {
        context = BaseApplication.getContext();
    }

    public static DaoSingleton getInstance() {
        if (instance == null) {
            synchronized (DaoSingleton.class){
                if (instance == null) {
                    instance = new DaoSingleton();
                }
            }
        }
        return instance;
    }

    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context,DATABASE_NAME,null);
        }
        return helper;
    }

    private SQLiteDatabase getDb(){
        if (db == null) {
            db = getHelper().getWritableDatabase();
        }
        return db;
    }

    private DaoMaster getDaoMaster() {
        if (daoMaster == null) {
            daoMaster = new DaoMaster(getDb());
        }
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        if (daoSession == null) {
            daoSession = getDaoMaster().newSession();
        }
        return daoSession;
    }

    public ClassiFiedDao getClassiFiedDao() {
        if (classiFiedDao == null) {
            classiFiedDao = getDaoSession().getClassiFiedDao();
        }
        return classiFiedDao;
    }

    public ReUseDao getReUseDao() {
        if (reUseDao == null) {
            reUseDao = getDaoSession().getReUseDao();
        }
        return reUseDao;
    }



}
