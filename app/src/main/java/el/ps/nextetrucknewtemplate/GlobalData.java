package el.ps.nextetrucknewtemplate;

public class GlobalData {
    private static GlobalData instance;
    private String globalName;
    private String globalPassword;

    private GlobalData() {
        // Initialize your global variable here if needed
        globalName = "";
        globalPassword = "";
    }

    public static synchronized GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public String getGlobalName() {
        return globalName;
    }

    public String getGlobalPassword() {
        return globalPassword;
    }

    public void setGlobalName(String globalVariable) {
        this.globalName = globalVariable;
    }

    public void setGlobalPassword(String globalVariable) {
        this.globalPassword = globalVariable;
    }
}
