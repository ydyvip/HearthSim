package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.Minion.MinionTribe;
import com.hearthsim.card.spellcard.SpellTargetableCard;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.event.filter.FilterCharacterTargetedSpell;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.model.PlayerModel;

public class TotemicMight extends SpellTargetableCard {

    /**
     * Constructor
     *
     * Defaults to hasBeenUsed = false
     */
    public TotemicMight() {
        super();
    }

    @Override
    public FilterCharacter getTargetableFilter() {
        return FilterCharacterTargetedSpell.SELF;
    }

    /**
     *
     * Use the card on the given target
     *
     * Gives all friendly totems +2 health
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
                PlayerModel currentPlayer = boardState.data_.modelForSide(originSide);
                for (Minion minion : currentPlayer.getMinions()) {
                    if (minion.getTribe() == MinionTribe.TOTEM) {
                        minion.setHealth((byte)(2 + minion.getHealth()));
                        minion.setMaxHealth((byte)(2 + minion.getMaxHealth()));
                    }
                }
                return boardState;
            };
        }
        return this.effect;
    }
}
