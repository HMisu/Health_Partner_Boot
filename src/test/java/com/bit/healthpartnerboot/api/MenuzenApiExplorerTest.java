package com.bit.healthpartnerboot.api;

import org.junit.jupiter.api.Test;

class MenuzenApiExplorerTest {
    private final MenuzenApiExplorer apiExplorer = new MenuzenApiExplorer();

    @Test
    void getApi() {
        apiExplorer.getFood(String.valueOf(1));
    }
}