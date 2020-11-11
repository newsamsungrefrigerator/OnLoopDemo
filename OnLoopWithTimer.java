
public class OnLoopWithTimer {
    public static class FourWDBot {
        public FourWDBot(){
            System.out.println("FourWDBot: init");
        }
        public void sleep(int milliseconds){
            try
            {
                Thread.sleep(milliseconds);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }            
        }
        public void onLoop() throws Exception{
            onLoop(500);
	}
	long lastOnLoopFinished = 0;
        public void onLoop(int interval) throws Exception{
            long start = System.currentTimeMillis();
            if (lastOnLoopFinished > 0 && start - lastOnLoopFinished > interval){
                throw new Exception("onLoop() has been called too long (" + (start - lastOnLoopFinished) + ") ago");
            }
            //System.out.println("FourWDBot OnLoop start ");
            this.onTick();
            long timeElapsed = System.currentTimeMillis() - start;
            System.out.println("FourWDBot OnLoop stop @ " + timeElapsed);
            if (timeElapsed > interval){
                throw new Exception("onTick() took too long (" + timeElapsed + ") to finish");
            }
            sleep(interval - (int)timeElapsed);
            lastOnLoopFinished = System.currentTimeMillis();

        }
        protected void onTick(){
            System.out.println("FourWDBot OnTick : do nothing");
        }
        
        // not block method
        public void driveByHand(int direction){
            System.out.println("FourWDBot driveByHand at direction " + direction);
        }
    }

    public static class GryoBot extends FourWDBot{
        public GryoBot(){
            super();
            System.out.println("GryoBot: init");
        }
	// blocking method
        public void driveStraightByDistance(int distance) throws Exception{
            int x = 0;
            while (x < distance){
                System.out.println("GryoBot driveStraight : step " + x);
	        onLoop(); 
                x ++;
            }
        }
    }

    
    public static class OdemetryBot extends GryoBot{
        public int x,y;
        public OdemetryBot(){
            super();
            System.out.println("OdemetryBot: init");
            x = y = 0;
        }
        public void localize(){
            x++;
            y--;
            System.out.println("OdemetryBot localize at x : " + x);
        }
        protected void onTick(){
            System.out.println("OdemetryBot OnTick : calculate position");
            super.onTick();
            localize();
        }
    }

    public static void main(String args[]) throws Exception {
        OdemetryBot bot = new OdemetryBot();
        // Call a blocking method
        bot.driveStraightByDistance(10);
        int i = 0;
	// Call not blocking method in a loop
        while (i < 10){
           bot.driveByHand(i % 4);
           bot.onLoop(); 
           i++;
        }
    }
}
