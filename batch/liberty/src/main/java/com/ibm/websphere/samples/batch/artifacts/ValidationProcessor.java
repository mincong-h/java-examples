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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;
import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;

public class ValidationProcessor implements ItemProcessor, BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Inject
    @BatchProperty(name = "bonusAmount")
    String bonusAmountStr;

    Integer bonusAmount = null;

    @Override
    public Object processItem(Object item) throws Exception {
        AccountDataObject tableDO = (AccountDataObject) item;
        AccountDataObject fileDO = tableDO.getCompareToDataObject();

        if (fileDO == null) {
            throw new IllegalStateException("Somehow got null fileDO");
        }

        if (tableDO.getAccountNumber() != fileDO.getAccountNumber()) {
            String errorMsg = "Mismatch between DB account # " + tableDO.getAccountNumber() + " and file account # " + fileDO.getAccountNumber();
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        } else if (!tableDO.getAccountCode().equals(fileDO.getAccountCode())) {
            String errorMsg = "Mismatch between DB account code " + tableDO.getAccountCode() + " and file account code " + fileDO.getAccountCode();
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        int originalBalance = fileDO.getBalance();
        int updatedBalance = tableDO.getBalance();

        if (originalBalance + getBonusAmount() != updatedBalance) {
            String errorMsg = "Mismatch between expected updated balance of " + Integer.toString(originalBalance + getBonusAmount()) + " actual updated balance of "
                              + updatedBalance;
            logger.severe(errorMsg);
            throw new IllegalStateException(errorMsg);
        }

        if (logger.isLoggable(Level.FINER)) {
            logger.finer("Verified match for records for account # " + tableDO.getAccountNumber());
        }
        return tableDO;
    }

    private int getBonusAmount() {
        if (bonusAmount == null) {
            bonusAmount = Integer.parseInt(bonusAmountStr);
        }

        return bonusAmount;
    }
}
