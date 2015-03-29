package com.hearthsim.card.spellcard.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.SpellCard;
import com.hearthsim.event.CharacterFilter;
import com.hearthsim.event.CharacterFilterTargetedBattlecry;
import com.hearthsim.event.effect.CardEffectCharacter;
import com.hearthsim.event.CharacterFilterTargetedSpell;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.CardDrawNode;
import com.hearthsim.util.tree.HearthTreeNode;

public class DivineFavor extends SpellCard {

    private final static CharacterFilter filter = new CharacterFilterTargetedSpell() {
        protected boolean includeOwnHero() { return true; }

        @Override
        public boolean targetMatches(PlayerSide originSide, Card origin, PlayerSide targetSide, Minion targetCharacter, BoardModel board) {
            if (!super.targetMatches(originSide, origin, targetSide, targetCharacter, board)) {
                return false;
            }

            int numCardsToDraw = board.modelForSide(targetSide).getHand().size() - board.modelForSide(originSide).getHand().size() + 1;
            if (numCardsToDraw < 1) {
                return false;
            }
            return true;
        }
    };

    /**
     * Constructor
     *
     * @param hasBeenUsed Whether the card has already been used or not
     */
    @Deprecated
    public DivineFavor(boolean hasBeenUsed) {
        this();
        this.hasBeenUsed = hasBeenUsed;
    }

    /**
     * Constructor
     *
     * Defaults to hasBeenUsed = false
     */
    public DivineFavor() {
        super();

        this.characterFilter = DivineFavor.filter;
    }

    /**
     *
     * Use the card on the given target
     *
     * Draw cards until you have as many in hand as your opponent
     *
     *
     *
     * @param side
     * @param boardState The BoardState before this card has performed its action.  It will be manipulated and returned.
     *
     * @return The boardState is manipulated and returned
     */
    @Override
    protected CardEffectCharacter getEffect() {
        if (this.effect == null) {
            this.effect = new CardEffectCharacter() {
                @Override
                public HearthTreeNode applyEffect(PlayerSide originSide, Card origin, PlayerSide targetSide, int targetCharacterIndex, HearthTreeNode boardState) throws HSException {
                    PlayerModel currentPlayer = boardState.data_.modelForSide(originSide);
                    PlayerModel waitingPlayer = boardState.data_.modelForSide(targetSide);

                    int numCardsToDraw = waitingPlayer.getHand().size() - currentPlayer.getHand().size() + 1;
                    if (numCardsToDraw < 1) {
                        return null;
                    }

                    if (boardState instanceof CardDrawNode) {
                        ((CardDrawNode) boardState).addNumCardsToDraw(numCardsToDraw);
                    } else {
                        boardState = new CardDrawNode(boardState, numCardsToDraw); //draw two cards
                    }
                    return boardState;
                }
            };
        }
        return this.effect;
    }
}
