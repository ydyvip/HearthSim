package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.filter.FilterCharacterTargetedBattlecry;
import com.hearthsim.event.effect.CardEffectCharacter;

public class MasterOfDisguise extends Minion implements MinionBattlecryInterface {

    private final static FilterCharacterTargetedBattlecry filter = new FilterCharacterTargetedBattlecry() {
        protected boolean includeOwnMinions() {
            return true;
        }
    };

    private final static CardEffectCharacter battlecryAction = (originSide, origin, targetSide, targetCharacterIndex, boardState) -> {
        Minion targetMinion = boardState.data_.modelForSide(targetSide).getCharacter(targetCharacterIndex);
        targetMinion.setStealthed(true);
        return boardState;
    };

    public MasterOfDisguise() {
        super();
    }

    @Override
    public FilterCharacter getBattlecryFilter() {
        return MasterOfDisguise.filter;
    }

    @Override
    public CardEffectCharacter getBattlecryEffect() {
        return MasterOfDisguise.battlecryAction;
    }
}
