
public class WithoutOnLoop {
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
        public void driveStraightByDistance(int distance){
            int x = 0;
            while (x < distance){
                System.out.println("GryoBot driveStraight : step " + x);
                //this.localize();
                sleep(500);
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
    }

    public static void main(String args[]) {
        OdemetryBot bot = new OdemetryBot();
        // Call a blocking method
        bot.driveStraightByDistance(10);
        int i = 0;
	// Call not blocking method in a loop
        while (i < 10){
           bot.driveByHand(i % 4);
           //bot.localize();
           bot.sleep(500);
           i++;
        }
    }
}
