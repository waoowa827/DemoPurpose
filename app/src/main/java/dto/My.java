package dto;

import java.util.ArrayList;

public class My {
    private String name;
    private String os;
    private Versions AllVersion;
    private ArrayList<Device> AndroidVersion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Versions getAllVersion() {
        return AllVersion;
    }

    public void setAllVersion(Versions allVersion) {
        AllVersion = allVersion;
    }

    public ArrayList<Device> getAndroidVersion() {
        return AndroidVersion;
    }

    public void setAndroidVersion(ArrayList<Device> androidVersion) {
        AndroidVersion = androidVersion;
    }
}
