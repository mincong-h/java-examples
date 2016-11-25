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

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import javax.batch.api.chunk.listener.AbstractItemWriteListener;

import com.ibm.websphere.samples.batch.util.BonusPayoutConstants;

/**
 * This class simply breaks apart a BatchUpdateException to log a more detailed
 * error message.
 */
public class BatchUpdateExceptionListener extends AbstractItemWriteListener implements BonusPayoutConstants {

    private final static Logger logger = Logger.getLogger(BONUS_PAYOUT_LOGGER);

    @Override
    public void onWriteError(List<Object> items, Exception ex) throws Exception {
        BatchUpdateException bue = null;
        if (ex instanceof BatchUpdateException) {
            bue = (BatchUpdateException) ex;
            int[] updateCounts = bue.getUpdateCounts();
            logger.info("Caught BatchUpdateException with exception message = " + ex.getMessage());
            logger.info("Update Counts = " + updateCounts.length + "; Original chunk size of items to write = " + items.size());

            SQLException e = bue;
            while (e.getNextException() != null) {
                logger.info("Next chained exception message: " + e.getMessage() + ", SQLSTATE = " + e.getSQLState() + ", errorcode = " + e.getErrorCode());
                e = e.getNextException();
            }
        } else {
            logger.info("Found something besides BatchUpdateException, class type = " + ex.getClass());
        }

    }
}
