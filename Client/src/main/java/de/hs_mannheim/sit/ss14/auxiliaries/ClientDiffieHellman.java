package de.hs_mannheim.sit.ss14.auxiliaries;

import java.security.AlgorithmParameterGenerator;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

/**
 * Diffie Hellman Algorithm implementation by Oracle. Split up in Server and
 * Client Classes by DS. Function names and comments by Jochen Schwander.
 *
 * @src http://docs.oracle.com/javase/7/docs/technotes/guides/security/crypto/CryptoSpec.html Appendix D
 *
 *      Copyright (c) 1997, 2001, Oracle and/or its affiliates. All rights
 *      reserved.
 *
 *      Redistribution and use in source and binary forms, with or without
 *      modification, are permitted provided that the following conditions are
 *      met:
 *
 *      - Redistributions of source code must retain the above copyright notice,
 *      this list of conditions and the following disclaimer.
 *
 *      - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *
 *      - Neither the name of Oracle nor the names of its contributors may be
 *      used to endorse or promote products derived from this software without
 *      specific prior written permission.
 *
 *      THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 *      IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *      TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 *      PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *      OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *      EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *      PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *      PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *      LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *      NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *      SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class ClientDiffieHellman {

	private KeyAgreement clientKeyAgree;

	/**
	 * Calculates the public key with the given other sides public key.
	 *
	 * @param clientPubKeyEncString
	 *            client public key
	 * @return server public key
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidParameterSpecException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws InvalidKeyException 
	 * @throws Exception
	 */
	public String calculatePublicKey() throws NoSuchAlgorithmException, InvalidParameterSpecException, InvalidAlgorithmParameterException, InvalidKeyException {
		DHParameterSpec dhSkipParamSpec;

		// Some central authority creates new DH parameters
		AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator
				.getInstance("DH");
		// bitl�nge erh�ht auf 1024
		paramGen.init(1024);
		dhSkipParamSpec = (DHParameterSpec) paramGen.generateParameters()
				.getParameterSpec(DHParameterSpec.class);

		/*
		 * Client creates her own DH key pair, using the DH parameters from
		 * above
		 */
		KeyPairGenerator clientKpairGen = KeyPairGenerator.getInstance("DH");
		clientKpairGen.initialize(dhSkipParamSpec);
		KeyPair clientKpair = clientKpairGen.generateKeyPair();

		// Client creates and initializes her DH KeyAgreement object
		clientKeyAgree = KeyAgreement.getInstance("DH");
		clientKeyAgree.init(clientKpair.getPrivate());

		// Client encodes her public key, and sends it over to Server.
		byte[] clientPubKeyEnc = clientKpair.getPublic().getEncoded();

		return Base64.encodeBase64String(clientPubKeyEnc);
	}

	/**
	 * Calculates the shared secret between client and server.
	 *
	 * @return shared secret
	 * @throws NoSuchAlgorithmException 
	 * @throws IllegalStateException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 * @throws Exception
	 */
	public byte[] calculateSharedSecret(String serverPubKeyEncString) throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, InvalidKeySpecException {
		byte[] serverPubKeyEnc = Base64.decodeBase64(serverPubKeyEncString);
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
		 * At this stage, both Client and Server have completed the DH key
		 * agreement protocol. Both generate the (same) shared secret.
		 */
		byte[] clientSharedSecret = clientKeyAgree.generateSecret();
		return clientSharedSecret;
	}

}