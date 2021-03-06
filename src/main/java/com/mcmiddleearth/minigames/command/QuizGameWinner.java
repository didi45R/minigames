/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mcmiddleearth.minigames.command;

import com.mcmiddleearth.minigames.data.PluginData;
import com.mcmiddleearth.minigames.game.AbstractGame;
import com.mcmiddleearth.minigames.game.GameType;
import com.mcmiddleearth.minigames.game.QuizGame;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Eriol_Eandur
 */
public class QuizGameWinner extends AbstractGameCommand{
    
    public QuizGameWinner(String... permissionNodes) {
        super(0, true, permissionNodes);
        cmdGroup = CmdGroup.LORE_QUIZ;
        setShortDescription(": Announces the winner of a quiz game.");
        setUsageDescription(": Announces the winner of a quiz game. If two or more players have the same score after the last question, no winner will be announced automatically. The manager can add more questions or announce multiple winners with this command.");
    }
    
    @Override
    protected void execute(CommandSender cs, String... args) {
        AbstractGame game = getGame((Player) cs);
        if(game != null && isManager((Player) cs, game) 
                        && isCorrectGameType((Player) cs, game, GameType.LORE_QUIZ)) {
            QuizGame quizGame = (QuizGame) game;
            if(!quizGame.announceWinner(true)) {
                sendNoWinnerMessage(cs);
            }
        }
    }

    private void sendNoWinnerMessage(CommandSender cs) {
        PluginData.getMessageUtil().sendErrorMessage(cs, "There is no winner.");
    }
}
