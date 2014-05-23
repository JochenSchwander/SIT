package de.hs_mannheim.sit.ss14.dummy;

public class DHtest {

	public static void main(String[] args) throws Exception {
		
		ServerDHKeyAgreement2 bob=new ServerDHKeyAgreement2();
		
		ClientDHKeyAgreement2 alice=new ClientDHKeyAgreement2();
		
		
		String alicePubKeyEnc = alice.clientPubKeyEnc();
		String bobPubKeyEnc = bob.serverPubKeyEnc(alicePubKeyEnc);
		
		System.out.println("AUSGABE:BOB:   "+bob.serverSharedSecret());
		System.out.println("AUSGABE:Alice: "+alice.clientSharedSecret(bobPubKeyEnc));
		

}
	
	private static String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();

		int len = block.length;

		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
		}
		return buf.toString();
	}
	/*
	 * Converts a byte to hex digit and writes to the supplied buffer
	 */
	private static void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}

}
