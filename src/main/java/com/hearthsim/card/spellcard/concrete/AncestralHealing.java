package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellTargetableCard;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.filter.FilterCharacterTargetedSpell;
import com.hearthsim.event.effect.CardEffectCharacter;

public class AncestralHealing extends SpellTargetableCard {

    /**
     * Constructor
     *
     * @param hasBeenUsed Whether the card has already been used or not
     */
    @Deprecated
    public AncestralHealing(boolean hasBeenUsed) {
        this();
        this.hasBeenUsed = hasBeenUsed;
    }

    /**
     * Constructor
     *
     * Defaults to hasBeenUsed = false
     */
    public AncestralHealing() {
        super();
    }

    @Override
    public FilterCharacter getTargetableFilter() {
        return FilterCharacterTargetedSpell.ALL_MINIONS;
    }

    /**
     *
     * Use the card on the given target
     *
     * This card heals the target minion up to full health and gives it taunt.  Cannot be used on heroes.
     *
     *
     *
     * @param side
     * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
     *
     * @return The boardState is manipulated and returned
     */
    @Override
    public CardEffectCharacter getTargetableEffect() {
        if (this.effect == null) {
            this.effect = (originSide, origin, targetSide, targetCharacterIndex, boardState) -> {
                Minion targetCharacter = boardState.data_.getCharacter(targetSide, targetCharacterIndex);
                if (targetCharacter.getHealth() < targetCharacter.getMaxHealth())
                    boardState = targetCharacter.takeHealAndNotify((byte) (targetCharacter.getMaxHealth() - targetCharacter.getHealth()), targetSide, boardState);
                targetCharacter.setTaunt(true);
                return boardState;
            };
        }
        return this.effect;
    }
}
