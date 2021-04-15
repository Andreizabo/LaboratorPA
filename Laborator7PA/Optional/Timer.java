public class Timer implements Runnable {

    private Game game;

    private long startTime;
    private long maxTime;
    private final long ONE_SECOND = 1000000000;

    public Timer(Game game, long maxSeconds) {
        this.game = game;
        maxTime = maxSeconds;
    }

    @Override
    public void run() {
        startTime = System.nanoTime();
        while (!game.isBoardEmpty()) {
            if(System.nanoTime() - startTime > maxTime * ONE_SECOND) {
                game.forceEnd();
                return;
            }
        }
    }

    public void getTime() {
        System.out.print("\n" + (double)(System.nanoTime() - startTime) / ONE_SECOND + " seconds have passed\n");
    }
}
