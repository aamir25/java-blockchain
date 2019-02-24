package blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Block {
	private int index;
	private long timestamp;
	private String hash;
	private String previousHash;
	private String data;
	private int nounce;
	
	public Block(int index, String previousHash, String data) {
		this.index = index;
		this.timestamp = System.currentTimeMillis();
		this.previousHash = previousHash;
		this.data = data;
		nounce = 0;
		hash = Block.calculateHash(this);
	}

	public int getIndex() {
		return index;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getHash() {
		return hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public String getData() {
		return data;
	}
	
	public String getString() {
		return index + timestamp + previousHash + data + nounce;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Block #" + index + 
				" [previousHash: " + previousHash + 
				", hash: " + hash + 
				", timestamp: " + timestamp +
				", data: " + data + "]");
		
		return builder.toString();
	}

	public static String calculateHash(Block block) {
		MessageDigest digest;
		if(block != null) {
			try {
				digest = MessageDigest.getInstance("SHA-256");
			} 
			catch (NoSuchAlgorithmException e) {
				return null;
			}
			
			String str = block.getString();
			final byte encBytes[] = digest.digest(str.getBytes());
			final StringBuilder builder = new StringBuilder();
			
			for(final byte b: encBytes) {
				String hex = Integer.toHexString(0xff & b);
				
				if(hex.length() == 1) {
					builder.append('0');
				}
				
				builder.append(hex);
			}
			
			return builder.toString();
		}
		
		return null;
	}
	
	public void mineBlock(int difficulty) {
		nounce = 0;
		
		do {
			hash = Block.calculateHash(this);
			nounce++;
		} while(!getHash().substring(0, difficulty).equals(Utils.getZeroes(difficulty)));
	}
}
