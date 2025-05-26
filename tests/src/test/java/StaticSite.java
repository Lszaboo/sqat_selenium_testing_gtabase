public class StaticSite extends GTASite{
    @Override
    protected String expectedUrl(){
        return staticUrl;
    }

    protected final String staticUrl;

    public StaticSite(WebTools wTools, String staticUrl){
        
        super(wTools);
        this.staticUrl = staticUrl;
    }

    public static StaticSite enter(WebTools wTools, String staticUrl){
        wTools.getDriver().get(staticUrl);
        StaticSite site = new StaticSite(wTools,staticUrl);
        return site;
    }
}
