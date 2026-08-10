package fr.mickaelbaron.spellwhatroyal.tambouiclient.ui;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;

import dev.tamboui.image.ImageData;
import dev.tamboui.toolkit.app.ToolkitRunner;
import dev.tamboui.toolkit.element.Element;
import fr.mickaelbaron.spellwhatroyal.tambouiclient.controller.SpellWhatRoyalController;

public class SpellWhatRoyalUI {

    private AuthenticationUI refAuthenticationUI;

    private WaitingServerUI refWaitingServerUI;

    private GameUI refGameUI;

    private ResultUI refResultUI;

    private URL currentURL;

    private ImageData currentData;

    private enum STATE {
        login,
        waiting,
        game,
        postresult
    }

    private SpellWhatRoyalController controller;

    private STATE current = STATE.login;

    public SpellWhatRoyalUI(SpellWhatRoyalController ref) throws Exception {
        this.controller = ref;
        refAuthenticationUI = new AuthenticationUI(ref);
        refWaitingServerUI = new WaitingServerUI(ref);
        refGameUI = new GameUI(ref);
        refResultUI = new ResultUI(ref);

        Thread uiThread = new Thread(() -> {
            try {
                try (ToolkitRunner r = ToolkitRunner.builder()
                        .tickRate(Duration.ofMillis(100))
                        .build()) {
                    r.run(this::render);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                controller.close();
            }
        }, "tamboui-render-thread");
        uiThread.setDaemon(false);
        uiThread.start();
    }

    protected Element render() {
        switch (current) {
            case login:
                return refAuthenticationUI.render();
            case waiting:
                return refWaitingServerUI.render();
            case game:
                return refGameUI.render();
            case postresult:
                return refResultUI.render();
            default:
                return refAuthenticationUI.render();
        }
    }

    public void setGameUIOthers(String value) {
        this.refGameUI.setOthers(value);
    }

    public void setGameUICheck(String value) {
        this.refGameUI.setCheck(value);
    }

    public void setGameUIName(String playerName) {
        this.refGameUI.setName(playerName);
    }

    private boolean loadImageIfNeeded(URL gameDataURL) {
        if (gameDataURL == null) {
            return false;
        }

        if (gameDataURL.equals(currentURL) && currentData != null) {
            return true;
        }

        try (InputStream in = gameDataURL.openStream()) {
            currentData = ImageData.fromBytes(in.readAllBytes());
            currentURL = gameDataURL;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void setGameUIImage(URL gameDataURL) {
        if (loadImageIfNeeded(gameDataURL)) {
            refGameUI.setImage(currentData);
        }
    }

    public void setResultUIImage(URL gameDataURL) {
        if (loadImageIfNeeded(gameDataURL)) {
            refResultUI.setImage(currentData);
        }
    }

    public void setGameUITimer(String string) {
        this.refGameUI.setTimer(string);
    }

    public void setGameUIHelp(String help) {
        this.refGameUI.setHelp(help);
    }

    public void setGameUIScore(String string) {
        this.refGameUI.setScore(string);
    }

    public void setResultUIClean() {
        this.refResultUI.clean();
    }

    public void setWaitingUITimer(String string) {
        this.refWaitingServerUI.setTimer(string);
    }

    public void setGameUIclean() {
        this.refGameUI.clean();
    }

    public void setResultUISolution(String value) {
        this.refResultUI.setSolution(value);
    }

    public void setResultUIScore(String string) {
        this.refResultUI.setScore(string);
    }

    public void setResultUIYourValue(String value) {
        this.refResultUI.setYourValue(value);
    }

    public void setResultUIFound(String value) {
        this.refResultUI.setFound(value);
    }

    public void showAuthenticationUI() {
        this.current = STATE.login;
    }

    public void showWaitingServerUI() {
        this.current = STATE.waiting;
    }

    public void showGameUI() {
        this.current = STATE.game;
    }

    public void showResultUI() {
        this.current = STATE.postresult;
    }

    public void addResultUIPlayers(String name, String value, Integer score) {
        this.refResultUI.addPlayer(name, value, score);
    }
}
