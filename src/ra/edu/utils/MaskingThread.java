package ra.edu.utils;

class MaskingThread implements Runnable {
    private volatile boolean stop;
    private String prompt;

    // Khi khởi tạo, in prompt nhập mật khẩu
    public MaskingThread(String prompt) {
        this.prompt = prompt;
        System.out.print(prompt);
    }

    public void run() {
        try {
            // Vòng lặp liên tục in lại dấu "*" để thay thế ký tự nhập
            while (!stop) {
                // "\010" là ký tự backspace: nó xoá ký tự cuối hiển thị.
                System.out.print("\010*");
                // Tăng khoảng thời gian nếu cần thiết (50ms ở đây)
                Thread.sleep(50);
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopMasking() {
        this.stop = true;
    }
}