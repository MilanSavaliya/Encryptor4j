package org.encryptor4j.test;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.Security;

import org.encryptor4j.DHPeer;
import org.encryptor4j.ECDHPeer;
import org.encryptor4j.KeyAgreementPeer;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * 
 * @author Martin
 *
 */
public class KeyAgreementTest {
	
	static {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}
	
	@Test public void testDH() throws GeneralSecurityException {
		
		// Create p & g
		int bitLength = 512;
	    SecureRandom random = new SecureRandom();
	    BigInteger p = BigInteger.probablePrime(bitLength, random);
	    BigInteger g = BigInteger.probablePrime(bitLength, random);
		
		// Create two peers
		KeyAgreementPeer peerA = new DHPeer(p, g);
		KeyAgreementPeer peerB = new DHPeer(p, g);
		
		// Exchange public keys and compute shared secret
		byte[] sharedSecretA = peerA.computeSharedSecret(peerB.getPublicKey());
		byte[] sharedSecretB = peerB.computeSharedSecret(peerA.getPublicKey());
		
		assertArrayEquals(sharedSecretA, sharedSecretB);
	}
	
	@Test public void testECDH() throws GeneralSecurityException {
		
		String algorithm = "brainpoolp512r1";
		
		// Create two peers
		KeyAgreementPeer peerA = new ECDHPeer(algorithm);
		KeyAgreementPeer peerB = new ECDHPeer(algorithm);
		
		// Exchange public keys and compute shared secret
		byte[] sharedSecretA = peerA.computeSharedSecret(peerB.getPublicKey());
		byte[] sharedSecretB = peerB.computeSharedSecret(peerA.getPublicKey());
		
		assertArrayEquals(sharedSecretA, sharedSecretB);
	}
	
	// MQV and ECMQV are not safe!
	
	/*@Test public void testECMQV() throws GeneralSecurityException {
		
		String algorithm = "brainpoolp512r1";
		
		// Create two peers
		KeyAgreementPeer peerA = new ECMQVPeer(algorithm);
		KeyAgreementPeer peerB = new ECMQVPeer(algorithm);
		
		// Exchange public keys and compute shared secret
		byte[] sharedSecretA = peerA.computeSharedSecret(peerB.getPublicKey());
		byte[] sharedSecretB = peerB.computeSharedSecret(peerA.getPublicKey());
		
		assertArrayEquals(sharedSecretA, sharedSecretB);
	}*/
}