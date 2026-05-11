// SPDX-License-Identifier: MIT
// Copyright (c) 2024 Huawei Technologies Co., Ltd.
// Copyright (c) 2026 The FIT Lab AI Group

package org.fitframework.schedule.cron.support;

import static org.fitframework.util.ObjectUtils.cast;

import org.fitframework.schedule.cron.CronField;
import org.fitframework.util.TimeUtils;

import java.time.ZonedDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * 表示下一个合法时间的计算器。
 *
 * @author 季聿阶
 * @since 2023-01-04
 */
public class NextDateTimeCalculator {
    private final List<CronField> fields;
    private final List<Function<Temporal, Boolean>> isLastFunctions =
            Arrays.asList(TimeUtils::isLastMonthOfYear, TimeUtils::isLastDayOfMonth, TimeUtils::isLastHourOfDay,
                    TimeUtils::isLastMinuteOfHour, TimeUtils::isLastSecondOfMinute);
    private final List<TemporalAdjuster> forwardFunctions =
            Arrays.asList(TimeUtils.firstTimeOfNextMonth(), TimeUtils.firstTimeOfNextDay(),
                    TimeUtils.firstTimeOfNextHour(), TimeUtils.firstTimeOfNextMinute(),
                    TimeUtils.firstTimeOfNextSecond());
    private final int maxFutureYears;
    private final ZonedDateTime stopDateTime;

    private ZonedDateTime current;
    private int currentIndex = 0;
    private boolean isRollback = false;

    NextDateTimeCalculator(List<CronField> fields, int maxFutureYears, ZonedDateTime datetime) {
        this.fields = fields;
        this.maxFutureYears = maxFutureYears;
        this.current = datetime.plusSeconds(1);
        this.stopDateTime = this.maxDateTime(this.current);
    }

    /**
     * 计算下一个合法的时间。
     *
     * @return 表示找到的下一个合法时间的 {@link Optional}{@code <}{@link ZonedDateTime}{@code >}。
     */
    public Optional<ZonedDateTime> findNextDateTime() {
        while (this.currentIndex >= -1 && this.currentIndex < 5) {
            if (this.currentIndex == -1) {
                this.current = this.current.with(TimeUtils.firstTimeOfNextYear());
                if (!this.current.isBefore(this.stopDateTime)) {
                    break;
                } else {
                    this.currentIndex++;
                    this.isRollback = false;
                }
            } else if (this.isRollback) {
                boolean isLast = this.isLastFunctions.get(this.currentIndex).apply(this.current);
                if (isLast) {
                    this.currentIndex--;
                    this.isRollback = true;
                } else {
                    this.current = cast(this.forwardFunctions.get(this.currentIndex).adjustInto(this.current));
                    this.isRollback = false;
                }
            } else {
                Optional<ZonedDateTime> optional =
                        this.fields.get(this.currentIndex).findCurrentOrNextTime(this.current);
                if (optional.isPresent()) {
                    this.current = optional.get();
                    this.currentIndex++;
                    this.isRollback = false;
                } else {
                    this.currentIndex--;
                    this.isRollback = true;
                }
            }
        }
        if (this.currentIndex == 5) {
            return Optional.of(this.current);
        }
        return Optional.empty();
    }

    private ZonedDateTime maxDateTime(ZonedDateTime current) {
        return current.with(TimeUtils.firstTimeOfNextYear()).plusYears(this.maxFutureYears);
    }
}
