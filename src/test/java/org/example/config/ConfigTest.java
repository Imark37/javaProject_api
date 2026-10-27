package org.example.config;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class ConfigTest {

    @Test
    void configIsLoaded() {
        ConfigProvider.printConfig();

        assertThat(ConfigProvider.CONFIG.baseUrl())
                .as("baseUrl из конфига")
                .isEqualTo("http://localhost:8080");

        assertThat(ConfigProvider.CONFIG.adminUsername())
                .as("admin.username из конфига")
                .isEqualTo("admin");
    }
}