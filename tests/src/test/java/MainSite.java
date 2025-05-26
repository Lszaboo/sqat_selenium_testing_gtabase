public class MainSite extends GTASite{
    
    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/";
    }

    public MainSite(WebTools wTools){
        super(wTools);
    }

    public static MainSite enter(WebTools wTools){
        wTools.getDriver().get("https://www.gtabase.com/");
        MainSite site = new MainSite(wTools);
        return site;
    }

}
