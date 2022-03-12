package com.shendeng_bianmin.agent.db;


import com.shendeng_bianmin.agent.db.table.HistoryRecord;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.Map;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig historyRecordDaoConfig;

    private final HistoryRecordDao historyRecordDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        historyRecordDaoConfig = daoConfigMap.get(HistoryRecordDao.class).clone();
        historyRecordDaoConfig.initIdentityScope(type);

        historyRecordDao = new HistoryRecordDao(historyRecordDaoConfig, this);

        registerDao(HistoryRecord.class, historyRecordDao);
    }
    
    public void clear() {
        historyRecordDaoConfig.clearIdentityScope();
    }

    public HistoryRecordDao getHistoryRecordDao() {
        return historyRecordDao;
    }

}
