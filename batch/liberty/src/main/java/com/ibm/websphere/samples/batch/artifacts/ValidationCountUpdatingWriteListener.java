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

import java.util.List;

import javax.batch.api.chunk.listener.AbstractItemWriteListener;
import javax.batch.runtime.context.StepContext;
import javax.inject.Inject;

/**
 * Though we may have been able to overload the reader's checkpoint, we purposely
 * go out of our way to instead make use of persistent userdata in this class to
 * count the number of records validated in this step (or this partition).
 */
public class ValidationCountUpdatingWriteListener extends AbstractItemWriteListener {

    @Inject
    StepContext stepCtx;

    @Override
    public void afterWrite(List<Object> items) throws Exception {

        Integer previousCount = (Integer) stepCtx.getPersistentUserData();
        if (previousCount == null) {
            previousCount = 0;
        }

        // Update count based on new chunk size
        Integer newCount = previousCount + items.size();

        stepCtx.setPersistentUserData(newCount);

        // Updating exit status will be a useful way to aggregate the
        // per-partition counts.
        stepCtx.setExitStatus(Integer.toString(newCount));
    }

}
