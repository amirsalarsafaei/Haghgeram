package com.SalarJavaDevGroup.Listeners;

import com.SalarJavaDevGroup.GraphicAgent;
import javafx.event.Event;
import javafx.event.EventHandler;

public class BlockButtonListener implements EventHandler {
    String targetUser;
    public BlockButtonListener(String target) {
        super();
        targetUser = target;
    }

    @Override
    public void handle(Event event) {
        GraphicAgent.serverAgent.profileAgent.toggleBlock(GraphicAgent.username, GraphicAgent.password, targetUser);
    }
}
