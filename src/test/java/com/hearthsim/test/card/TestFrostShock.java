package com.hearthsim.test.card;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.hearthsim.model.PlayerModel;
import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.spellcard.concrete.FrostShock;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class TestFrostShock {

    private HearthTreeNode board;
    private PlayerModel currentPlayer;
    private PlayerModel waitingPlayer;

    private static final byte mana = 2;
    private static final byte attack0 = 2;
    private static final byte health0 = 3;
    private static final byte health1 = 7;

    @Before
    public void setup() throws HSException {
        board = new HearthTreeNode(new BoardModel());
        currentPlayer = board.data_.getCurrentPlayer();
        waitingPlayer = board.data_.getWaitingPlayer();

        Minion minion0_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion0_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
        Minion minion1_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion1_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);

        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_0);
        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_1);

        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_0);
        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_1);

        currentPlayer.setMana((byte) 10);
        currentPlayer.setMaxMana((byte) 10);
    }

    @Test
    public void test1() throws HSException {
        FrostShock fb = new FrostShock();
        currentPlayer.placeCardHand(fb);

        Card theCard = currentPlayer.getHand().get(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.WAITING_PLAYER, 1, board);
        assertFalse(ret == null);

        assertEquals(currentPlayer.getHand().size(), 0);
        assertEquals(currentPlayer.getNumMinions(), 2);
        assertEquals(waitingPlayer.getNumMinions(), 2);
        assertEquals(currentPlayer.getHero().getHealth(), 30);
        assertEquals(waitingPlayer.getHero().getHealth(), 30);
        assertEquals(currentPlayer.getMinions().get(0).getHealth(), health0);
        assertEquals(currentPlayer.getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(waitingPlayer.getMinions().get(0).getHealth(), health0 - 1);
        assertEquals(waitingPlayer.getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(currentPlayer.getMinions().get(0).getTotalAttack(), attack0);
        assertEquals(currentPlayer.getMinions().get(1).getTotalAttack(), attack0);
        assertEquals(waitingPlayer.getMinions().get(0).getTotalAttack(), attack0);
        assertEquals(waitingPlayer.getMinions().get(1).getTotalAttack(), attack0);
        assertFalse(currentPlayer.getMinions().get(0).getCharge());
        assertFalse(currentPlayer.getMinions().get(1).getCharge());
        assertFalse(currentPlayer.getMinions().get(0).getFrozen());
        assertTrue(waitingPlayer.getMinions().get(0).getFrozen());
    }
}
