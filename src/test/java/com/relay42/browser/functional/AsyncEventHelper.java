package com.relay42.browser.functional;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.with;

/**
 * Helper class, used to wait for asynchronous events during testing.
 */
public final class AsyncEventHelper {

    private static final TimeUnit MAX_WAIT_TIME_UNIT = TimeUnit.MINUTES;
    private static final TimeUnit DELAY_TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final TimeUnit POLL_INTERVAL_TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final int DEFAULT_MAX_WAIT_TIME = 5;
    private static final int DEFAULT_DELAY_TIME = 5000;

    private AsyncEventHelper() { }

    /**
     * Wait until the given method is asserted as true.
     * Uses default values for delay time, max wait time and poll interval.
     *
     * @param method - method whose result should be asserted
     * @param alias - name for this waiting instance
     */
    public static <T> void await(Callable<T> method, String alias) {
        await(method, Matchers.equalTo(true), alias);
    }

    /**
     * Wait until the given method resolves so the provided matcher condition is satisfied.
     * Uses default values for delay time, max wait time and poll interval.
     *
     * @param method - method whose result should be asserted
     * @param matcher - matcher instance
     * @param alias - name for this waiting instance
     */
    public static <T> void await(Callable<T> method, Matcher<? super T> matcher, String alias) {
        await(method, matcher, alias, DEFAULT_MAX_WAIT_TIME, DEFAULT_DELAY_TIME);
    }

    /**
     * Wait until the given method resolves so the provided matcher condition is satisfied.
     * Uses default values for delay time.
     *
     * @param method - method whose result should be asserted
     * @param matcher - matcher instance
     * @param alias - name for this waiting instance
     * @param maxWaitTime - maximum time to wait
     * @param delayTime - time to wait before the first pool attempt
     */
    public static <T> void await(Callable<T> method, Matcher<? super T> matcher, String alias, int maxWaitTime, int delayTime) {
        await(method, matcher, alias, maxWaitTime, delayTime, DEFAULT_DELAY_TIME);
    }

    /**
     * Wait until the given method resolves so the provided matcher condition is satisfied.
     *
     * @param method - method whose result should be asserted
     * @param matcher - matcher instance
     * @param alias - name for this waiting instance
     * @param maxWaitTime - maximum time to wait
     * @param delayTime - time to wait before the first pool attempt
     * @param pollInterval - polling interval
     */
    public static <T> void await(Callable<T> method,
                                 Matcher<? super T> matcher,
                                 String alias,
                                 int maxWaitTime,
                                 int delayTime,
                                 int pollInterval) {
        with().pollInterval(pollInterval, POLL_INTERVAL_TIME_UNIT)
                .with().pollDelay(delayTime, DELAY_TIME_UNIT)
                .await(alias).atMost(maxWaitTime, MAX_WAIT_TIME_UNIT)
                .until(method, matcher);
    }
}
