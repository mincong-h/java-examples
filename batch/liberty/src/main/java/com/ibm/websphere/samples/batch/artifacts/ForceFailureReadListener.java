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

import javax.batch.api.BatchProperty;
import javax.batch.api.chunk.listener.AbstractItemReadListener;
import javax.inject.Inject;

import com.ibm.websphere.samples.batch.beans.AccountDataObject;

/**
 * Designed to force failure after the record #N is read, starting at index 0, either for the
 * step overall or on this partition.
 * 
 * In order to allow for an implementation to pool the listener artifact, however,
 * the count is performed relative to the first account number number read, rather
 * than kept as state in the form of a field of this class itself.
 */
public class ForceFailureReadListener extends AbstractItemReadListener {

    @Inject
    @BatchProperty(name = "forceFailure")
    private String failOnStr;

    // Helps initialize, toggle on/off, and cache the forced failure config.
    private ForcedFailureConfig failureConfig;

    @Override
    public void afterRead(Object item) throws Exception {

        if (item == null) {
            return; // End of step
        }
        
        if (failureConfig == null) {
            failureConfig = new ForcedFailureConfig((AccountDataObject) item);
        }

        failureConfig.possiblyFailOn(item);
    }

    private class ForcedFailureConfig {

        public void possiblyFailOn(Object item) {
            if (!failEnabled) {
                return;
            }

            int currentRecordNumber = ((AccountDataObject) item).getAccountNumber();

            if (currentRecordNumber - firstRecordNumber >= failOn) {
                String excMessage = "Forcing failure.  The failOn property = " + failOn +
                                    ", with the first record number = " + firstRecordNumber +
                                    ", and the current record number = " + currentRecordNumber;
                throw new IllegalStateException(excMessage);
            }
        }

        boolean failEnabled = false;
        int failOn = 0;
        int firstRecordNumber = 0;

        private ForcedFailureConfig(AccountDataObject item) {
            if (failOnStr != null && !failOnStr.isEmpty()) {
                failEnabled = true;
                failOn = Integer.parseInt(failOnStr);
                firstRecordNumber = item.getAccountNumber();
            }
        }
    }

}
