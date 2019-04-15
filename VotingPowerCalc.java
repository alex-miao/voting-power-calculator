import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class VotingPowerCalc {
    
    public int[] votingWeights;
    public int numVoters;
    public HashMap<String, Integer> table;
    
    /**
     * Constructor to load in voting weights
     *
     * @param _votingWeights - non-empty array of positive integers representing the weight of each voter
     */
    public VotingPowerCalc(int[] _votingWeights) {
        
        if (_votingWeights == null || _votingWeights.length == 0) {
            throw new IllegalArgumentException();
        }
        
        numVoters = _votingWeights.length;
        
        votingWeights = new int[numVoters];
        
        for (int i = 0; i < _votingWeights.length; i++) {
            if (_votingWeights[i] <= 0) {
                throw new IllegalArgumentException();
            }
            votingWeights[i] = _votingWeights[i];
        }
        
        table = new HashMap<String, Integer>();
    }
    
    /**
     * Calculate voting power of the voter at the input index
     * 
     * @param voter - index of the voter in array
     * @return The voting power of the specified voter
     */
    public double calculateVotingPower(int voter) {
        
        if (voter < 0 || voter >= numVoters) {
            throw new IllegalArgumentException();
        }
        
        if (numVoters == 1) {
            return 1;
        }
        
        int temp = votingWeights[numVoters - 1];
        votingWeights[numVoters - 1] = votingWeights[voter];
        votingWeights[voter] = temp;
        
        int voteThreshold = 0;
        
        // Calculate the number of votes to surpass to obtain a majority
        for (int i = 0; i < numVoters; i++) {
            voteThreshold += votingWeights[i];
        }
        voteThreshold /= 2;
        
        int votingPower = 0;
        
        for (int i = voteThreshold; i > voteThreshold - votingWeights[numVoters - 1]; i--) {
            votingPower += helper(votingWeights.length - 2, i);
        }
        
        votingWeights[voter] = votingWeights[numVoters - 1];
        votingWeights[numVoters - 1] = temp;
        table.clear();
        
        return votingPower / Math.pow(2, numVoters - 1);
    }
    
    /**
     * Helper function to calculate how many ways voters 1 through index can vote such that the sum of their weights is votes
     * 
     * @param index - the greatest index a voter is allowed to have to be considered
     * @param votes - the target for weight sums
     * @return Number of ways voters can vote to get the specified vote weight sum
     */
    private int helper(int index, int votes) {
        if (votes < 0) {
            return 0;
        }
        
        if (votes == 0) {
            return 1;
        }
        
        if (index == 0) {
            if (votes == votingWeights[0]) {
                return 1;
            } else {
                return 0;
            }
        }
        
        int[] tuple = new int[2];
        
        int val1;
        
        tuple[0] = index - 1;
        tuple[1] = votes;
        
        if (table.containsKey(hashTuple(tuple)) && tuple[0] != 0 && tuple[1] != 0) {
            val1 = table.get(hashTuple(tuple));
        } else {
            val1 = helper(tuple[0], tuple[1]);
        }
        
        int val2;
        
        tuple[0] = index - 1;
        tuple[1] = votes - votingWeights[index];
        
        if (table.containsKey(hashTuple(tuple)) && tuple[0] != 0 && tuple[1] != 0) {
            val2 = table.get(hashTuple(tuple));
        } else {
            val2 = helper(tuple[0], tuple[1]);
        }
        
        int value = val1 + val2;
        
        tuple[0] = index;
        tuple[1] = votes;
        
        table.put(hashTuple(tuple), value);
        return value;
    }
    
    /**
     * Custom hash for 2 element array (tuple)
     * 
     * @param tuple - the array to hash
     * @return Length 32 hex string hash
     */
    public String hashTuple(int[] tuple) {
        String input = tuple[0] + ", " + tuple[1];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(input.getBytes()); 
            
            BigInteger no = new BigInteger(1, messageDigest); 
 
            String hashtext = no.toString(16); 
  
            while (hashtext.length() < 32) { 
                hashtext = "0" + hashtext; 
            } 
            return hashtext;
        } catch (NoSuchAlgorithmException e) { 
            throw new RuntimeException(e); 
        }  
    }
}
