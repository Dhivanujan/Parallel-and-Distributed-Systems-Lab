class Drop {
    private String message;
    private boolean empty = true;

    public synchronized String take() {
        while (empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        String result = message;
        empty = true;

        notifyAll();

        return result;
    }

    public synchronized void put(String msg) {
        while (!empty) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        this.message = msg;
        empty = false;

        notifyAll();
    }
}

public class MessageDrop {
    public static void main(String[] args) {
        Drop drop = new Drop();

        Thread producer = new Thread(() -> {
            String[] messages = {"Data 1", "Data 2", "Data 3", "DONE"};
            for (String msg : messages) {
                drop.put(msg);
                System.out.println("Produced: " + msg);
            }
        });

        Thread consumer = new Thread(() -> {
            for (String msg = drop.take();
                 !msg.equals("DONE");
                 msg = drop.take()) {

                System.out.println("Consumed: " + msg);
            }
        });

        producer.start();
        consumer.start();
    }
}