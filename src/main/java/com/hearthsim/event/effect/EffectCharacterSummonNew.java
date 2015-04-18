package com.hearthsim.event.effect;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class EffectCharacterSummonNew<T extends Card> implements EffectCharacter<T> {
    private Class<? extends Minion> minionClass;

    private int count;

    private boolean atEnd;

    public EffectCharacterSummonNew(Class<? extends Minion> minionClass) {
        this(minionClass, 1);
    }

    public EffectCharacterSummonNew(Class<? extends Minion> minionClass, int count) {
        this(minionClass, count, false);
    }

    public EffectCharacterSummonNew(Class<? extends Minion> minionClass, int count, boolean atEnd) {
        this.minionClass = minionClass;
        this.count = count;
        this.atEnd = atEnd;
    }

    @Override
    public HearthTreeNode applyEffect(PlayerSide originSide, T origin, PlayerSide targetSide, int targetCharacterIndex, HearthTreeNode boardState) {
        for (int i = 0; i < this.count; i++) {
            PlayerModel player = boardState.data_.modelForSide(targetSide);
            if (player.isBoardFull()) {
                break;
            }

            try {
                Minion summon = this.minionClass.newInstance();
                summon.hasBeenUsed(true);
                if (atEnd) {
                    boardState = summon.summonMinionAtEnd(targetSide, boardState, true, false);
                } else {
                    boardState = summon.summonMinion(targetSide, targetCharacterIndex, boardState, true, false);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // if no origin is set then we have no idea whether we are in the original state. copy our base minion and summon a copy.
        // this is used for Minions with RNG battlecries (e.g. Bomb Lobber)
//        if (origin == null) {
//            summon = (Minion)minion.deepCopy();
//        }
        return boardState;
    }
}
