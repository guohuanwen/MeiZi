package me.itangqi.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import me.itangqi.greendao.Fuli;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table FULI.
*/
public class FuliDao extends AbstractDao<Fuli, Long> {

    public static final String TABLENAME = "FULI";

    /**
     * Properties of entity Fuli.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Who = new Property(1, String.class, "who", false, "WHO");
        public final static Property PublishedAt = new Property(2, String.class, "publishedAt", false, "PUBLISHED_AT");
        public final static Property Desc = new Property(3, String.class, "desc", false, "DESC");
        public final static Property Type = new Property(4, String.class, "type", false, "TYPE");
        public final static Property Url = new Property(5, String.class, "url", false, "URL");
        public final static Property Used = new Property(6, String.class, "used", false, "USED");
        public final static Property ObjectId = new Property(7, String.class, "objectId", false, "OBJECT_ID");
        public final static Property CreatedAt = new Property(8, String.class, "createdAt", false, "CREATED_AT");
        public final static Property UpdatedAt = new Property(9, String.class, "updatedAt", false, "UPDATED_AT");
    };


    public FuliDao(DaoConfig config) {
        super(config);
    }
    
    public FuliDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'FULI' (" + //
                "'_id' INTEGER PRIMARY KEY ," + // 0: id
                "'WHO' TEXT NOT NULL ," + // 1: who
                "'PUBLISHED_AT' TEXT NOT NULL ," + // 2: publishedAt
                "'DESC' TEXT NOT NULL ," + // 3: desc
                "'TYPE' TEXT NOT NULL ," + // 4: type
                "'URL' TEXT NOT NULL ," + // 5: url
                "'USED' TEXT NOT NULL ," + // 6: used
                "'OBJECT_ID' TEXT NOT NULL ," + // 7: objectId
                "'CREATED_AT' TEXT NOT NULL ," + // 8: createdAt
                "'UPDATED_AT' TEXT NOT NULL );"); // 9: updatedAt
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'FULI'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Fuli entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getWho());
        stmt.bindString(3, entity.getPublishedAt());
        stmt.bindString(4, entity.getDesc());
        stmt.bindString(5, entity.getType());
        stmt.bindString(6, entity.getUrl());
        stmt.bindString(7, entity.getUsed());
        stmt.bindString(8, entity.getObjectId());
        stmt.bindString(9, entity.getCreatedAt());
        stmt.bindString(10, entity.getUpdatedAt());
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Fuli readEntity(Cursor cursor, int offset) {
        Fuli entity = new Fuli( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // who
            cursor.getString(offset + 2), // publishedAt
            cursor.getString(offset + 3), // desc
            cursor.getString(offset + 4), // type
            cursor.getString(offset + 5), // url
            cursor.getString(offset + 6), // used
            cursor.getString(offset + 7), // objectId
            cursor.getString(offset + 8), // createdAt
            cursor.getString(offset + 9) // updatedAt
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Fuli entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setWho(cursor.getString(offset + 1));
        entity.setPublishedAt(cursor.getString(offset + 2));
        entity.setDesc(cursor.getString(offset + 3));
        entity.setType(cursor.getString(offset + 4));
        entity.setUrl(cursor.getString(offset + 5));
        entity.setUsed(cursor.getString(offset + 6));
        entity.setObjectId(cursor.getString(offset + 7));
        entity.setCreatedAt(cursor.getString(offset + 8));
        entity.setUpdatedAt(cursor.getString(offset + 9));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Fuli entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Fuli entity) {
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
