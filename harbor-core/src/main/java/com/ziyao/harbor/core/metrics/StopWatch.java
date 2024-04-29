package com.ziyao.harbor.core.metrics;


import lombok.Getter;

import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyao zhang
 * @since 2023/9/27
 */
@Getter
public final class StopWatch {

    /**
     * 初始容量
     */
    private static final int INITIAL_CAPACITY = 8;
    private static final NumberFormat nf;
    private static final NumberFormat pf;

    static {
        nf = NumberFormat.getNumberInstance();
        nf.setMinimumIntegerDigits(9);
        nf.setGroupingUsed(false);
        pf = NumberFormat.getPercentInstance();
        pf.setMinimumIntegerDigits(3);
        pf.setGroupingUsed(false);
    }

    /**
     * 任务id
     */
    private final String taskId;
    /**
     * 任务列表
     */
    private final Map<String, Task> watches;
    /**
     * 完成的任务列表
     */
    private final List<Task> completedTasks;
    /**
     * 任务总耗时
     * -- GETTER --
     * 获取所有任务的总时间（以毫秒为单位）
     */
    @Getter
    private long totalTimeMillis;
    /**
     * 任务开始时间
     */
    private long startTimeMillis;
    // 任务总数
    private int taskCount;
    // 正在执行任务数
    public int runningTaskCount;

    public StopWatch() {
        this(Thread.currentThread().getName());
    }

    public StopWatch(final String taskId) {
        notNull(taskId, "任务id不能为空！");
        this.taskId = taskId;
        this.watches = new ConcurrentHashMap<>(INITIAL_CAPACITY);
        this.completedTasks = new LinkedList<>();
    }

    /**
     * 启动总任务计时
     */
    public void start() {
        notNull(this.taskId, "任务id不能为空！");
        if (getWatches().containsKey(this.taskId)) {
            throw new IllegalStateException("Can't start StopWatch: it's already running. taskId=" + taskId);
        }
        if (this.startTimeMillis == 0) {
            this.startTimeMillis = System.currentTimeMillis();
        }
        watches.put(this.taskId, new Task(this.taskId));
        statistics();
    }

    /**
     * 启动指定任务计时
     *
     * @param taskId 任务id
     */
    public void start(final String taskId) {
        notNull(taskId, "任务id不能为空！");
        if (getWatches().containsKey(taskId)) {
            throw new IllegalStateException("Can't start StopWatch: it's already running. taskId=" + taskId);
        }
        if (this.startTimeMillis == 0) {
            this.startTimeMillis = System.currentTimeMillis();
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
        notNull(taskList, "任务列表为空！");
        for (Task task : taskList.values()) {
            // 如果任务在运行执行停止
            if (task.isRunning()) {
                task.stop();
                completedTasks.add(task);
            }
        }
        this.runningTaskCount = 0;
        if (this.startTimeMillis != 0) {
            this.totalTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        }
    }

    /**
     * 停止指定任务
     *
     * @param taskId 任务id
     */
    public void stop(final String taskId) {
        notNull(taskId, "任务id不能为空！");
        Task task = watches.get(taskId);
        notNull(task, "Can't stop StopWatch: it's not running");
        task.stop();
        watches.put(taskId, task);
        completedTasks.add(task);
        this.runningTaskCount = this.runningTaskCount - 1;
        if (this.startTimeMillis != 0) {
            this.totalTimeMillis = System.currentTimeMillis() - this.startTimeMillis;
        }
    }

    /**
     * 打印所有任务列表
     *
     * @return 返回String类型的任务列表信息
     */
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder("\n《执行时长详情》");
        sb.append("\n");
        if (completedTasks == null || completedTasks.isEmpty()) {
            sb.append("No task info kept");
        } else {
            sb.append("---------------------------------------------\n");
            sb.append("ms(毫秒)      %(占比)      Task name(任务名称)\n");
            sb.append("---------------------------------------------\n");
            boolean totalTimeNoZero = getTotalTimeMillis() != 0;
            for (Task task : completedTasks) {
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

    /**
     * 简短信息打印
     */
    public String shortSummary() {
        //汇总
        return "(总计)   Name: " + getTaskId() + "    Time: " + getTotalTimeMillis() + " ms";
    }

    //统计
    private void statistics() {
        this.taskCount = this.taskCount + 1;
        this.runningTaskCount = this.runningTaskCount + 1;
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
         * 获取所有任务的总时间（以毫秒为单位）
         */
        private long timeMillis;
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
            this.startTime = System.currentTimeMillis();
            this.running = true;
        }

        public void stop() {
            this.stopTime = System.currentTimeMillis();
            this.timeMillis = stopTime - startTime;
            this.running = false;
        }
    }

    /**
     * Assert that a string is not {@code null}.
     *
     * @param value   要检查的字符串
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(String value, String message) {
        if (isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }


    /**
     * Assert that an Object is not {@code null}.
     *
     * @param object  要断言的对象
     * @param message 断言失败后返回的异常信息
     */
    public static void notNull(Object object, String message) {
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 检查给定的对象（可能是 {@code String}）是否为空。
     *
     * @param str 候选对象（可能是 {@code String}）
     */
    public static boolean isEmpty(Object str) {
        return (str == null || "".equals(str));
    }
}
