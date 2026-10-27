package org.example.config;
import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources("classpath:config.properties")
public interface AppConfig extends Config {

    @Key("baseUrl")
    String baseUrl();

    @Key("apiUrl")
    String apiUrl();

    @Key("elementTimeout")
    long elementTimeout();

    @Key("logging.enabled")
    boolean loggingEnabled();

    @Key("logging.level")
    String loggingLevel();

    @Key("admin.username")
    String adminUsername();

    @Key("admin.password")
    String adminPassword();

    @Key("startGood.name")
    String startGoodName();

    @Key("startGood.price")
    double startGoodPrice();
}