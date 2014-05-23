package de.hs_mannheim.sit.ss14.dummy;

public class DHtest {

	public static void main(String[] args) throws Exception {
		BobDHKeyAgreement2 bob=new BobDHKeyAgreement2();
		
		AliceDHKeyAgreement2 alice=new AliceDHKeyAgreement2();
		
		
		byte[] alicePubKeyEnc = alice.AlicePubKeyEnc();
		byte[] bobPubKeyEnc = bob.bobPubKeyEnc(alicePubKeyEnc);
		
		System.out.println("AUSGABE:BOB:   "+bob.bobSharedSecret());
		System.out.println("AUSGABE:Alice: "+alice.aliceSharedSecret(bobPubKeyEnc));
		

	}

}
