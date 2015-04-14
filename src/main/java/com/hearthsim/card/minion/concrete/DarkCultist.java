package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.event.filter.FilterCharacterUntargetedDeathrattle;
import com.hearthsim.event.deathrattle.DeathrattleEffectRandomMinion;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.event.effect.CardEffectCharacterBuffDelta;

public class DarkCultist extends Minion {

    private final static CardEffectCharacter darkCultistEffect = new CardEffectCharacterBuffDelta(0, 3);

    private final static FilterCharacterUntargetedDeathrattle filter = new FilterCharacterUntargetedDeathrattle() {
        @Override
        protected boolean includeOwnMinions() {
            return true;
        }
    };

    public DarkCultist() {
        super();
        deathrattleAction_ = new DeathrattleEffectRandomMinion(DarkCultist.darkCultistEffect, DarkCultist.filter);
    }
}
