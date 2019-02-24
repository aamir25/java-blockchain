import blockchain.Chain;

public class Main {

	public static void main(String[] args) {
		Chain chain = new Chain(4);
		
		chain.addBlock(chain.newBlock("JavaScript"));
		chain.addBlock(chain.newBlock("Python"));
		chain.addBlock(chain.newBlock("JAVA"));
		chain.addBlock(chain.newBlock("C#"));
		
		System.out.println(chain);
	}

}
