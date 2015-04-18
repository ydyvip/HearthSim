package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionBattlecryInterface;
import com.hearthsim.event.effect.EffectCharacter;
import com.hearthsim.event.effect.EffectCharacterBuffDelta;
import com.hearthsim.event.effect.EffectCharacterHeal;
import com.hearthsim.event.filter.FilterCharacter;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class MetaltoothLeaper extends Minion implements MinionBattlecryInterface {

    private static final EffectCharacter<Card> effect = new EffectCharacterBuffDelta<>(2, 0);

    private static final FilterCharacter filter = new FilterCharacter() {
        @Override
        protected boolean includeOwnMinions() {
            return true;
        }

        @Override
        protected boolean excludeSource() {
            return true;
        }

        @Override
        protected MinionTribe tribeFilter() {
            return MinionTribe.MECH;
        }
    };

    public MetaltoothLeaper() {
        super();
    }

    @Override
    public EffectCharacter<Minion> getBattlecryEffect() {
        return (PlayerSide originSide, Minion origin, PlayerSide targetSide, int minionPlacementIndex, HearthTreeNode boardState) ->
            this.effectAllUsingFilter(MetaltoothLeaper.effect, MetaltoothLeaper.filter, boardState);
    }
}
