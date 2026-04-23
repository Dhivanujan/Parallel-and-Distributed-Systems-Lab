class WorkerTask implements Runnable {
    private int taskId;

    public WorkerTask(int id) {
        this.taskId = id;
    }

    @Override
    public void run() {
        System.out.println("Task " + taskId + " is running on "
                + Thread.currentThread().getName());
    }
}

public class HelloRunnable {
    public static void main(String[] args) {
        System.out.println("Master thread starting.");

        WorkerTask task1 = new WorkerTask(1);
        WorkerTask task2 = new WorkerTask(2);

        Thread t1 = new Thread(task1, "Worker-Thread-1");
        Thread t2 = new Thread(task2, "Worker-Thread-2");

        t1.start();
        t2.start();

        System.out.println("Master thread finishing.");
    }
}