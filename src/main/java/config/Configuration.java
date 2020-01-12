package config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:conf/${env}.properties"})
public interface Configuration extends Config {

    String target();

    @Key("url.base")
    String url();

    String timeout();

    @Key("grid.url")
    String gridUrl();

    @Key("grid.port")
    String gridPort();

    @Key("faker.locale")
    String faker();
}
