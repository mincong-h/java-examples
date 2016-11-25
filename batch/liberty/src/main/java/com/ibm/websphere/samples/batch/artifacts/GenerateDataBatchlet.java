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

import java.io.BufferedWriter;
import java.util.Random;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.beans.AccountType;
import com.ibm.websphere.samples.batch.beans.CheckingAccountType;
import com.ibm.websphere.samples.batch.beans.PriorityAccount;
import com.ibm.websphere.samples.batch.util.BonusPayoutUtils;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;

/**
 * Generate some random data, then write in CSV format into a text file.
 */
public class GenerateDataBatchlet implements Batchlet, BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    /**
     * How many records to write.
     */
    @Inject
    @BatchProperty(name = "numRecords")
    private String numRecordsStr;

    /**
     * File to write generated data to.
     */
    @Inject
    @BatchProperty
    private String generateFileNameRoot;

    @Inject
    private JobContext jobCtx;

    /**
     * Set to whatever you want, could make a parameter/property.
     */
    private final int maxAccountValue = 1000;

    /*
     * For CDI version of sample this will be injectable.
     */
    private AccountType acctType = new CheckingAccountType();

    /*
     * Included for CDI version of sample.
     */
    @Inject
    public void setAccountType(@PriorityAccount AccountType acctType) {
        this.acctType = acctType;
    }

    private boolean stopped = false;

    private BufferedWriter writer = null;

    @Override
    public String process() throws Exception {

        writer = new BonusPayoutUtils(jobCtx).openCurrentInstanceStreamWriter();

        String accountCode = acctType.getAccountCode();
        
        logger.info("In GenerateDataBatchlet, using account code = " + accountCode);
        
        int numRecords = Integer.parseInt(numRecordsStr);
        for (int i = 0; i < numRecords; i++) {

            StringBuilder line = new StringBuilder();

            // 1. Write record number
            line.append(i).append(',');

            // 2. Write random value
            line.append(new Random().nextInt(maxAccountValue)).append(',');

            // 3. Write account code
            line.append(accountCode);

            writer.write(line.toString());
            writer.newLine();

            if (stopped) {
                logger.info("Aborting GenerateDataBatchlet since a stop was received");
                writer.close();
                return null;
            }
        }
        writer.close();

        return null;
    }

    @Override
    public void stop() {
        stopped = true;
    }

}
