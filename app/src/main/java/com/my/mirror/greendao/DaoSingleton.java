package com.my.mirror.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.my.mirror.base.BaseApplication;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;


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
    private SpecialShareDao specialShareDao;


    /**
     * 私有构造方法
     */
    private DaoSingleton(Context context) {
        this.context = context;
    }

    /**
     * 对外提供一个方法，可以获得DaoSingleton
     * @param context
     * @return
     */
    public static DaoSingleton getInstance(Context context) {
        if (instance == null) {
            synchronized (DaoSingleton.class){
                if (instance == null) {
                    instance = new DaoSingleton(context);
                }
            }
        }
        return instance;
    }

    /**
     * 获得DevOpenHelper类对象，类似于SQLiteOpenHelper
     * @return
     */
    public DaoMaster.DevOpenHelper getHelper() {
        if (helper == null) {
            helper = new DaoMaster.DevOpenHelper(context,DATABASE_NAME,null);
        }
        return helper;
    }

    /**
     * 获得一个可操作的数据库对象
     * @return
     */
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

    public SpecialShareDao getSpecialShareDao() {
        if (specialShareDao == null) {
            specialShareDao = getDaoSession().getSpecialShareDao();
        }
        return specialShareDao;
    }

    //***************

    /**
     * 插入数据
     * 插入集合
     */
    public void insertReUseList(List<ReUse> entities) {
        getReUseDao().insertOrReplaceInTx(entities);
    }

    /**
     * 插入数据
     * 插入单个对象
     */
    public void insertReUseOnly(ReUse reUse) {
        getReUseDao().insertOrReplace(reUse);
    }

    public void deleteReUse(ReUse reUse) {
        getReUseDao().delete(reUse);
        QueryBuilder queryBuilder = null;
        queryBuilder.where(ReUseDao.Properties.Name.eq("全部"));
    }

    /**
     * 按名字查询
     * 其他一样
     */
    public List<ReUse> queryReUseList(String name) {
        QueryBuilder<ReUse> qb = getReUseDao().queryBuilder();
        qb.where(ReUseDao.Properties.Name.eq(name));
        return qb.list();
    }

    public List<ReUse> queryReUseAll() {
        return getReUseDao().loadAll();
    }

    //*************

    /**
     * 插入数据
     * 插入集合
     */
    public void insertClassiFiedList(List<ClassiFied> classiFieds) {
        getClassiFiedDao().insertOrReplaceInTx(classiFieds);
    }

    /**
     * 插入数据
     * 插入单个对象
     */
    public void insertClassiFiedOnly(ClassiFied classiFied) {
        getClassiFiedDao().insertOrReplace(classiFied);
    }

    public void deleteClassiFied(ClassiFied classiFied) {
        getClassiFiedDao().delete(classiFied);
        QueryBuilder queryBuilder = null;
        queryBuilder.where(ClassiFiedDao.Properties.Title.eq("全部"));
    }

    /**
     * 按标题查询
     * 其他一样
     */
    public List<ClassiFied> queryClassiFiedList(String name) {
        QueryBuilder<ClassiFied> qb = getClassiFiedDao().queryBuilder();
        qb.where(ClassiFiedDao.Properties.Title.eq(name));
        return qb.list();
    }

    public List<ClassiFied> queryClassiFiedAll() {
        return getClassiFiedDao().loadAll();
    }

    //**************
    /**
     * 插入数据
     * 插入集合
     */
    public void insertSpecialShareList(List<SpecialShare> specialShares) {
        getSpecialShareDao().insertOrReplaceInTx(specialShares);
    }

    /**
     * 插入数据
     * 插入单个对象
     */
    public void insertSpecialShareOnly(SpecialShare specialShare) {
        getSpecialShareDao().insertOrReplace(specialShare);
    }

    public void deleteSpecialShare(SpecialShare specialShare) {
        getSpecialShareDao().delete(specialShare);
        QueryBuilder queryBuilder = null;
        queryBuilder.where(SpecialShareDao.Properties.Img.eq("全部"));
    }

    /**
     * 按图片网址查询
     * 其他一样
     */
    public List<SpecialShare> querySpecialShareList(String name) {
        QueryBuilder<SpecialShare> qb = getSpecialShareDao().queryBuilder();
        qb.where(SpecialShareDao.Properties.Img.eq(name));
        return qb.list();
    }

    public List<SpecialShare> querySpecialShareAll() {
        return getSpecialShareDao().loadAll();
    }


}
