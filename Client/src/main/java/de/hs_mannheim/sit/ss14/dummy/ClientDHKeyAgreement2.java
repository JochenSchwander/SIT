package de.hs_mannheim.sit.ss14.dummy;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

public class ClientDHKeyAgreement2 {

	private KeyAgreement clientKeyAgree;

	public String clientPubKeyEnc() throws Exception {
		DHParameterSpec dhSkipParamSpec;

		// Some central authority creates new DH parameters
		AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator
				.getInstance("DH");
		paramGen.init(1024);
		dhSkipParamSpec = (DHParameterSpec) paramGen.generateParameters()
				.getParameterSpec(DHParameterSpec.class);

		/*
		 * Client creates her own DH key pair, using the DH parameters from above
		 */
		KeyPairGenerator clientKpairGen = KeyPairGenerator.getInstance("DH");
		clientKpairGen.initialize(dhSkipParamSpec);
		KeyPair clientKpair = clientKpairGen.generateKeyPair();

		// Client creates and initializes her DH KeyAgreement object
		clientKeyAgree = KeyAgreement.getInstance("DH");
		clientKeyAgree.init(clientKpair.getPrivate());

		// Client encodes her public key, and sends it over to Server.
		byte[] clientPubKeyEnc = clientKpair.getPublic().getEncoded();

		return toHexString(clientPubKeyEnc);
	}

	public String clientSharedSecret(String serverPubKeyEncString) throws Exception {
		byte[]serverPubKeyEnc  = hexStringToByteArray(serverPubKeyEncString);
		/*
		 * Client uses Server's public key for the first (and only) phase of her
		 * version of the DH protocol. Before she can do so, she has to
		 * instantiate a DH public key from Server's encoded key material.
		 */
		KeyFactory clientKeyFac = KeyFactory.getInstance("DH");
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(serverPubKeyEnc);
		PublicKey serverPubKey = clientKeyFac.generatePublic(x509KeySpec);
		clientKeyAgree.doPhase(serverPubKey, true);

		/*
		 * At this stage, both Client and Server have completed the DH key agreement
		 * protocol. Both generate the (same) shared secret.
		 */
		byte[] clientSharedSecret = clientKeyAgree.generateSecret();
		return toHexString(clientSharedSecret);

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
	
	private static byte[] hexStringToByteArray(String hexString) {
		return (new BigInteger(hexString, 16)).toByteArray();
	}
	

}