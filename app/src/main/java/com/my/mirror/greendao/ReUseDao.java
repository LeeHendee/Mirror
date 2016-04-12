package com.my.mirror.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.my.mirror.greendao.ReUse;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table RE_USE.
*/
public class ReUseDao extends AbstractDao<ReUse, Long> {

    public static final String TABLENAME = "RE_USE";

    /**
     * Properties of entity ReUse.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Title = new Property(1, String.class, "title", false, "TITLE");
        public final static Property Img = new Property(2, String.class, "img", false, "IMG");
        public final static Property Name = new Property(3, String.class, "name", false, "NAME");
        public final static Property Price = new Property(4, String.class, "price", false, "PRICE");
        public final static Property Area = new Property(5, String.class, "area", false, "AREA");
        public final static Property Brand = new Property(6, String.class, "brand", false, "BRAND");
    };


    public ReUseDao(DaoConfig config) {
        super(config);
    }
    
    public ReUseDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'RE_USE' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'TITLE' TEXT," + // 1: title
                "'IMG' TEXT," + // 2: img
                "'NAME' TEXT," + // 3: name
                "'PRICE' TEXT," + // 4: price
                "'AREA' TEXT," + // 5: area
                "'BRAND' TEXT);"); // 6: brand
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'RE_USE'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ReUse entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(2, title);
        }
 
        String img = entity.getImg();
        if (img != null) {
            stmt.bindString(3, img);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(4, name);
        }
 
        String price = entity.getPrice();
        if (price != null) {
            stmt.bindString(5, price);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(6, area);
        }
 
        String brand = entity.getBrand();
        if (brand != null) {
            stmt.bindString(7, brand);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ReUse readEntity(Cursor cursor, int offset) {
        ReUse entity = new ReUse( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // title
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // img
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // name
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // price
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // area
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6) // brand
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ReUse entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setTitle(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setImg(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setName(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPrice(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setArea(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setBrand(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ReUse entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ReUse entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
