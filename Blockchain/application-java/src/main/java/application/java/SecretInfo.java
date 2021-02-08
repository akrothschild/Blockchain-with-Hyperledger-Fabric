package application.java;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SecretInfo {

    PrivateKey privateKey;
    PublicKey publicKey;
    byte[] signature;

    public SecretInfo(PrivateKey privateKey, PublicKey publicKey, byte[] signature) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.signature = signature;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }
}
