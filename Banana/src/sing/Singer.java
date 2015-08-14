package sing;

public class Singer {

    private static Singer singer;
    
    private Singer() {
        
    }
    
    public Singer getInstance() {
        if (singer == null) {
            singer = new Singer();
        }
        return singer;
    }
    
}
