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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.listener.ChunkListener;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;
import com.ibm.websphere.samples.batch.util.TransientDataHolder;

public class ValidationDBReadChunkListener implements ChunkListener, BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    @BatchProperty(name = "chunkSize")
    private String chunkSizeStr;
    private Integer chunkSize;

    @Inject
    @BatchProperty
    private String dsJNDI;

    @Inject
    @BatchProperty
    private String tableName;

    @Inject
    private JobContext jobCtx;

    @Inject
    private StepContext stepCtx;

    private DataSource ds = null;

    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private void getDataSource() throws NamingException {
        if (ds == null) {
            ds = BonusPayoutUtils.lookupDataSource(dsJNDI);

            // Validate table name
            BonusPayoutUtils.validateTableName(tableName);
        }
    }

    /**
     * Grab checkpoint value from StepContext, and set ResultSet
     * back into context after executing query.
     */
    @Override
    public void beforeChunk() throws Exception {
        getDataSource();

        // We expect this to have been initialized by the ItemReader
        TransientDataHolder data = (TransientDataHolder) stepCtx.getTransientUserData();

        Integer recordNumber = data.getRecordNumber();
        buildAndExecuteQuery(recordNumber);
        data.setResultSet(rs);
    }

    private void buildAndExecuteQuery(Integer recordNumber) throws SQLException {

        int upperBound = recordNumber + getChunkSize();

        long instanceID = jobCtx.getInstanceId();

        String query = BonusPayoutUtils.getAccountQuery(tableName);

        logger.fine("Next query in ValidationDBReadChunkListener: " + query + "with parms: (" + recordNumber + "," + upperBound + "," + instanceID + ")");

        conn = ds.getConnection();
        ps = conn.prepareStatement(query);
        ps.setInt(1, recordNumber);
        ps.setInt(2, upperBound);
        ps.setLong(3, instanceID);

        rs = ps.executeQuery();
    }

    @Override
    public void onError(Exception ex) throws Exception {
        cleanup();
    }

    @Override
    public void afterChunk() throws Exception {
        cleanup();
    }

    private void cleanup() throws Exception {
        if (rs != null)
            rs.close();
        if (ps != null)
            ps.close();
        if (conn != null)
            conn.close();
    }

    private int getChunkSize() {
        if (chunkSize == null) {
            chunkSize = Integer.parseInt(chunkSizeStr);
        }
        return chunkSize;
    }
}
