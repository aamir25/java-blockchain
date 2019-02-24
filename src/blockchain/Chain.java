package blockchain;

import java.util.*;

public class Chain {
	private int difficulty;
	private List<Block> chain;
	
	public Chain(int difficulty) {
		this.difficulty = difficulty;
		chain = new ArrayList<>();
		
		// Genesis Block
		Block genesis = new Block(0, null, "Hello World");
		genesis.mineBlock(difficulty);
		chain.add(genesis);
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
	public Block getLatestBlock() {
		return chain.get(chain.size() - 1);
	}
	
	public Block newBlock(String data) {
		Block latestBlock = getLatestBlock();
		
		return new Block(latestBlock.getIndex() + 1, latestBlock.getHash(), data);
	}
	
	public void addBlock(Block newBlock) {
		if(newBlock != null) {
			newBlock.mineBlock(difficulty);
			chain.add(newBlock);
		}
	}
	
	public boolean isFirstBlockValid() {
		Block genesisBlock = chain.get(0);
		
		if(genesisBlock.getIndex() != 0) {
			return false;
		}
		
		if(genesisBlock.getPreviousHash() != null) {
			return false;
		}
		
		if(!genesisBlock.getHash().equals(Block.calculateHash(genesisBlock))) {
			return false;
		}
		
		return true;
	}
	
	public boolean isNewBlockValid(Block newBlock, Block previousBlock) {
		if(newBlock != null || previousBlock != null) {
			if(newBlock.getIndex() != previousBlock.getIndex() + 1) {
				return false;
			}
			
			if(newBlock.getPreviousHash() == null || !newBlock.getPreviousHash().equals(previousBlock.getHash())) {
				return false;
			}
			
			if(newBlock.getHash() == null || newBlock.getHash().equals(Block.calculateHash(newBlock))) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	public boolean isChainValid() {
		if(!isFirstBlockValid()) {
			return false;
		}
		
		for(int i=1; i<chain.size(); i++) {
			Block currentBlock = chain.get(i);
			Block previousBlock = chain.get(i-1);
			
			if(!isNewBlockValid(currentBlock, previousBlock)) {
				return false;
			}
		}
		
		return true;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(Block block: chain) {
			builder.append(block);
		}
		
		return builder.toString();
	}
}
