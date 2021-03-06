/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcmiddleearth.minigames.command;

import com.mcmiddleearth.minigames.data.PluginData;
import com.mcmiddleearth.minigames.game.AbstractGame;
import com.mcmiddleearth.minigames.game.GameType;
import com.mcmiddleearth.minigames.game.RaceGame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Eriol_Eandur
 */
public class RaceGameStop extends AbstractGameCommand{
    
    public RaceGameStop(String... permissionNodes) {
        super(0, true, permissionNodes);
        cmdGroup = CmdGroup.RACE;
        setShortDescription(": Stops a race.");
        setUsageDescription(": Aborts an already started race. Useful when there is a problem with a checkpoint or to let some other players join the race. Race can be started again.");
    }
    
    @Override
    protected void execute(CommandSender cs, String... args) {
        AbstractGame game = getGame((Player) cs);
        if(game != null && isManager((Player) cs, game)
                        && isCorrectGameType((Player) cs, game, GameType.RACE)) {
            RaceGame raceGame = (RaceGame) game;
            if(!raceGame.isStarted()) {
                sendNotStartedMessage(cs);
            }
            raceGame.stop();
        }
    }

    private void sendNotStartedMessage(CommandSender cs) {
        PluginData.getMessageUtil().sendErrorMessage(cs, "Race not started yet.");
    }
}
