package rs.ac.uns.ftn.model.environment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Jasmina on 24/06/2017.
 */
@Component
@ConfigurationProperties(prefix="environment")
public class EnvironmentProperties {

    private String swiftCode;

    private String nbsUrl;

    public void setSwiftCode(String swiftCode){
        this.swiftCode = swiftCode;
    }

    public String getSwiftCode(){
        return this.swiftCode;
    }

    public String getNbsUrl() {
        return nbsUrl;
    }

    public void setNbsUrl(String nbsUrl) {
        this.nbsUrl = nbsUrl;
    }
}
