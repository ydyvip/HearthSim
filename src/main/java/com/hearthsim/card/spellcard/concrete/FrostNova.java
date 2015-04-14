package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.event.effect.CardEffectOnResolveAoeInterface;

public class FrostNova extends SpellCard implements CardEffectOnResolveAoeInterface {

    /**
     * This freeze all enemy minions
     */
    public FrostNova() {
        super();
    }

    @Override
    public CardEffectCharacter getAoeEffect() {
        return CardEffectCharacter.FREEZE;
    }

    @Override
    public FilterCharacter getAoeFilter() {
        return FilterCharacter.ENEMY_MINIONS;
    }
}
