package de.hs_mannheim.sit.ss14.dummy;

public class DHtest {

	public static void main(String[] args) throws Exception {
		ServerDHKeyAgreement2 bob=new ServerDHKeyAgreement2();
		
		ClientDHKeyAgreement2 alice=new ClientDHKeyAgreement2();
		
		
		byte[] alicePubKeyEnc = alice.clientPubKeyEnc();
		byte[] bobPubKeyEnc = bob.serverPubKeyEnc(alicePubKeyEnc);
		
		System.out.println("AUSGABE:BOB:   "+bob.serverSharedSecret());
		System.out.println("AUSGABE:Alice: "+alice.clientSharedSecret(bobPubKeyEnc));
		

	}

}
