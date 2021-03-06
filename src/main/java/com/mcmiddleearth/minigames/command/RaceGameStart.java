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
public class RaceGameStart extends AbstractGameCommand{
    
    public RaceGameStart(String... permissionNodes) {
        super(0, true, permissionNodes);
        cmdGroup = CmdGroup.RACE;
        setShortDescription(": Starts a race.");
        setUsageDescription(": Cages the players at the start line. After countdown from 10 seconds the race is started.");
    }
    
    @Override
    protected void execute(CommandSender cs, String... args) {
        AbstractGame game = getGame((Player) cs);
        if(game != null && isManager((Player) cs, game)
                        && isCorrectGameType((Player) cs, game, GameType.RACE)) {
            if(!game.isAnnounced()) {
                sendNotAnnouncedErrorMessage(cs);
                return;
            }
            RaceGame raceGame = (RaceGame) game;
            if(!raceGame.hasStart() || !raceGame.hasFinish()) {
                sendNoStartFinishMessage(cs);
                return;
            }
            if(raceGame.isStarted()) {
                sendAlreadySteadyMessage(cs);
                return;
            }
            if(raceGame.getPlayers().size()<2) {
                sendNotEnoughPlayerMessage(cs);
                return;
            }
            raceGame.steady();
        }
    }

    private void sendNotEnoughPlayerMessage(CommandSender cs) {
        PluginData.getMessageUtil().sendErrorMessage(cs, "You need at least two players for a race.");
    }
    private void sendAlreadySteadyMessage(CommandSender cs) {
        PluginData.getMessageUtil().sendErrorMessage(cs, "You already started the race.");
    }

    private void sendNoStartFinishMessage(CommandSender cs) {
        PluginData.getMessageUtil().sendErrorMessage(cs, "A race needs a start and finish.");
    }
}
