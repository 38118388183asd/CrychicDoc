package com.mojang.realmsclient.gui.task;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public interface RepeatedDelayStrategy {

    RepeatedDelayStrategy CONSTANT = new RepeatedDelayStrategy() {

        @Override
        public long delayCyclesAfterSuccess() {
            return 1L;
        }

        @Override
        public long delayCyclesAfterFailure() {
            return 1L;
        }
    };

    long delayCyclesAfterSuccess();

    long delayCyclesAfterFailure();

    static RepeatedDelayStrategy exponentialBackoff(final int int0) {
        return new RepeatedDelayStrategy() {

            private static final Logger LOGGER = LogUtils.getLogger();

            private int failureCount;

            @Override
            public long delayCyclesAfterSuccess() {
                this.failureCount = 0;
                return 1L;
            }

            @Override
            public long delayCyclesAfterFailure() {
                this.failureCount++;
                long $$0 = Math.min(1L << this.failureCount, (long) int0);
                LOGGER.debug("Skipping for {} extra cycles", $$0);
                return $$0;
            }
        };
    }
}