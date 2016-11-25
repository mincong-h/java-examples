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
package com.ibm.websphere.samples.batch.util;

/**
 *
 */
public interface BonusPayoutConstants {

    public static final String BONUS_PAYOUT_LOGGER = "BonusPayout";
    public static final String FILE_ENCODING_PROP = "fileEncoding";
    public static final String NUM_RECORDS = "numRecords";
    public static final String GENERATE_FILE_NAME_ROOT_PROPNAME = "generateFileNameRoot";
    public static final String DFLT_GEN_FILE_PREFIX = "bonuspayout.outfile";

    // Could make these configurable
    public static final String ACCOUNT_NUM_COLUMN = "ACCTNUM";
    public static final String ACCOUNT_CODE_COLUMN = "ACCTCODE";
    public static final String BALANCE_COLUMN = "BALANCE";
    public static final String INSTANCE_ID_COLUMN = "INSTANCEID";
}
