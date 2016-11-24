/*
 * Copyright 2014 International Business Machines Corp.
 * 
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership. Licensed under the Apache License, 
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.batch.artifacts;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.sql.DataSource;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;

/**
 * Loops through the items list building and finally executing a batch insert. 
 * 
 * Follow get-use-close pattern with JDBC Connection.
 */
public class AccountJDBCWriter extends AbstractItemWriter implements ItemWriter, BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    @BatchProperty
    private String dsJNDI;

    @Inject
    @BatchProperty
    private String tableName;

    @Inject
    private JobContext jobCtx;

    private DataSource ds = null;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        ds = BonusPayoutUtils.lookupDataSource(dsJNDI);
        BonusPayoutUtils.validateTableName(tableName);
    }

    @Override
    public void writeItems(List<Object> items) throws Exception {
        Connection conn = ds.getConnection();
        String sql = "INSERT INTO " + tableName + " VALUES (?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        for (Object obj : items) {
            AccountDataObject ado = AccountDataObject.class.cast(obj);
            ps.setInt(1, ado.getAccountNumber());
            ps.setInt(2, ado.getBalance());
            ps.setLong(3, jobCtx.getInstanceId());
            ps.setString(4, ado.getAccountCode());
            ps.addBatch();
        }
        logger.fine("Adding: " + items.size() + " items to table name: " + tableName + " via batch update");
        ps.executeBatch();
        logger.fine("Executed batch update.");
        ps.clearBatch();
        ps.close();
        conn.close();
    }

}
