/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import com.owlike.genson.annotation.JsonProperty;

import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public final class Asset {

    @Property()
    private final String fileID;

    @Property()
    private final String fileName;

    @Property()
    private final String signerPublicKey1;

    @Property()
    private final String signerSignature1;

    @Property()
    private final String signerPublicKey2;

    @Property()
    private final String signerSignature2;

    public Asset(@JsonProperty("fileID") final String fileID, @JsonProperty("fileName") final String fileName,
                 @JsonProperty("signerPublicKey1") final String signerPublicKey1,
                 @JsonProperty("signerSignature1") final String signerSignature1, @JsonProperty("signerPublicKey2") final String signerPublicKey2,
                 @JsonProperty("signerSignature2") final String signerSignature2) {
        this.fileID = fileID;
        this.fileName = fileName;
        this.signerPublicKey1 = signerPublicKey1;
        this.signerSignature1 = signerSignature1;
        this.signerPublicKey2 = signerPublicKey2;
        this.signerSignature2 = signerSignature2;
    }

    public String getFileID() {
        return fileID;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSignerPublicKey1() {
        return signerPublicKey1;
    }

    public String getSignerSignature1() {
        return signerSignature1;
    }

    public String getSignerPublicKey2() {
        return signerPublicKey2;
    }

    public String getSignerSignature2() {
        return signerSignature2;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        Asset other = (Asset) obj;

        return Objects.deepEquals(
                new String[]{getFileID(), getFileName(), getSignerSignature1()},
                new String[]{other.getFileID(), other.getFileName(), other.getSignerPublicKey1(),
                        other.getSignerSignature1(), other.getSignerPublicKey2(), other.getSignerSignature2()});
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFileID(), getFileName(), getSignerPublicKey1(), getSignerSignature1(), getSignerPublicKey2(), getSignerSignature2());
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode())
                + " [fileID=" + fileID + ", fileName=" + fileName + ", signerPublicKey1=" + signerPublicKey1
                + ", signerSignature1=" + signerSignature1 + ", signerPublicKey2=" + signerPublicKey2
                + ", signerSignature2=" + signerSignature2 + "]";
    }
}
