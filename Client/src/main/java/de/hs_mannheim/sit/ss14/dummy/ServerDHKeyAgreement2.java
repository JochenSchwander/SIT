package de.hs_mannheim.sit.ss14.dummy;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class ServerDHKeyAgreement2 {

	private KeyAgreement serverKeyAgree;
	private PublicKey clientPubKey;

	public String serverPubKeyEnc(String clientPubKeyEncString) throws Exception {
		byte[]clientPubKeyEnc  = hexStringToByteArray(clientPubKeyEncString);
		/*
		 * Let's turn over to Server. Server has received Client's public key in
		 * encoded format. He instantiates a DH public key from the encoded key
		 * material.
		 */
		KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(clientPubKeyEnc);
		clientPubKey = serverKeyFac.generatePublic(x509KeySpec);

		/*
		 * Server gets the DH parameters associated with Client's public key. He
		 * must use the same parameters when he generates his own key pair.
		 */
		DHParameterSpec dhParamSpec = ((DHPublicKey) clientPubKey).getParams();

		// Server creates his own DH key pair
		KeyPairGenerator serverKpairGen = KeyPairGenerator.getInstance("DH");
		serverKpairGen.initialize(dhParamSpec);
		KeyPair serverKpair = serverKpairGen.generateKeyPair();

		// Server creates and initializes his DH KeyAgreement object
		serverKeyAgree = KeyAgreement.getInstance("DH");
		serverKeyAgree.init(serverKpair.getPrivate());

		// Server encodes his public key, and sends it over to Client.
		byte[] serverPubKeyEnc = serverKpair.getPublic().getEncoded();
		return toHexString(serverPubKeyEnc);

	}

	public String serverSharedSecret() throws Exception {
		/*
		 * Server uses Client's public key for the first (and only) phase of his
		 * version of the DH protocol.
		 */
		serverKeyAgree.doPhase(clientPubKey, true);

		/*
		 * generate the (same) shared secret.
		 */

		byte[] serverSharedSecret = serverKeyAgree.generateSecret();
		return toHexString(serverSharedSecret);

	}

	/*
	 * Converts a byte to hex digit and writes to the supplied buffer
	 */
	private void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	/*
	 * Converts a byte array to hex string
	 */
	private String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();

		int len = block.length;

		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
		}
		return buf.toString();
	}
	
	private byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
}