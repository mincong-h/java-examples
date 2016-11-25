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

import java.util.logging.Logger;

import javax.batch.api.listener.AbstractStepListener;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.context.JobContext;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;

/** 
 * Verifies that the number of records we've processed in the validation step
 * equals the original number of records specified to be used for this job. 
 */
public class ValidationCountAfterStepListener extends AbstractStepListener implements BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    private JobContext jobCtx;

    @Inject
    private StepContext stepCtx;

    @Override
    public void afterStep() throws Exception {

        BatchStatus stepBatchStatus = stepCtx.getBatchStatus();

        // If step has already failed or is stopping, don't validate the exit status
        if (stepBatchStatus == BatchStatus.FAILED || stepBatchStatus == BatchStatus.STOPPING) {
            logger.info("Don't bother parsing exit status since step has batchStatus = " + stepBatchStatus.name());
            return;
        }

        int numRecordsProcessed = Integer.parseInt(stepCtx.getExitStatus());
        int originalNumRecords = Integer.parseInt(jobCtx.getProperties().getProperty(NUM_RECORDS));

        if (numRecordsProcessed != originalNumRecords) {
            String errorMsg = "App failure.  Didn't read the same number of records as we originally wrote.  Originally wrote # " + originalNumRecords + "but instead read  "
                              + numRecordsProcessed + " # records during validation step.";
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        }
    }

}
