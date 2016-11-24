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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemReader;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;
import com.ibm.websphere.samples.batch.util.TransientDataHolder;

/*
 * The idea here is that we'll reverse the calculation adding the 
 * bonus to each account balance, and confirm that the result matches
 * what was generated in step 1.
 * 
 * We will use an aggregate readItem to do this, reading both from the 
 * text file generated in the first step and the DB table inserted into
 * in the second step.
 * 
 */
public class ValidationReader implements ItemReader, BonusPayoutConstants {

    protected final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    @BatchProperty
    protected String dsJNDI;

    @Inject
    @BatchProperty(name = "startAtIndex")
    protected String startAtIndexStr;
    protected int startAtIndex;

    // For a partition we want to stop with one partition's worth, not the full set of records.
    @Inject
    @BatchProperty(name = "maxRecordsToValidate")
    protected String maxRecordsToValidateStr;
    protected int maxRecordsToValidate;

    @Inject
    protected JobContext jobCtx;

    @Inject
    protected StepContext stepCtx;

    // Keep a separate count for the purpose of double-checking the total number of 
    // entries validated.
    protected Integer recordsAlreadyRead = null;

    protected BufferedReader reader = null;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        openBaseImpl(checkpoint);
    }

    protected void openBaseImpl(Serializable checkpoint) throws Exception {
        if (checkpoint != null) {
            recordsAlreadyRead = (Integer) checkpoint;
        } else {
            recordsAlreadyRead = 0;
        }

        startAtIndex = getStartAtIndex();

        maxRecordsToValidate = getMaxRecordsToValidate();

        setupFileReader();
        setupDBReader();
    }

    /**
     * This value incorporates both the initial position plus
     * the number processed in both this and previous executions.
     */
    protected int getNextRecordIndex() {
        return startAtIndex + recordsAlreadyRead;
    }

    private void setupFileReader() throws Exception {
        BonusPayoutUtils helper = new BonusPayoutUtils(getJobContext());

        reader = helper.openCurrentInstanceStreamReader();

        // Advance cursor (not worrying much about performance) 
        for (int i = 0; i < getNextRecordIndex(); i++) {
            reader.readLine();
        }
    }

    protected void setupDBReader() throws Exception {
        TransientDataHolder data = new TransientDataHolder();
        data.setRecordNumber(getNextRecordIndex());
        getStepContext().setTransientUserData(data);
    }

    @Override
    public Object readItem() throws Exception {

        if (recordsAlreadyRead >= maxRecordsToValidate) {
            logger.fine("Reached the maximum number of records to validate without error.  Exiting chunk loop.");
            return null;
        }

        AccountDataObject fileDO = readFromFile();
        AccountDataObject tableDO = readFromDB();

        if (fileDO == null && tableDO != null) {
            String errorMsg = "App failure.  Read record # " + (recordsAlreadyRead + 1) + "from DB table, but only read " + recordsAlreadyRead + " records from file.";
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        } else if (fileDO != null && tableDO == null) {
            String errorMsg = "App failure.  Read record # " + (recordsAlreadyRead + 1) + "from file, but only read " + recordsAlreadyRead + " records from DB table.";
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        } else if (fileDO == null && tableDO == null) {
            // Some partitions might not have the full maximum number of data (for odd lots), so exit if both streams are exhausted at the same record count
            logger.fine("Input exhausted without error.  Exiting chunk loop.");
            return null;
        }

        recordsAlreadyRead++;

        // Convenient way for passing both to processor as a single item.
        tableDO.setCompareToDataObject(fileDO);

        return tableDO;
    }

    private AccountDataObject readFromFile() throws IOException {
        String line = reader.readLine();
        if (line == null) {
            logger.fine("End of stream reached in " + this.getClass());
            return null;
        } else {
            AccountDataObject acct = BonusPayoutUtils.parseLine(line);
            return acct;
        }
    }

    protected AccountDataObject readFromDB() throws SQLException {
        ResultSet rs = getCurrentResultSet();

        if (rs.next()) {
            int acctNum = rs.getInt(1);
            int balance = rs.getInt(2);
            String acctCode = rs.getString(3);
            return new AccountDataObject(acctNum, balance, acctCode);
        } else {
            logger.fine("End of JDBC input reached in " + this.getClass());
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        if (reader != null) {
            reader.close();
        }
    }

    @Override
    public Serializable checkpointInfo() throws Exception {

        // Update for the next chunk's query
        TransientDataHolder data = (TransientDataHolder) getStepContext().getTransientUserData();
        if (data != null) {
            data.setRecordNumber(getNextRecordIndex());
        }

        return recordsAlreadyRead;
    }

    protected ResultSet getCurrentResultSet() {
        TransientDataHolder data = (TransientDataHolder) getStepContext().getTransientUserData();
        return data.getResultSet();
    }

    /*
     * These getters set us up for using this as a superclass of an extension.
     * 
     * This solves the fact that injection needs to be done on the superclass and subclass independently.
     */
    protected int getStartAtIndex() {
        return Integer.parseInt(startAtIndexStr);
    }

    protected int getMaxRecordsToValidate() {
        return Integer.parseInt(maxRecordsToValidateStr);
    }

    protected JobContext getJobContext() {
        return jobCtx;
    }

    protected StepContext getStepContext() {
        return stepCtx;
    }
}
