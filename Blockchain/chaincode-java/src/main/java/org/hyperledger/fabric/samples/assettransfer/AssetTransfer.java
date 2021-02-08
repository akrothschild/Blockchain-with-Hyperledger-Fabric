/*
 * SPDX-License-Identifier: Apache-2.0
 */

package org.hyperledger.fabric.samples.assettransfer;

import com.owlike.genson.Genson;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contact;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.License;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import java.util.ArrayList;
import java.util.List;

@Contract(
        name = "basic",
        info = @Info(
                title = "File Transfer",
                description = "The hyperlegendary file transfer",
                version = "0.0.1-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0 License",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"),
                contact = @Contact(
                        email = "aydar.sitdikov@bitingbit.com",
                        name = "Aydar Sitdikov",
                        url = "https://bitingbit.com")))
@Default
public final class AssetTransfer implements ContractInterface {

    private final Genson genson = new Genson();

    /**
     * Creates some initial assets on the ledger.
     *
     * @param ctx the transaction context
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void InitLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
    }

    /**
     * Creates a new file on the ledger.
     *
     * @param ctx       the transaction context
     * @param fileID    the ID of the new file
     * @param fileName  the fileName of the new file
     * @param signerPublicKey1 the public key of the first signer
     * @param signerSignature1 the signature of the first signer
     * @param signerPublicKey2 the public key of the second signer
     * @param signerSignature2 the signature of the second signer
     * @return the created file
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Asset CreateFile(final Context ctx, final String fileID, final String fileName,
                            final String signerPublicKey1, final String signerSignature1, final String signerPublicKey2,
                            final String signerSignature2) {
        ChaincodeStub stub = ctx.getStub();

        if (FileExists(ctx, fileID)) {
            String errorMessage = String.format("File %s already exists", fileID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_ALREADY_EXISTS.toString());
        }

        Asset myFile = new Asset(fileID, fileName, signerPublicKey1, signerSignature1, signerPublicKey2, signerSignature2);
        String fileJSON = genson.serialize(myFile);
        stub.putStringState(fileID, fileJSON);

        return myFile;
    }

    /**
     * Retrieves an asset with the specified ID from the ledger.
     *
     * @param ctx    the transaction context
     * @param fileID the ID of the File
     * @return the asset found on the ledger if there was one
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public Asset ReadFile(final Context ctx, final String fileID) {
        ChaincodeStub stub = ctx.getStub();
        String fileJSON = stub.getStringState(fileID);

        if (fileJSON == null || fileJSON.isEmpty()) {
            String errorMessage = String.format("File %s does not exist", fileID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Asset myFile = genson.deserialize(fileJSON, Asset.class);
        return myFile;
    }

    /**
     * Updates the properties of an asset on the ledger.
     *
     * @param ctx             the transaction context
     * @param fileID          the ID of the file being updated
     * @param fileName        the fileName of the file being updated
     * @param owner           the owner of the file being updated
     * @param docSigner       the signer of the file being updated
     * @param signerPublicKey the signer public key of the file being updated
     * @param signerSignature the signer signature of the file being updated
     * @return the transferred asset
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Asset UpdateFile(final Context ctx, final String fileID, final String fileName,
                            final String owner, final String docSigner, final String signerPublicKey,
                            final String signerSignature) {
        ChaincodeStub stub = ctx.getStub();

        if (!FileExists(ctx, fileID)) {
            String errorMessage = String.format("File %s does not exist", fileName);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Asset newFile = new Asset(fileID, fileName, owner, docSigner, signerPublicKey, signerSignature);
        String newFileJSON = genson.serialize(newFile);
        stub.putStringState(fileID, newFileJSON);

        return newFile;
    }

    /**
     * Deletes file on the ledger.
     *
     * @param ctx    the transaction context
     * @param fileID the ID of the file being deleted
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public void DeleteFile(final Context ctx, final String fileID) {
        ChaincodeStub stub = ctx.getStub();

        if (!FileExists(ctx, fileID)) {
            String errorMessage = String.format("File %s does not exist", fileID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        stub.delState(fileID);
    }

    /**
     * Checks the existence of the asset on the ledger
     *
     * @param ctx    the transaction context
     * @param fileID the ID of the asset
     * @return boolean indicating the existence of the asset
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public boolean FileExists(final Context ctx, final String fileID) {
        ChaincodeStub stub = ctx.getStub();
        String fileJSON = stub.getStringState(fileID);

        return (fileJSON != null && !fileJSON.isEmpty());
    }

    /**
     * Changes the owner of a asset on the ledger.
     *
     * @param ctx      the transaction context
     * @param fileID   the ID of the file being transferred
     * @param newOwner the new owner
     * @return the updated asset
     */
    @Transaction(intent = Transaction.TYPE.SUBMIT)
    public Asset TransferFile(final Context ctx, final String fileID, final String newOwner) {
        ChaincodeStub stub = ctx.getStub();
        String fileJSON = stub.getStringState(fileID);

        if (fileJSON == null || fileJSON.isEmpty()) {
            String errorMessage = String.format("File %s does not exist", fileID);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Asset myFile = genson.deserialize(fileJSON, Asset.class);

        Asset newFile = new Asset(myFile.getFileID(), myFile.getFileName(), myFile.getSignerPublicKey1(),
                myFile.getSignerSignature1(), myFile.getSignerPublicKey2(), myFile.getSignerSignature2());
        String newFileJSON = genson.serialize(newFile);
        stub.putStringState(fileID, newFileJSON);

        return newFile;
    }

    /**
     * Retrieves all files from the ledger.
     *
     * @param ctx the transaction context
     * @return array of files found on the ledger
     */
    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String GetAllFiles(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<Asset> queryResults = new ArrayList<Asset>();

        // To retrieve all files from the ledger use getStateByRange with empty startKey & endKey.
        // Giving empty startKey & endKey is interpreted as all the keys from beginning to end.
        // As another example, if you use startKey = 'asset0', endKey = 'asset9' ,
        // then getStateByRange will retrieve asset with keys between asset0 (inclusive) and asset9 (exclusive) in lexical order.
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("", "");

        for (KeyValue result : results) {
            Asset myFile = genson.deserialize(result.getStringValue(), Asset.class);
            queryResults.add(myFile);
            System.out.println(myFile.toString());
        }

        final String response = genson.serialize(queryResults);

        return response;
    }

    @Transaction(intent = Transaction.TYPE.EVALUATE)
    public String isEqual(final Context ctx, final String fileID1, final String fileID2) {
        ChaincodeStub stub = ctx.getStub();
        String fileJSON1 = stub.getStringState(fileID1);
        String response;

        if (fileJSON1 == null || fileJSON1.isEmpty()) {
            String errorMessage = String.format("File %s does not exist", fileID1);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }
        String fileJSON2 = stub.getStringState(fileID2);
        if (fileJSON2 == null || fileJSON2.isEmpty()) {
            String errorMessage = String.format("File %s does not exist", fileID2);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, AssetTransferErrors.ASSET_NOT_FOUND.toString());
        }

        Asset file1 = genson.deserialize(fileJSON1, Asset.class);
        Asset file2 = genson.deserialize(fileJSON2, Asset.class);

        String comparisonMessage;
        if (file1.getFileID() == file2.getFileID()) {
            comparisonMessage = "Files are equal";
        } else {
            comparisonMessage = "Files are not equal";
        }
        System.out.println(comparisonMessage);
        response = genson.serialize(comparisonMessage);

        return response;
    }

    private enum AssetTransferErrors {
        ASSET_NOT_FOUND,
        ASSET_ALREADY_EXISTS
    }
}
