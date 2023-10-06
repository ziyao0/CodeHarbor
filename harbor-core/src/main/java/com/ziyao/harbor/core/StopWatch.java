package com.ziyao.harbor.core;

import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Collections;
import lombok.Getter;

import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2023/9/27
 */
@Getter
public final class StopWatch {

    /**
     * 任务id
     */
    private final String taskId;
    /**
     * 任务列表
     */
    public final Map<String, Task> watches;
    /**
     * 任务总耗时
     */
    private long totalTimeNanos;
    /**
     * 任务开始时间
     */
    private long startTimeNanos;
    // 任务总数
    private int taskCount;
    // 正在执行任务数
    public int runningTaskCount;

    public StopWatch() {
        this(Thread.currentThread().getName());
    }

    public StopWatch(final String taskId) {
        this.taskId = taskId;
        this.watches = new LinkedHashMap<>(8);
    }

    /**
     * 启动总任务计时
     */
    public void start() {
        Assert.notNull(this.taskId, "任务id不能为空！");
        if (getWatches().containsKey(this.taskId)) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        this.startTimeNanos = System.nanoTime();
        watches.put(this.taskId, new Task(this.taskId));
        statistics();
    }

    /**
     * 启动指定任务计时
     *
     * @param taskId 任务id
     */
    public void start(final String taskId) {
        Assert.notNull(taskId, "任务id不能为空！");
        if (getWatches().containsKey(taskId)) {
            throw new IllegalStateException("Can't start StopWatch: it's already running");
        }
        if (this.startTimeNanos == 0) {
            this.startTimeNanos = System.nanoTime();
        }
        watches.put(taskId, new Task(taskId));
        statistics();
    }

    /**
     * 判断当前是否还有任务正在执行
     *
     * @return <code>true</code> 表示当前至少有一个任务正在执行
     */
    public boolean isRunning() {
        for (Task value : watches.values()) {
            if (value.isRunning()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 停止所有正在运行的任务
     */
    public void stop() {
        Map<String, Task> taskList = getWatches();
        Assert.notNull(taskList, "任务列表为空！");
        for (Task task : taskList.values()) {
            // 如果任务在运行执行停止
            if (task.isRunning()) {
                task.stop();
            }
        }
        this.runningTaskCount = 0;
        this.totalTimeNanos = System.nanoTime() - this.startTimeNanos;
    }

    /**
     * 停止指定任务
     *
     * @param taskId 任务id
     */
    public void stop(final String taskId) {
        Assert.notNull(taskId, "任务id不能为空！");
        Task task = watches.get(taskId);
        Assert.notNull(task, "Can't stop StopWatch: it's not running");
        task.stop();
        watches.put(taskId, task);
        this.runningTaskCount = this.runningTaskCount - 1;
        if (this.startTimeNanos != 0) {
            this.totalTimeNanos = System.nanoTime() - this.startTimeNanos;
        }
    }

    /**
     * 打印所有任务列表
     *
     * @return 返回String类型的任务列表信息
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder("执行时长详情");
        sb.append("\n");
        List<Task> tasks = filterRunning();
        if (Collections.isEmpty(tasks)) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------\n");
            sb.append("ms(毫秒)      %(占比)      Task name(任务名称)\n");
            sb.append("---------------------------------------------\n");
            NumberFormat nf = NumberFormat.getNumberInstance();
            nf.setMinimumIntegerDigits(9);
            nf.setGroupingUsed(false);
            NumberFormat pf = NumberFormat.getPercentInstance();
            pf.setMinimumIntegerDigits(3);
            pf.setGroupingUsed(false);
            boolean totalTimeNoZero = getTotalTimeNanos() != 0;
            for (Task task : tasks) {
                sb.append(nf.format(task.getTimeMillis())).append("     ");
                if (totalTimeNoZero) {
                    sb.append(pf.format(task.getTimeMillis() * 1.0 / getTotalTimeMillis())).append("        ");
                } else {
                    sb.append(pf.format(0)).append("        ");
                }
                sb.append(task.getTaskName()).append("\n");
            }
            sb.append("---------------------------------------------\n");
            sb.append(shortSummary());
        }
        return sb.toString();
    }

    private List<Task> filterRunning() {
        if (Collections.isEmpty(getWatches())) {
            return java.util.Collections.emptyList();
        }
        return getWatches().values().stream()
                .filter(t -> !t.isRunning()).collect(Collectors.toList());
    }

    public String shortSummary() {
        //汇总
        return "(总计)   Name: " + getTaskId() + "    Time: " + getTotalTimeMillis() + " ms";
    }

    //统计
    private void statistics() {
        this.taskCount = this.taskCount + 1;
        this.runningTaskCount = this.runningTaskCount + 1;
    }

    /**
     * 获取所有任务的总时间（以毫秒为单位）
     */
    public long getTotalTimeMillis() {
        return nanosToMillis(this.totalTimeNanos);
    }

    /**
     * 获取所有任务的总时间（以秒为单位）
     */
    public double getTotalTimeSeconds() {
        return nanosToSeconds(this.totalTimeNanos);
    }

    private static long nanosToMillis(long duration) {
        return TimeUnit.NANOSECONDS.toMillis(duration);
    }

    /**
     * duration / 1_000_000_000.0
     */
    private static double nanosToSeconds(long duration) {
        return TimeUnit.NANOSECONDS.toSeconds(duration);
    }

    @Getter
    private static final class Task {

        // 任务名称
        private final String taskName;
        // 开始时间
        private long startTime;
        // 结束时间
        private long stopTime;
        /**
         * -- GETTER --
         * 获取所有任务的总时间（以纳秒为单位）
         */
        private long timeNanos;
        // 当前任务是否正在运行
        private boolean running;

        public Task(String taskName) {
            if (taskName == null) {
                throw new IllegalArgumentException("任务名称不能为空！");
            }
            this.taskName = taskName;
            start();
        }

        public void start() {
            this.startTime = System.nanoTime();
            this.running = true;
        }

        public void stop() {
            this.stopTime = System.nanoTime();
            this.timeNanos = stopTime - startTime;
            this.running = false;
        }

        /**
         * 获取所有任务的总时间（以毫秒为单位）
         */
        public long getTimeMillis() {
            return nanosToMillis(this.timeNanos);
        }

        /**
         * 获取所有任务的总时间（以秒为单位）
         */
        public double getTimeSeconds() {
            return nanosToSeconds(this.timeNanos);
        }

        private static long nanosToMillis(long duration) {
            return TimeUnit.NANOSECONDS.toMillis(duration);
        }

        /**
         * duration / 1_000_000_000.0
         */
        private static double nanosToSeconds(long duration) {
            return TimeUnit.NANOSECONDS.toSeconds(duration);
        }
    }
}
