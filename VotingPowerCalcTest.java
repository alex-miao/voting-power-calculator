import static org.junit.Assert.*;

import org.junit.Test;

public class VotingPowerCalcTest {

    @Test
    public void test() {
        int[] a1 = {1};
        VotingPowerCalc v1 = new VotingPowerCalc(a1);
        assertEquals(1, v1.calculateVotingPower(0), 0.0001);
        
        int[] a2 = {101, 100};
        VotingPowerCalc v2 = new VotingPowerCalc(a2);
        assertEquals(1, v2.calculateVotingPower(0), 0.0001);
        assertEquals(0, v2.calculateVotingPower(1), 0.0001);
        
        int[] a3 = {100, 100, 1};
        VotingPowerCalc v3 = new VotingPowerCalc(a3);
        assertEquals(0.5, v3.calculateVotingPower(0), 0.0001);
        assertEquals(0.5, v3.calculateVotingPower(1), 0.0001);
        assertEquals(0.5, v3.calculateVotingPower(2), 0.0001);
        
        int[] a4 = {100, 100, 100, 25};
        VotingPowerCalc v4 = new VotingPowerCalc(a4);
        assertEquals(0.5, v4.calculateVotingPower(0), 0.0001);
        assertEquals(0.5, v4.calculateVotingPower(1), 0.0001);
        assertEquals(0.5, v4.calculateVotingPower(2), 0.0001);
        assertEquals(0, v4.calculateVotingPower(3), 0.0001);
    }

}
