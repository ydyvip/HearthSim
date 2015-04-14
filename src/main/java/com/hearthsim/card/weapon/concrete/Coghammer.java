package com.hearthsim.card.weapon.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.weapon.WeaponCard;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.event.effect.CardEffectOnResolveRandomCharacterInterface;

public class Coghammer extends WeaponCard implements CardEffectOnResolveRandomCharacterInterface {
    private static final CardEffectCharacter effect = (originSide, origin, targetSide, targetCharacterIndex, boardState) -> {
        Minion targetCharacter = boardState.data_.modelForSide(targetSide).getCharacter(targetCharacterIndex);
        targetCharacter.setDivineShield(true);
        targetCharacter.setTaunt(true);
        return boardState;
    };

    @Override
    public CardEffectCharacter getRandomTargetEffect() {
        return Coghammer.effect;
    }

    @Override
    public FilterCharacter getRandomTargetFilter() {
        return FilterCharacter.FRIENDLY_MINIONS;
    }
}
