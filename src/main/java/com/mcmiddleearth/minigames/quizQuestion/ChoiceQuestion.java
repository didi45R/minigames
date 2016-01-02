/*
 * Copyright (C) 2015 MCME
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mcmiddleearth.minigames.quizQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import lombok.Getter;

/**
 *
 * @author Eriol_Eandur
 */

public class ChoiceQuestion extends AbstractQuestion {

    @Getter
    protected final String[] answers;
    
    protected final boolean[] correctAnswers = new boolean[answerCount];
    
    public final static int answerCount = 4;
    
    public ChoiceQuestion(String question, String[] answers, String correctAnswers) {
        this(question, QuestionType.MULTI, answers, correctAnswers);
    }

    protected ChoiceQuestion(String question, QuestionType type, String[] answers, String correctAnswers) {
        super(question, type);
        this.answers = answers;
        setCorrectAnswers(correctAnswers);
    }

    public String[] getInRandomOrder() {
        String[] result = new String[answerCount];
        for(int i = 0; i< answerCount; i++) {
            int rand = (int) Math.round(Math.floor(answerCount*Math.random()));
Logger.getGlobal().info("inrandomOrder "+ rand);            
            while(result[rand]!=null) {
Logger.getGlobal().info("inrandomOrder search"+ rand);            
                rand++;
                if(rand==answerCount) {
                    rand=0;
Logger.getGlobal().info("inrandomOrder to first "+ rand);            
                }
            }
Logger.getGlobal().info("inrandomOrder finised "+ rand);            
            result[rand]=getAnswerCharacter(i)+answers[i];
        }
        return result;
    }
    
    public String[] getInProperOrder() {
        String[] result = new String[answerCount];
        for(int i=0; i<answerCount;i++) {
            result[i] = getAnswerCharacter(i)+answers[i];
        }
        return result;
    }
    
    public void setCorrectAnswers(String correctLetters) {
        correctLetters = correctLetters.trim();
        while(correctLetters.length()>0) {
            if(isLetterValid(correctLetters.charAt(0))) {
                correctAnswers[getAnswerIndex(correctLetters.charAt(0))] = true;
            }
            correctLetters = correctLetters.substring(1).trim();
        }
    }
    
    public String getCorrectAnswers() {
        String result = "";
        for(int i = 0;i<answerCount;i++) {
            if(correctAnswers[i]) {
                result = result+getAnswerCharacter(i);
            }
        }
        return result;
    }
    
    public static int getAnswerIndex(char answer) {
        switch(answer) {
            case 'A': case'a': 
                return 0;
            case 'B': case'b': 
                return 1;
            case 'C': case'c': 
                return 2;
            case 'D': case'd': 
                return 3;
            default: 
                return -1;
        }
    }
    
    public static boolean isLetterValid(char answer) {
        return getAnswerIndex(answer)>=0;
    }
    
    public static boolean isAnswerValid(String answer) {
        String answerCopy = answer+"";
        answerCopy = answerCopy.trim();
        while(answerCopy.length()>0) {
            if(!isLetterValid(answerCopy.charAt(0))) {
                return false;
            }
            answerCopy = answerCopy.substring(1).trim();
        }
        return true;
    }

    public static char getAnswerCharacter(int index) {
        switch(index) {
            case 0: 
                return 'A';
            case 1: 
                return 'B';
            case 2: 
                return 'C';
            case 3: 
                return 'D';
            default:
                return 'x';
        }
    }
    
    private boolean isIn(Character letter, Character[] answers) {
        int letterIndex = getAnswerIndex(letter);
        for(char search: answers) {
            if(letterIndex == getAnswerIndex(search)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isCorrectAnswer(Character[] answers) {
        for(int i = 0;i<answerCount;i++) {
            char answerLetter = getAnswerCharacter(i);
            if(correctAnswers[i]!=isIn(answerLetter,answers)) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isCorrectAnswer(String answer) {
        return isCorrectAnswer(parseAnswer(answer));
    }
    
    public static Character[] parseAnswer(String answer) {
        answer = answer.trim();
        List<Character> answerList = new ArrayList<>();
        while(answer.length()>0) {
            answerList.add(answer.charAt(0));
            answer = answer.substring(1).trim();
        }
        return answerList.toArray(new Character[0]);
    }
}