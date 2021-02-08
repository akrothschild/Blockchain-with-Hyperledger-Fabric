/*
 * Copyright IBM Corp. All Rights Reserved.
 *
 * SPDX-License-Identifier: Apache-2.0
 */

// Running TestApp: 
// gradle runApp 

package application.java;

import org.apache.commons.codec.digest.DigestUtils;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Scanner;


public class App {

    static String myYes = "y";
// fixme: Konstanten, enum, bedeutung aller parameter erklaeen koennen
//	TODO : byte array as decode as hexa, then to string / hexastring

    static {
        System.setProperty("org.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
    }

    // helper function for getting connected to the gateway
    public static Gateway connect() throws Exception {
        // Load a file system based wallet for managing identities.
        Path walletPath = Paths.get("wallet");
        Wallet wallet = Wallets.newFileSystemWallet(walletPath);
        // load a CCP
        Path networkConfigPath = Paths.get("..", "..", "test-network", "organizations", "peerOrganizations", "org1.example.com", "connection-org1.yaml");

        Gateway.Builder builder = Gateway.createBuilder();
        builder.identity(wallet, "appUser").networkConfig(networkConfigPath).discovery(true);
        return builder.connect();
    }

//    static void isExist(String myHash) {
//        try {
//            //DeleteFile deletes previously created file using fileID hashString1
//            byte[] result = contract.submitTransaction(BCCommands.Commands.FILE_EXISTS.value, myFile1.fileID);
//            System.out.println("Is a file in the database equal to " + myFile1.fileName + "? " + new String(result));
//            if (new String(result).equals("true")) {
//                contract.submitTransaction(BCCommands.Commands.DELETE_FILE.value, myFile1.fileID);
//                System.out.println("FILE DELETED");
//            }
//        } catch (Exception e) {
//            System.err.println(e);
//        }
//    }

    public static SecretInfo signDoc (String fileID) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("DSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate a pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the private key from the key pair
        PrivateKey privateKey = pair.getPrivate();

        //Getting the public key from the key pair
        PublicKey publicKey = pair.getPublic();

        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withDSA");

        //Initialize the signature
        sign.initSign(privateKey);
        byte[] bytes = fileID.getBytes();

        //Add data to the signature
        sign.update(bytes);

        //Calculate the signature
        byte[] signature = sign.sign();

        //Initializing the signature
        sign.initVerify(pair.getPublic());

        //Update the data to be verified
        sign.update(bytes);

        //Verify the signature
        boolean bool = sign.verify(signature);

        if (bool) {
            System.out.println("The signature for the " + fileID + " verified");
        } else {
            System.out.println("The signature for the " + fileID + " failed");
        }

        return new SecretInfo(privateKey, publicKey, signature);
    }

    public static void main(String[] args) throws Exception {
        // enrolls the admin and registers the user
        try {
            EnrollAdmin.main(null);
            RegisterUser.main(null);
        } catch (Exception e) {
            System.err.println(e);
        }

//		String decodedString1 = new String(Base64.decodeBase64("VXBkYXRlRmlsZQ==".getBytes()));
//        String decodedString2 = new String(Base64.decodeBase64("NDViMjMzZmFlNjAyMTE2YTg1NTA2MTY5YzEyYjVkMTYyNjI5MWQzNzU5ZTlkMTNlZjExOGI3MjBiMTBiZGRhZQ".getBytes()));
//        String decodedString3 = new String(Base64.decodeBase64("MS5wZGY=".getBytes()));
//        String decodedString4 = new String(Base64.decodeBase64("WzQ4LCAtMTI2LCAzLCA2NiwgNDgsIC0xMjYsIDIsIDUzLCA2LCA3LCA0MiwgLTEyMiwgNzIsIC01MCwgNTYsIDQsIDEsIDQ4LCAtMTI2LCAyLCA0MCwgMiwgLTEyNiwgMSwgMSwgMCwgLTExMywgMTIxLCA1MywgLTM5LCAtNzEsIC04NiwgLTIzLCAtNjUsIC04NSwgLTE5LCAtMTIwLCAxMjIsIC00OSwgNzMsIDgxLCAtNzQsIC0xMywgNDYsIC01OSwgLTk4LCA1OSwgLTgxLCA1NSwgMjQsIC0yNCwgLTIyLCAtNjAsIC0xMDYsIDMxLCA2MiwgLTMsIDU0LCA2LCAtMjUsIDY3LCA4MSwgLTg3LCAtNjAsIDI0LCA1MSwgNTcsIC03MiwgOSwgLTI1LCAtNjIsIC04MiwgMjgsIDgzLCAtMTAxLCAtODksIDcxLCA5MSwgLTEyMywgLTQ4LCAxNywgLTgzLCAtNzIsIC03NiwgMTIxLCAtMTIxLCAxMTcsIDczLCAtMTI0LCAxMDUsIDkyLCAtODQsIDE0LCAtMTEzLCAyMCwgLTc3LCA1NCwgOCwgNDAsIC05NCwgNDcsIC02LCAzOSwgMTcsIDEwLCA2MSwgOTgsIC04NywgLTEwOSwgNjksIDUyLCA5LCAtOTYsIC0yLCAxMDUsIDEwOCwgNzAsIDg4LCAtOCwgNzUsIC0zNSwgMzIsIC0xMjcsIC0xMDAsIDU1LCA5LCAtOTYsIDE2LCA4NywgLTc5LCAtMTA3LCAtODMsIC01MSwgMCwgMzUsIDYxLCAtNzAsIDg0LCAtMTI0LCAtNzQsIDQxLCAzMSwgLTk5LCAxMDAsIC0xMTQsIC04LCAtMTI1LCA2OCwgLTEyMiwgMTE5LCAtMTA1LCAtMTAwLCAtMjAsIDQsIC03NiwgNTIsIC05MCwgLTg0LCA0NiwgMTE3LCAtMjMsIC0xMDQsIDkzLCAtMzAsIDYxLCAtODAsIDQxLCA0NywgLTYzLCAxNywgLTExNiwgLTk3LCAtNiwgLTk5LCAtMTI3LCAtMTI3LCAtMjUsIDUxLCAtMTE1LCAtNzMsIC0xMTAsIC03MywgNDgsIC00MSwgLTcxLCAtMjksIDczLCA4OSwgNDcsIDEwNCwgOSwgLTEwNCwgMTE0LCAyMSwgNTcsIDIxLCAtMjIsIDYxLCAxMDcsIC0xMTcsIDcwLCA4MywgLTU4LCA1MSwgNjksIC0xMTMsIC0xMjgsIDU5LCA1MCwgLTkyLCAtNjIsIC0zMiwgLTE0LCAxMTQsIC0xMTIsIDM3LCAxMTAsIDc4LCA2MywgLTExOCwgNTksIDgsIDU2LCAtOTUsIC02MCwgODAsIC0yOCwgLTMxLCAtMTE2LCAyNiwgNDEsIC05MywgMTI1LCAtMzMsIDk0LCAtOTUsIDY3LCAtMzQsIDc1LCAxMDIsIC0xLCA0LCAtMTEyLCA2MiwgLTQzLCAtNDksIDIyLCAzNSwgLTMxLCA4OCwgLTQ0LCAtMTIxLCAtNTgsIDgsIC0yMywgMTI3LCAzMywgMjgsIC00MCwgMjksIC01NCwgMzUsIC01MywgMTEwLCA1NiwgNywgMTAxLCAtOCwgMzQsIC0yOSwgNjYsIC02NiwgNzIsIDc2LCA1LCAxMTgsIDU3LCA1NywgOTYsIDI4LCAtNDIsIDEwMywgMiwgMjksIDAsIC03MCwgLTEwLCAtMTA2LCAtOTAsIC0xMjMsIDEyMCwgLTksIC0zMywgLTM0LCAtMjUsIC02LCAxMDMsIC01NSwgMTE5LCAtNTcsIC0xMjMsIC0xNywgNTAsIC03OCwgNTEsIC03MCwgLTI3LCAtMTI4LCAtNjQsIC02OCwgLTQzLCAxMDUsIDkzLCAyLCAtMTI2LCAxLCAwLCAyMiwgLTkwLCA5MiwgODgsIDMyLCA3MiwgODAsIDExMiwgNzgsIDExNywgMiwgLTkzLCAtMTA1LCA4NywgNCwgMTMsIDUyLCAtMzgsIDU4LCA1MiwgMTIwLCAtNjMsIDg0LCAtNDQsIC0yOCwgLTkxLCAtNjQsIDQ1LCAzNiwgNDYsIC0zMiwgNzksIC0xMDYsIC0yNiwgMzAsIDc1LCAtNDgsIC0xMTIsIDc0LCAtNjcsIC04NCwgLTExMywgNTUsIC0xOCwgLTc5LCAtMzIsIC05NywgNDksIC0xMjYsIC00NiwgNjAsIC0xMTIsIDY3LCAtNTMsIDEwMCwgNDcsIC0xMjAsIDAsIDY1LCA5NiwgLTE5LCAtNywgLTU0LCA5LCAtNzcsIDMyLCAxMTgsIC04OSwgLTEwMCwgNTAsIC05MCwgMzksIC0xNCwgNzEsIDYyLCAtMTExLCAtMTIxLCAtMTAxLCAtOTQsIC02MCwgLTI1LCA2OCwgLTY3LCAzMiwgLTEyNywgODQsIDc2LCAtNzUsIDkxLCAtMTI4LCA0NCwgNTQsIC0xMTUsIDMxLCAtODgsIDYyLCAtNDQsIC0xMTksIC0yMywgNzgsIDE1LCAtOTYsIDEwNCwgLTExNCwgNTAsIDY2LCAtMTE4LCA5MiwgMTIwLCAtNjAsIDEyMCwgLTU4LCAtMTE1LCA1LCAzOSwgLTczLCAyOCwgLTEwMiwgNTgsIC02OSwgMTEsIDExLCAtMzEsIDQ0LCA2OCwgMTA0LCAtMTA2LCA1NywgLTI1LCAtNDUsIC01MCwgMTE2LCAtMzcsIDE2LCAyNiwgMTAxLCAtODYsIDQzLCAtMTIxLCAtMTAsIDc2LCAxMDQsIDM4LCAtMzcsIDYyLCAtNTcsIDQ3LCA3NSwgODUsIC0xMDMsIC0xMjUsIDc1LCAtNzYsIC0xOSwgLTgwLCA0NywgMTI0LCAtMTEyLCAtMjMsIC05MiwgLTEwNiwgLTQ1LCAtOTEsIDkzLCA4MywgOTEsIC0yMSwgLTQsIDY5LCAtNDQsIC0xMCwgMjUsIC0xMCwgNjMsIDYxLCAtMTksIC02OSwgLTEyMSwgNTcsIDM3LCAtNjIsIC0xNCwgMzYsIC0zMiwgMTE5LCA0OSwgNDEsIDEwOSwgLTg4LCAtMTIxLCAtMjAsIDMwLCA3MSwgNzIsIC04LCAxMjYsIC01LCA5NSwgLTM0LCAtNzMsIDg0LCAtMTI0LCA0OSwgMTA3LCAzNCwgNTAsIC0zNCwgLTI3LCA4MywgLTM1LCAtODEsIDIsIDE3LCA0MywgMTMsIDMxLCAyLCAtMzgsIDQ4LCAtMTA1LCA1MCwgMzYsIC0yLCAzOSwgLTgyLCAtMzgsIC0xMTcsIC05OSwgNzUsIDQxLCAzNCwgLTM5LCAtNzAsIC0xMTcsIC0yOSwgLTk4LCAtMzksIC0zMSwgMywgLTkwLCA2MCwgODIsIC0xMjcsIDExLCAtNTgsIC0xMjAsIC03MywgLTMwLCAtMTksIDY3LCAyMiwgLTMxLCAtMTcsIDIzLCAtMzcsIC0zNCwgMywgLTEyNiwgMSwgNSwgMCwgMiwgLTEyNiwgMSwgMCwgNDMsIC00OCwgLTc5LCAtMTEyLCA2NiwgNjksIC0xMTIsIDExLCA5MywgLTEyMiwgLTUwLCAtOSwgLTE5LCAtMzksIDY1LCAtNjIsIDY2LCAtMTYsIC0xMDQsIC0zMywgNzksIC00MywgLTExOCwgNjksIC0xMDQsIC00MCwgNTgsIDMsIDExLCAxMTcsIDgzLCAtNTgsIC0xNiwgOTEsIC0xMTksIC0xMTcsIDMxLCA4MSwgLTEyOCwgMzMsIDg0LCAtODAsIDE3LCAxLCA4LCAzMywgLTQxLCAtMTE4LCA1MiwgLTk4LCAzMywgNjMsIDEsIC04MiwgLTM1LCAtNDQsIDIzLCA5NywgNzMsIDgwLCAxMTEsIC00MywgLTEwNCwgLTUxLCAtNjAsIC0xMDgsIC03NCwgOTMsIC05NywgLTM4LCAtMTA1LCAxMCwgLTg5LCA3LCAtODEsIC0xMDQsIC0xMjAsIDc5LCAxOCwgLTg4LCAtMTIzLCAtOCwgMTIyLCAxMjQsIC0xMjcsIDExMiwgNjksIC0xMDQsIDE4LCAzNywgLTM2LCA3NSwgLTEwOSwgNywgMTE2LCA2NCwgMTAwLCA4LCA2LCAzNiwgLTc2LCAtMTEsIC05MCwgLTEyMiwgLTc3LCAtMTI1LCAtNjksIC01OSwgLTE5LCAtMTA0LCAtOTQsIC0xMTgsIC0xOSwgMTE1LCAtMTIxLCAyMSwgLTg3LCAtMzgsIDcxLCAtNjcsIC01MCwgLTk0LCAyLCAtMTA3LCAtOCwgLTYzLCAtMTAsIDY0LCAzLCA3NywgODMsIC0yNywgMTEyLCA0OCwgNjksIC05MiwgLTI5LCAxMjQsIDUwLCAtMjUsIC0xMjMsIC0xMjYsIC0xNiwgNTMsIC02NSwgNDMsIDc0LCAtMzMsIDQsIDkzLCA1NSwgMzYsIC04OCwgLTczLCAtNzEsIDcsIDcxLCA2OCwgLTY3LCA3OSwgOTYsIDcwLCAxMjUsIC05LCAtNSwgLTgxLCAtMTA5LCA0NCwgLTEwOCwgLTExNSwgNiwgODAsIDkwLCAzMSwgMTE5LCAtNjAsIDEyNCwgLTc0LCAxLCAtOTgsIDI0LCAtNDUsIDEzLCA1NywgLTYxLCAtMTE2LCAtMTA4LCAzMSwgLTQ1LCAtNDYsIC01OCwgNjksIC0xMjcsIC0xMTYsIC04LCAtNzYsIC0xMTgsIDI4LCA3OCwgLTU5LCA4OSwgLTExLCAtMzMsIDc1LCA3MSwgLTExMSwgMTEzLCA3MywgMTYsIC0xMTUsIC0xMjIsIDg5LCA4MCwgLTc1LCAtOTQsIDQ5LCA4MywgLTY0LCAtMjQsIC0yOSwgLTM3LCA3MywgLTU2LCAxMTMsIDcsIDU1LCAtNDEsIDk4LCAzNywgMjgsIC0zNiwgLTgsIDYyLCAtMTI3LCA3NCwgLTExMCwgMjUsIDgzLCA1NSwgMzcsIC04NywgOTIsIC01OSwgLTExMiwgMzQsIC0xMTcsIDI0LCAtNjQsIC00MSwgNjAsIDQ4LCAtMTIsIC04MiwgNTMsIC02OSwgMTVd".getBytes()));
//        String decodedString5 = new String(Base64.decodeBase64("MDwCHFhzCVlX77+9GO+/ve+/vTHvv70g77+9f++/ve+/vUTvv73vv73vv73vv702dd2D77+9eAIcbyPvv73vv70gHwfvv73vv70Y77+977+9O1IQWe+/vSFd77+9GU7vv70l77+9eO+/vQ==".getBytes()));
//        String decodedString6 = new String(Base64.decodeBase64("WzQ4LCAtMTI2LCAzLCA2NiwgNDgsIC0xMjYsIDIsIDUzLCA2LCA3LCA0MiwgLTEyMiwgNzIsIC01MCwgNTYsIDQsIDEsIDQ4LCAtMTI2LCAyLCA0MCwgMiwgLTEyNiwgMSwgMSwgMCwgLTExMywgMTIxLCA1MywgLTM5LCAtNzEsIC04NiwgLTIzLCAtNjUsIC04NSwgLTE5LCAtMTIwLCAxMjIsIC00OSwgNzMsIDgxLCAtNzQsIC0xMywgNDYsIC01OSwgLTk4LCA1OSwgLTgxLCA1NSwgMjQsIC0yNCwgLTIyLCAtNjAsIC0xMDYsIDMxLCA2MiwgLTMsIDU0LCA2LCAtMjUsIDY3LCA4MSwgLTg3LCAtNjAsIDI0LCA1MSwgNTcsIC03MiwgOSwgLTI1LCAtNjIsIC04MiwgMjgsIDgzLCAtMTAxLCAtODksIDcxLCA5MSwgLTEyMywgLTQ4LCAxNywgLTgzLCAtNzIsIC03NiwgMTIxLCAtMTIxLCAxMTcsIDczLCAtMTI0LCAxMDUsIDkyLCAtODQsIDE0LCAtMTEzLCAyMCwgLTc3LCA1NCwgOCwgNDAsIC05NCwgNDcsIC02LCAzOSwgMTcsIDEwLCA2MSwgOTgsIC04NywgLTEwOSwgNjksIDUyLCA5LCAtOTYsIC0yLCAxMDUsIDEwOCwgNzAsIDg4LCAtOCwgNzUsIC0zNSwgMzIsIC0xMjcsIC0xMDAsIDU1LCA5LCAtOTYsIDE2LCA4NywgLTc5LCAtMTA3LCAtODMsIC01MSwgMCwgMzUsIDYxLCAtNzAsIDg0LCAtMTI0LCAtNzQsIDQxLCAzMSwgLTk5LCAxMDAsIC0xMTQsIC04LCAtMTI1LCA2OCwgLTEyMiwgMTE5LCAtMTA1LCAtMTAwLCAtMjAsIDQsIC03NiwgNTIsIC05MCwgLTg0LCA0NiwgMTE3LCAtMjMsIC0xMDQsIDkzLCAtMzAsIDYxLCAtODAsIDQxLCA0NywgLTYzLCAxNywgLTExNiwgLTk3LCAtNiwgLTk5LCAtMTI3LCAtMTI3LCAtMjUsIDUxLCAtMTE1LCAtNzMsIC0xMTAsIC03MywgNDgsIC00MSwgLTcxLCAtMjksIDczLCA4OSwgNDcsIDEwNCwgOSwgLTEwNCwgMTE0LCAyMSwgNTcsIDIxLCAtMjIsIDYxLCAxMDcsIC0xMTcsIDcwLCA4MywgLTU4LCA1MSwgNjksIC0xMTMsIC0xMjgsIDU5LCA1MCwgLTkyLCAtNjIsIC0zMiwgLTE0LCAxMTQsIC0xMTIsIDM3LCAxMTAsIDc4LCA2MywgLTExOCwgNTksIDgsIDU2LCAtOTUsIC02MCwgODAsIC0yOCwgLTMxLCAtMTE2LCAyNiwgNDEsIC05MywgMTI1LCAtMzMsIDk0LCAtOTUsIDY3LCAtMzQsIDc1LCAxMDIsIC0xLCA0LCAtMTEyLCA2MiwgLTQzLCAtNDksIDIyLCAzNSwgLTMxLCA4OCwgLTQ0LCAtMTIxLCAtNTgsIDgsIC0yMywgMTI3LCAzMywgMjgsIC00MCwgMjksIC01NCwgMzUsIC01MywgMTEwLCA1NiwgNywgMTAxLCAtOCwgMzQsIC0yOSwgNjYsIC02NiwgNzIsIDc2LCA1LCAxMTgsIDU3LCA1NywgOTYsIDI4LCAtNDIsIDEwMywgMiwgMjksIDAsIC03MCwgLTEwLCAtMTA2LCAtOTAsIC0xMjMsIDEyMCwgLTksIC0zMywgLTM0LCAtMjUsIC02LCAxMDMsIC01NSwgMTE5LCAtNTcsIC0xMjMsIC0xNywgNTAsIC03OCwgNTEsIC03MCwgLTI3LCAtMTI4LCAtNjQsIC02OCwgLTQzLCAxMDUsIDkzLCAyLCAtMTI2LCAxLCAwLCAyMiwgLTkwLCA5MiwgODgsIDMyLCA3MiwgODAsIDExMiwgNzgsIDExNywgMiwgLTkzLCAtMTA1LCA4NywgNCwgMTMsIDUyLCAtMzgsIDU4LCA1MiwgMTIwLCAtNjMsIDg0LCAtNDQsIC0yOCwgLTkxLCAtNjQsIDQ1LCAzNiwgNDYsIC0zMiwgNzksIC0xMDYsIC0yNiwgMzAsIDc1LCAtNDgsIC0xMTIsIDc0LCAtNjcsIC04NCwgLTExMywgNTUsIC0xOCwgLTc5LCAtMzIsIC05NywgNDksIC0xMjYsIC00NiwgNjAsIC0xMTIsIDY3LCAtNTMsIDEwMCwgNDcsIC0xMjAsIDAsIDY1LCA5NiwgLTE5LCAtNywgLTU0LCA5LCAtNzcsIDMyLCAxMTgsIC04OSwgLTEwMCwgNTAsIC05MCwgMzksIC0xNCwgNzEsIDYyLCAtMTExLCAtMTIxLCAtMTAxLCAtOTQsIC02MCwgLTI1LCA2OCwgLTY3LCAzMiwgLTEyNywgODQsIDc2LCAtNzUsIDkxLCAtMTI4LCA0NCwgNTQsIC0xMTUsIDMxLCAtODgsIDYyLCAtNDQsIC0xMTksIC0yMywgNzgsIDE1LCAtOTYsIDEwNCwgLTExNCwgNTAsIDY2LCAtMTE4LCA5MiwgMTIwLCAtNjAsIDEyMCwgLTU4LCAtMTE1LCA1LCAzOSwgLTczLCAyOCwgLTEwMiwgNTgsIC02OSwgMTEsIDExLCAtMzEsIDQ0LCA2OCwgMTA0LCAtMTA2LCA1NywgLTI1LCAtNDUsIC01MCwgMTE2LCAtMzcsIDE2LCAyNiwgMTAxLCAtODYsIDQzLCAtMTIxLCAtMTAsIDc2LCAxMDQsIDM4LCAtMzcsIDYyLCAtNTcsIDQ3LCA3NSwgODUsIC0xMDMsIC0xMjUsIDc1LCAtNzYsIC0xOSwgLTgwLCA0NywgMTI0LCAtMTEyLCAtMjMsIC05MiwgLTEwNiwgLTQ1LCAtOTEsIDkzLCA4MywgOTEsIC0yMSwgLTQsIDY5LCAtNDQsIC0xMCwgMjUsIC0xMCwgNjMsIDYxLCAtMTksIC02OSwgLTEyMSwgNTcsIDM3LCAtNjIsIC0xNCwgMzYsIC0zMiwgMTE5LCA0OSwgNDEsIDEwOSwgLTg4LCAtMTIxLCAtMjAsIDMwLCA3MSwgNzIsIC04LCAxMjYsIC01LCA5NSwgLTM0LCAtNzMsIDg0LCAtMTI0LCA0OSwgMTA3LCAzNCwgNTAsIC0zNCwgLTI3LCA4MywgLTM1LCAtODEsIDIsIDE3LCA0MywgMTMsIDMxLCAyLCAtMzgsIDQ4LCAtMTA1LCA1MCwgMzYsIC0yLCAzOSwgLTgyLCAtMzgsIC0xMTcsIC05OSwgNzUsIDQxLCAzNCwgLTM5LCAtNzAsIC0xMTcsIC0yOSwgLTk4LCAtMzksIC0zMSwgMywgLTkwLCA2MCwgODIsIC0xMjcsIDExLCAtNTgsIC0xMjAsIC03MywgLTMwLCAtMTksIDY3LCAyMiwgLTMxLCAtMTcsIDIzLCAtMzcsIC0zNCwgMywgLTEyNiwgMSwgNSwgMCwgMiwgLTEyNiwgMSwgMCwgNTUsIDE3LCA4MywgLTY5LCAxNiwgMjUsIDc5LCAxMjcsIC04NSwgLTE3LCAtMzcsIC04OSwgLTg1LCAzNiwgMTA3LCAtMTA5LCAtMTA4LCAtOTAsIDM2LCAxMywgNTQsIC00MywgLTExMiwgLTE3LCA0MywgMywgLTExLCAxMjYsIC0yNCwgLTE1LCAyNiwgMzEsIC03NCwgMTIxLCAzNywgLTU4LCAtNDYsIDEzLCAtMjcsIC04LCAtMTEzLCA0MSwgLTU4LCAtNzAsIDMyLCAtNTIsIDkzLCAtOTksIDU3LCAtMiwgMTEsIC0yMCwgNTksIC0xNywgLTQ0LCAtOTcsIDM2LCAxNSwgLTEyOCwgLTE2LCAtMTA4LCAtMTE1LCA1NCwgOTUsIC0yMCwgLTEwNCwgNzIsIC0xMTIsIDk1LCAxMDgsIDYzLCAtNDUsIC05NywgLTEwOSwgLTM5LCAtODAsIC0xOCwgMCwgLTU1LCA1MCwgLTEwMSwgLTExNywgLTgzLCAzMywgOTAsIDEsIDM0LCA5MCwgOTksIDcyLCAxMDUsIC02LCA1NSwgMTA4LCAtMTE3LCA0OSwgLTYxLCAxMDEsIDEyNCwgLTEwMSwgMzgsIC02NCwgLTIsIC0xMDIsIC0xMiwgOTUsIDk4LCAtNTksIDI0LCAtODMsIDQyLCAxNSwgMTA2LCAxMTYsIC05MSwgMTE3LCAtOSwgLTUyLCAtMTIyLCAtMTQsIDU5LCA5NCwgOSwgLTQ4LCAtODgsIC0xMiwgNCwgNDAsIDc5LCAxMDcsIC0xMDgsIC0xMjYsIC04MCwgLTEyNywgLTk1LCA3LCA5LCAzLCA2MCwgMzAsIDE3LCAtMzAsIDY2LCAxMTksIDE5LCAtMTE3LCAyNiwgNywgLTExNywgMTAwLCAtOTksIC04MSwgLTUsIC0xMjQsIDk0LCA2NCwgMTAsIC0xMDgsIDkwLCAtMywgNjIsIDg1LCAzNywgMTMsIC0xMDcsIC00MiwgLTExMywgLTk2LCA4MiwgLTEyNCwgOTYsIDkwLCAtMTIwLCAtMTAxLCAtNjAsIC0xMTEsIDQ4LCAtNzMsIDM4LCAxMjcsIDc3LCA4MSwgLTY2LCAtMzAsIDg1LCA4MywgLTU0LCAtMTUsIDEwNiwgLTk1LCAtMTI1LCAtNzcsIC0xMDksIDUwLCAtMiwgLTIzLCAtMzUsIDU5LCAtMTE0LCAtMzcsIDEwNCwgMTExLCA4MSwgLTIxLCA2LCAtNjUsIDEsIDgwLCAxNSwgLTUsIDEyMSwgLTY2LCAtMTksIC04MCwgLTEwMCwgLTExNSwgNjUsIDQ0LCAtMzcsIC01LCAtMTIzLCAxMDgsIDExNywgMTI3LCAzNywgNzEsIDkwLCAtNDYsIC0xMDMsIC03NywgLTIwLCAtMTEwLCAxMDEsIC0xMDMsIDEwMywgLTk0LCAtOTIsIC0xMiwgLTg4LCA0MCwgLTM2LCAtNDEsIDEwOCwgLTU2LCA2MSwgLTM1LCAxMDUsIC0xMjYsIC0xMTAsIC00OSwgNjcsIC04NCwgLTQ3LCA4MCwgMTEwLCAxMjdd".getBytes()));
//        String decodedString7 = new String(Base64.decodeBase64("MD0CHFPvv73vv73vv70HF++/ve+/vWHvv70aZO+/vd62Pk8977+977+9BQXvv73vv73vv70uAh0A77+977+9Fu+/vRojeQnvv70G77+977+9Sta577+977+977+9f++/vXXvv70SbSnUqe+/vQ==".getBytes()));
////        "VXBkYXRlRmlsZQ==",
////                "NDViMjMzZmFlNjAyMTE2YTg1NTA2MTY5YzEyYjVkMTYyNjI5MWQzNzU5ZTlkMTNlZjExOGI3MjBiMTBiZGRhZQ==",
////                "MS5wZGY=",
////                "WzQ4LCAtMTI2LCAzLCA2NiwgNDgsIC0xMjYsIDIsIDUzLCA2LCA3LCA0MiwgLTEyMiwgNzIsIC01MCwgNTYsIDQsIDEsIDQ4LCAtMTI2LCAyLCA0MCwgMiwgLTEyNiwgMSwgMSwgMCwgLTExMywgMTIxLCA1MywgLTM5LCAtNzEsIC04NiwgLTIzLCAtNjUsIC04NSwgLTE5LCAtMTIwLCAxMjIsIC00OSwgNzMsIDgxLCAtNzQsIC0xMywgNDYsIC01OSwgLTk4LCA1OSwgLTgxLCA1NSwgMjQsIC0yNCwgLTIyLCAtNjAsIC0xMDYsIDMxLCA2MiwgLTMsIDU0LCA2LCAtMjUsIDY3LCA4MSwgLTg3LCAtNjAsIDI0LCA1MSwgNTcsIC03MiwgOSwgLTI1LCAtNjIsIC04MiwgMjgsIDgzLCAtMTAxLCAtODksIDcxLCA5MSwgLTEyMywgLTQ4LCAxNywgLTgzLCAtNzIsIC03NiwgMTIxLCAtMTIxLCAxMTcsIDczLCAtMTI0LCAxMDUsIDkyLCAtODQsIDE0LCAtMTEzLCAyMCwgLTc3LCA1NCwgOCwgNDAsIC05NCwgNDcsIC02LCAzOSwgMTcsIDEwLCA2MSwgOTgsIC04NywgLTEwOSwgNjksIDUyLCA5LCAtOTYsIC0yLCAxMDUsIDEwOCwgNzAsIDg4LCAtOCwgNzUsIC0zNSwgMzIsIC0xMjcsIC0xMDAsIDU1LCA5LCAtOTYsIDE2LCA4NywgLTc5LCAtMTA3LCAtODMsIC01MSwgMCwgMzUsIDYxLCAtNzAsIDg0LCAtMTI0LCAtNzQsIDQxLCAzMSwgLTk5LCAxMDAsIC0xMTQsIC04LCAtMTI1LCA2OCwgLTEyMiwgMTE5LCAtMTA1LCAtMTAwLCAtMjAsIDQsIC03NiwgNTIsIC05MCwgLTg0LCA0NiwgMTE3LCAtMjMsIC0xMDQsIDkzLCAtMzAsIDYxLCAtODAsIDQxLCA0NywgLTYzLCAxNywgLTExNiwgLTk3LCAtNiwgLTk5LCAtMTI3LCAtMTI3LCAtMjUsIDUxLCAtMTE1LCAtNzMsIC0xMTAsIC03MywgNDgsIC00MSwgLTcxLCAtMjksIDczLCA4OSwgNDcsIDEwNCwgOSwgLTEwNCwgMTE0LCAyMSwgNTcsIDIxLCAtMjIsIDYxLCAxMDcsIC0xMTcsIDcwLCA4MywgLTU4LCA1MSwgNjksIC0xMTMsIC0xMjgsIDU5LCA1MCwgLTkyLCAtNjIsIC0zMiwgLTE0LCAxMTQsIC0xMTIsIDM3LCAxMTAsIDc4LCA2MywgLTExOCwgNTksIDgsIDU2LCAtOTUsIC02MCwgODAsIC0yOCwgLTMxLCAtMTE2LCAyNiwgNDEsIC05MywgMTI1LCAtMzMsIDk0LCAtOTUsIDY3LCAtMzQsIDc1LCAxMDIsIC0xLCA0LCAtMTEyLCA2MiwgLTQzLCAtNDksIDIyLCAzNSwgLTMxLCA4OCwgLTQ0LCAtMTIxLCAtNTgsIDgsIC0yMywgMTI3LCAzMywgMjgsIC00MCwgMjksIC01NCwgMzUsIC01MywgMTEwLCA1NiwgNywgMTAxLCAtOCwgMzQsIC0yOSwgNjYsIC02NiwgNzIsIDc2LCA1LCAxMTgsIDU3LCA1NywgOTYsIDI4LCAtNDIsIDEwMywgMiwgMjksIDAsIC03MCwgLTEwLCAtMTA2LCAtOTAsIC0xMjMsIDEyMCwgLTksIC0zMywgLTM0LCAtMjUsIC02LCAxMDMsIC01NSwgMTE5LCAtNTcsIC0xMjMsIC0xNywgNTAsIC03OCwgNTEsIC03MCwgLTI3LCAtMTI4LCAtNjQsIC02OCwgLTQzLCAxMDUsIDkzLCAyLCAtMTI2LCAxLCAwLCAyMiwgLTkwLCA5MiwgODgsIDMyLCA3MiwgODAsIDExMiwgNzgsIDExNywgMiwgLTkzLCAtMTA1LCA4NywgNCwgMTMsIDUyLCAtMzgsIDU4LCA1MiwgMTIwLCAtNjMsIDg0LCAtNDQsIC0yOCwgLTkxLCAtNjQsIDQ1LCAzNiwgNDYsIC0zMiwgNzksIC0xMDYsIC0yNiwgMzAsIDc1LCAtNDgsIC0xMTIsIDc0LCAtNjcsIC04NCwgLTExMywgNTUsIC0xOCwgLTc5LCAtMzIsIC05NywgNDksIC0xMjYsIC00NiwgNjAsIC0xMTIsIDY3LCAtNTMsIDEwMCwgNDcsIC0xMjAsIDAsIDY1LCA5NiwgLTE5LCAtNywgLTU0LCA5LCAtNzcsIDMyLCAxMTgsIC04OSwgLTEwMCwgNTAsIC05MCwgMzksIC0xNCwgNzEsIDYyLCAtMTExLCAtMTIxLCAtMTAxLCAtOTQsIC02MCwgLTI1LCA2OCwgLTY3LCAzMiwgLTEyNywgODQsIDc2LCAtNzUsIDkxLCAtMTI4LCA0NCwgNTQsIC0xMTUsIDMxLCAtODgsIDYyLCAtNDQsIC0xMTksIC0yMywgNzgsIDE1LCAtOTYsIDEwNCwgLTExNCwgNTAsIDY2LCAtMTE4LCA5MiwgMTIwLCAtNjAsIDEyMCwgLTU4LCAtMTE1LCA1LCAzOSwgLTczLCAyOCwgLTEwMiwgNTgsIC02OSwgMTEsIDExLCAtMzEsIDQ0LCA2OCwgMTA0LCAtMTA2LCA1NywgLTI1LCAtNDUsIC01MCwgMTE2LCAtMzcsIDE2LCAyNiwgMTAxLCAtODYsIDQzLCAtMTIxLCAtMTAsIDc2LCAxMDQsIDM4LCAtMzcsIDYyLCAtNTcsIDQ3LCA3NSwgODUsIC0xMDMsIC0xMjUsIDc1LCAtNzYsIC0xOSwgLTgwLCA0NywgMTI0LCAtMTEyLCAtMjMsIC05MiwgLTEwNiwgLTQ1LCAtOTEsIDkzLCA4MywgOTEsIC0yMSwgLTQsIDY5LCAtNDQsIC0xMCwgMjUsIC0xMCwgNjMsIDYxLCAtMTksIC02OSwgLTEyMSwgNTcsIDM3LCAtNjIsIC0xNCwgMzYsIC0zMiwgMTE5LCA0OSwgNDEsIDEwOSwgLTg4LCAtMTIxLCAtMjAsIDMwLCA3MSwgNzIsIC04LCAxMjYsIC01LCA5NSwgLTM0LCAtNzMsIDg0LCAtMTI0LCA0OSwgMTA3LCAzNCwgNTAsIC0zNCwgLTI3LCA4MywgLTM1LCAtODEsIDIsIDE3LCA0MywgMTMsIDMxLCAyLCAtMzgsIDQ4LCAtMTA1LCA1MCwgMzYsIC0yLCAzOSwgLTgyLCAtMzgsIC0xMTcsIC05OSwgNzUsIDQxLCAzNCwgLTM5LCAtNzAsIC0xMTcsIC0yOSwgLTk4LCAtMzksIC0zMSwgMywgLTkwLCA2MCwgODIsIC0xMjcsIDExLCAtNTgsIC0xMjAsIC03MywgLTMwLCAtMTksIDY3LCAyMiwgLTMxLCAtMTcsIDIzLCAtMzcsIC0zNCwgMywgLTEyNiwgMSwgNSwgMCwgMiwgLTEyNiwgMSwgMCwgNDMsIC00OCwgLTc5LCAtMTEyLCA2NiwgNjksIC0xMTIsIDExLCA5MywgLTEyMiwgLTUwLCAtOSwgLTE5LCAtMzksIDY1LCAtNjIsIDY2LCAtMTYsIC0xMDQsIC0zMywgNzksIC00MywgLTExOCwgNjksIC0xMDQsIC00MCwgNTgsIDMsIDExLCAxMTcsIDgzLCAtNTgsIC0xNiwgOTEsIC0xMTksIC0xMTcsIDMxLCA4MSwgLTEyOCwgMzMsIDg0LCAtODAsIDE3LCAxLCA4LCAzMywgLTQxLCAtMTE4LCA1MiwgLTk4LCAzMywgNjMsIDEsIC04MiwgLTM1LCAtNDQsIDIzLCA5NywgNzMsIDgwLCAxMTEsIC00MywgLTEwNCwgLTUxLCAtNjAsIC0xMDgsIC03NCwgOTMsIC05NywgLTM4LCAtMTA1LCAxMCwgLTg5LCA3LCAtODEsIC0xMDQsIC0xMjAsIDc5LCAxOCwgLTg4LCAtMTIzLCAtOCwgMTIyLCAxMjQsIC0xMjcsIDExMiwgNjksIC0xMDQsIDE4LCAzNywgLTM2LCA3NSwgLTEwOSwgNywgMTE2LCA2NCwgMTAwLCA4LCA2LCAzNiwgLTc2LCAtMTEsIC05MCwgLTEyMiwgLTc3LCAtMTI1LCAtNjksIC01OSwgLTE5LCAtMTA0LCAtOTQsIC0xMTgsIC0xOSwgMTE1LCAtMTIxLCAyMSwgLTg3LCAtMzgsIDcxLCAtNjcsIC01MCwgLTk0LCAyLCAtMTA3LCAtOCwgLTYzLCAtMTAsIDY0LCAzLCA3NywgODMsIC0yNywgMTEyLCA0OCwgNjksIC05MiwgLTI5LCAxMjQsIDUwLCAtMjUsIC0xMjMsIC0xMjYsIC0xNiwgNTMsIC02NSwgNDMsIDc0LCAtMzMsIDQsIDkzLCA1NSwgMzYsIC04OCwgLTczLCAtNzEsIDcsIDcxLCA2OCwgLTY3LCA3OSwgOTYsIDcwLCAxMjUsIC05LCAtNSwgLTgxLCAtMTA5LCA0NCwgLTEwOCwgLTExNSwgNiwgODAsIDkwLCAzMSwgMTE5LCAtNjAsIDEyNCwgLTc0LCAxLCAtOTgsIDI0LCAtNDUsIDEzLCA1NywgLTYxLCAtMTE2LCAtMTA4LCAzMSwgLTQ1LCAtNDYsIC01OCwgNjksIC0xMjcsIC0xMTYsIC04LCAtNzYsIC0xMTgsIDI4LCA3OCwgLTU5LCA4OSwgLTExLCAtMzMsIDc1LCA3MSwgLTExMSwgMTEzLCA3MywgMTYsIC0xMTUsIC0xMjIsIDg5LCA4MCwgLTc1LCAtOTQsIDQ5LCA4MywgLTY0LCAtMjQsIC0yOSwgLTM3LCA3MywgLTU2LCAxMTMsIDcsIDU1LCAtNDEsIDk4LCAzNywgMjgsIC0zNiwgLTgsIDYyLCAtMTI3LCA3NCwgLTExMCwgMjUsIDgzLCA1NSwgMzcsIC04NywgOTIsIC01OSwgLTExMiwgMzQsIC0xMTcsIDI0LCAtNjQsIC00MSwgNjAsIDQ4LCAtMTIsIC04MiwgNTMsIC02OSwgMTVd",
////                "MDwCHFhzCVlX77+9GO+/ve+/vTHvv70g77+9f++/ve+/vUTvv73vv73vv73vv702dd2D77+9eAIcbyPvv73vv70gHwfvv73vv70Y77+977+9O1IQWe+/vSFd77+9GU7vv70l77+9eO+/vQ==",
////                "WzQ4LCAtMTI2LCAzLCA2NiwgNDgsIC0xMjYsIDIsIDUzLCA2LCA3LCA0MiwgLTEyMiwgNzIsIC01MCwgNTYsIDQsIDEsIDQ4LCAtMTI2LCAyLCA0MCwgMiwgLTEyNiwgMSwgMSwgMCwgLTExMywgMTIxLCA1MywgLTM5LCAtNzEsIC04NiwgLTIzLCAtNjUsIC04NSwgLTE5LCAtMTIwLCAxMjIsIC00OSwgNzMsIDgxLCAtNzQsIC0xMywgNDYsIC01OSwgLTk4LCA1OSwgLTgxLCA1NSwgMjQsIC0yNCwgLTIyLCAtNjAsIC0xMDYsIDMxLCA2MiwgLTMsIDU0LCA2LCAtMjUsIDY3LCA4MSwgLTg3LCAtNjAsIDI0LCA1MSwgNTcsIC03MiwgOSwgLTI1LCAtNjIsIC04MiwgMjgsIDgzLCAtMTAxLCAtODksIDcxLCA5MSwgLTEyMywgLTQ4LCAxNywgLTgzLCAtNzIsIC03NiwgMTIxLCAtMTIxLCAxMTcsIDczLCAtMTI0LCAxMDUsIDkyLCAtODQsIDE0LCAtMTEzLCAyMCwgLTc3LCA1NCwgOCwgNDAsIC05NCwgNDcsIC02LCAzOSwgMTcsIDEwLCA2MSwgOTgsIC04NywgLTEwOSwgNjksIDUyLCA5LCAtOTYsIC0yLCAxMDUsIDEwOCwgNzAsIDg4LCAtOCwgNzUsIC0zNSwgMzIsIC0xMjcsIC0xMDAsIDU1LCA5LCAtOTYsIDE2LCA4NywgLTc5LCAtMTA3LCAtODMsIC01MSwgMCwgMzUsIDYxLCAtNzAsIDg0LCAtMTI0LCAtNzQsIDQxLCAzMSwgLTk5LCAxMDAsIC0xMTQsIC04LCAtMTI1LCA2OCwgLTEyMiwgMTE5LCAtMTA1LCAtMTAwLCAtMjAsIDQsIC03NiwgNTIsIC05MCwgLTg0LCA0NiwgMTE3LCAtMjMsIC0xMDQsIDkzLCAtMzAsIDYxLCAtODAsIDQxLCA0NywgLTYzLCAxNywgLTExNiwgLTk3LCAtNiwgLTk5LCAtMTI3LCAtMTI3LCAtMjUsIDUxLCAtMTE1LCAtNzMsIC0xMTAsIC03MywgNDgsIC00MSwgLTcxLCAtMjksIDczLCA4OSwgNDcsIDEwNCwgOSwgLTEwNCwgMTE0LCAyMSwgNTcsIDIxLCAtMjIsIDYxLCAxMDcsIC0xMTcsIDcwLCA4MywgLTU4LCA1MSwgNjksIC0xMTMsIC0xMjgsIDU5LCA1MCwgLTkyLCAtNjIsIC0zMiwgLTE0LCAxMTQsIC0xMTIsIDM3LCAxMTAsIDc4LCA2MywgLTExOCwgNTksIDgsIDU2LCAtOTUsIC02MCwgODAsIC0yOCwgLTMxLCAtMTE2LCAyNiwgNDEsIC05MywgMTI1LCAtMzMsIDk0LCAtOTUsIDY3LCAtMzQsIDc1LCAxMDIsIC0xLCA0LCAtMTEyLCA2MiwgLTQzLCAtNDksIDIyLCAzNSwgLTMxLCA4OCwgLTQ0LCAtMTIxLCAtNTgsIDgsIC0yMywgMTI3LCAzMywgMjgsIC00MCwgMjksIC01NCwgMzUsIC01MywgMTEwLCA1NiwgNywgMTAxLCAtOCwgMzQsIC0yOSwgNjYsIC02NiwgNzIsIDc2LCA1LCAxMTgsIDU3LCA1NywgOTYsIDI4LCAtNDIsIDEwMywgMiwgMjksIDAsIC03MCwgLTEwLCAtMTA2LCAtOTAsIC0xMjMsIDEyMCwgLTksIC0zMywgLTM0LCAtMjUsIC02LCAxMDMsIC01NSwgMTE5LCAtNTcsIC0xMjMsIC0xNywgNTAsIC03OCwgNTEsIC03MCwgLTI3LCAtMTI4LCAtNjQsIC02OCwgLTQzLCAxMDUsIDkzLCAyLCAtMTI2LCAxLCAwLCAyMiwgLTkwLCA5MiwgODgsIDMyLCA3MiwgODAsIDExMiwgNzgsIDExNywgMiwgLTkzLCAtMTA1LCA4NywgNCwgMTMsIDUyLCAtMzgsIDU4LCA1MiwgMTIwLCAtNjMsIDg0LCAtNDQsIC0yOCwgLTkxLCAtNjQsIDQ1LCAzNiwgNDYsIC0zMiwgNzksIC0xMDYsIC0yNiwgMzAsIDc1LCAtNDgsIC0xMTIsIDc0LCAtNjcsIC04NCwgLTExMywgNTUsIC0xOCwgLTc5LCAtMzIsIC05NywgNDksIC0xMjYsIC00NiwgNjAsIC0xMTIsIDY3LCAtNTMsIDEwMCwgNDcsIC0xMjAsIDAsIDY1LCA5NiwgLTE5LCAtNywgLTU0LCA5LCAtNzcsIDMyLCAxMTgsIC04OSwgLTEwMCwgNTAsIC05MCwgMzksIC0xNCwgNzEsIDYyLCAtMTExLCAtMTIxLCAtMTAxLCAtOTQsIC02MCwgLTI1LCA2OCwgLTY3LCAzMiwgLTEyNywgODQsIDc2LCAtNzUsIDkxLCAtMTI4LCA0NCwgNTQsIC0xMTUsIDMxLCAtODgsIDYyLCAtNDQsIC0xMTksIC0yMywgNzgsIDE1LCAtOTYsIDEwNCwgLTExNCwgNTAsIDY2LCAtMTE4LCA5MiwgMTIwLCAtNjAsIDEyMCwgLTU4LCAtMTE1LCA1LCAzOSwgLTczLCAyOCwgLTEwMiwgNTgsIC02OSwgMTEsIDExLCAtMzEsIDQ0LCA2OCwgMTA0LCAtMTA2LCA1NywgLTI1LCAtNDUsIC01MCwgMTE2LCAtMzcsIDE2LCAyNiwgMTAxLCAtODYsIDQzLCAtMTIxLCAtMTAsIDc2LCAxMDQsIDM4LCAtMzcsIDYyLCAtNTcsIDQ3LCA3NSwgODUsIC0xMDMsIC0xMjUsIDc1LCAtNzYsIC0xOSwgLTgwLCA0NywgMTI0LCAtMTEyLCAtMjMsIC05MiwgLTEwNiwgLTQ1LCAtOTEsIDkzLCA4MywgOTEsIC0yMSwgLTQsIDY5LCAtNDQsIC0xMCwgMjUsIC0xMCwgNjMsIDYxLCAtMTksIC02OSwgLTEyMSwgNTcsIDM3LCAtNjIsIC0xNCwgMzYsIC0zMiwgMTE5LCA0OSwgNDEsIDEwOSwgLTg4LCAtMTIxLCAtMjAsIDMwLCA3MSwgNzIsIC04LCAxMjYsIC01LCA5NSwgLTM0LCAtNzMsIDg0LCAtMTI0LCA0OSwgMTA3LCAzNCwgNTAsIC0zNCwgLTI3LCA4MywgLTM1LCAtODEsIDIsIDE3LCA0MywgMTMsIDMxLCAyLCAtMzgsIDQ4LCAtMTA1LCA1MCwgMzYsIC0yLCAzOSwgLTgyLCAtMzgsIC0xMTcsIC05OSwgNzUsIDQxLCAzNCwgLTM5LCAtNzAsIC0xMTcsIC0yOSwgLTk4LCAtMzksIC0zMSwgMywgLTkwLCA2MCwgODIsIC0xMjcsIDExLCAtNTgsIC0xMjAsIC03MywgLTMwLCAtMTksIDY3LCAyMiwgLTMxLCAtMTcsIDIzLCAtMzcsIC0zNCwgMywgLTEyNiwgMSwgNSwgMCwgMiwgLTEyNiwgMSwgMCwgNTUsIDE3LCA4MywgLTY5LCAxNiwgMjUsIDc5LCAxMjcsIC04NSwgLTE3LCAtMzcsIC04OSwgLTg1LCAzNiwgMTA3LCAtMTA5LCAtMTA4LCAtOTAsIDM2LCAxMywgNTQsIC00MywgLTExMiwgLTE3LCA0MywgMywgLTExLCAxMjYsIC0yNCwgLTE1LCAyNiwgMzEsIC03NCwgMTIxLCAzNywgLTU4LCAtNDYsIDEzLCAtMjcsIC04LCAtMTEzLCA0MSwgLTU4LCAtNzAsIDMyLCAtNTIsIDkzLCAtOTksIDU3LCAtMiwgMTEsIC0yMCwgNTksIC0xNywgLTQ0LCAtOTcsIDM2LCAxNSwgLTEyOCwgLTE2LCAtMTA4LCAtMTE1LCA1NCwgOTUsIC0yMCwgLTEwNCwgNzIsIC0xMTIsIDk1LCAxMDgsIDYzLCAtNDUsIC05NywgLTEwOSwgLTM5LCAtODAsIC0xOCwgMCwgLTU1LCA1MCwgLTEwMSwgLTExNywgLTgzLCAzMywgOTAsIDEsIDM0LCA5MCwgOTksIDcyLCAxMDUsIC02LCA1NSwgMTA4LCAtMTE3LCA0OSwgLTYxLCAxMDEsIDEyNCwgLTEwMSwgMzgsIC02NCwgLTIsIC0xMDIsIC0xMiwgOTUsIDk4LCAtNTksIDI0LCAtODMsIDQyLCAxNSwgMTA2LCAxMTYsIC05MSwgMTE3LCAtOSwgLTUyLCAtMTIyLCAtMTQsIDU5LCA5NCwgOSwgLTQ4LCAtODgsIC0xMiwgNCwgNDAsIDc5LCAxMDcsIC0xMDgsIC0xMjYsIC04MCwgLTEyNywgLTk1LCA3LCA5LCAzLCA2MCwgMzAsIDE3LCAtMzAsIDY2LCAxMTksIDE5LCAtMTE3LCAyNiwgNywgLTExNywgMTAwLCAtOTksIC04MSwgLTUsIC0xMjQsIDk0LCA2NCwgMTAsIC0xMDgsIDkwLCAtMywgNjIsIDg1LCAzNywgMTMsIC0xMDcsIC00MiwgLTExMywgLTk2LCA4MiwgLTEyNCwgOTYsIDkwLCAtMTIwLCAtMTAxLCAtNjAsIC0xMTEsIDQ4LCAtNzMsIDM4LCAxMjcsIDc3LCA4MSwgLTY2LCAtMzAsIDg1LCA4MywgLTU0LCAtMTUsIDEwNiwgLTk1LCAtMTI1LCAtNzcsIC0xMDksIDUwLCAtMiwgLTIzLCAtMzUsIDU5LCAtMTE0LCAtMzcsIDEwNCwgMTExLCA4MSwgLTIxLCA2LCAtNjUsIDEsIDgwLCAxNSwgLTUsIDEyMSwgLTY2LCAtMTksIC04MCwgLTEwMCwgLTExNSwgNjUsIDQ0LCAtMzcsIC01LCAtMTIzLCAxMDgsIDExNywgMTI3LCAzNywgNzEsIDkwLCAtNDYsIC0xMDMsIC03NywgLTIwLCAtMTEwLCAxMDEsIC0xMDMsIDEwMywgLTk0LCAtOTIsIC0xMiwgLTg4LCA0MCwgLTM2LCAtNDEsIDEwOCwgLTU2LCA2MSwgLTM1LCAxMDUsIC0xMjYsIC0xMTAsIC00OSwgNjcsIC04NCwgLTQ3LCA4MCwgMTEwLCAxMjdd",
////                "MD0CHFPvv73vv73vv70HF++/ve+/vWHvv70aZO+/vd62Pk8977+977+9BQXvv73vv73vv70uAh0A77+977+9Fu+/vRojeQnvv70G77+977+9Sta577+977+977+9f++/vXXvv70SbSnUqe+/vQ=="
//		System.out.println("Decoded line is " + decodedString1 + " " + decodedString2 + " " + decodedString3 + " " + decodedString4 + " " + decodedString5 + " " + decodedString6 + " " + decodedString7);

        // connect to the network and invoke the smart contract
        try (Gateway gateway = connect()) {

            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("basic");

            byte[] result;

            File firstFile = new File("/Users/richardwindsorair/Downloads/1.pdf");
            File secondFile = new File("/Users/richardwindsorair/Downloads/2.pdf");
            File thirdFile = new File("/Users/richardwindsorair/Downloads/3.pdf");

            byte[] fileAsBytes = Files.readAllBytes(Paths.get("/Users/richardwindsorair/Downloads/1.pdf"));
            String hashString1 = DigestUtils.sha256Hex(fileAsBytes);
            System.out.println("The SHA-256 hash of the file " + firstFile.getName() + " is: " + hashString1);

            fileAsBytes = Files.readAllBytes(Paths.get("/Users/richardwindsorair/Downloads/2.pdf"));
            String hashString2 = DigestUtils.sha256Hex(fileAsBytes);
            System.out.println("The SHA-256 hash of the file " + secondFile.getName() + " is: " + hashString2);

            fileAsBytes = Files.readAllBytes(Paths.get("/Users/richardwindsorair/Downloads/3.pdf"));
            String hashString3 = DigestUtils.sha256Hex(fileAsBytes);
            System.out.println("The SHA-256 hash of the file " + thirdFile.getName() + " is: " + hashString3);

            MyFile myFile1 = new MyFile();
            myFile1.fileID = hashString1;
            myFile1.fileName = firstFile.getName();
            myFile1.signerPrivateKey1 = "";
            myFile1.signerPublicKey1 = "";
            myFile1.signerSignature1 = "";
            myFile1.signerPrivateKey2 = "";
            myFile1.signerPublicKey2 = "";
            myFile1.signerSignature2 = "";

            MyFile myFile2 = new MyFile();
            myFile2.fileID = hashString2;
            myFile2.fileName = secondFile.getName();

            MyFile myFile3 = new MyFile();
            myFile3.fileID = hashString3;
            myFile3.fileName = thirdFile.getName();



            try {
                System.out.println("Submit Transaction: CreateFile file1");
                //CreateFile creates a file with fileID hashString1, name: name of the file, size: size of the file, and owner Aydar
                contract.submitTransaction(BlockChainCommand.CREATE_FILE.value(), myFile1.fileID, myFile1.fileName, myFile1.signerPublicKey1, myFile1.signerSignature1, myFile1.signerPublicKey2, myFile1.signerSignature2);

                System.out.println("\n");
                System.out.println("Evaluate Transaction: ReadFile file1");
                //ReadFile returns a file with given fileID
                result = contract.evaluateTransaction(BlockChainCommand.READ_FILE.value(), myFile1.fileID);
                System.out.println("result: " + new String(result));

                Scanner myInput = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Do you want to sign the file " + myFile1.fileName + " ? [y/n]: ");
                String myChoice = myInput.nextLine();

                if (myChoice.equals(myYes)) {

                    SecretInfo signingDocSigner1 = signDoc(myFile1.fileID);
                    myFile1.signerPrivateKey1 = Arrays.toString(signingDocSigner1.getPrivateKey().getEncoded());
                    myFile1.signerPublicKey1 = Arrays.toString(signingDocSigner1.publicKey.getEncoded());
                    myFile1.signerSignature1 = new String(signingDocSigner1.signature);

                    SecretInfo signingDocSigner2 = signDoc(myFile1.fileID);
                    myFile1.signerPrivateKey1 = Arrays.toString(signingDocSigner2.getPrivateKey().getEncoded());
                    myFile1.signerPublicKey1 = Arrays.toString(signingDocSigner2.publicKey.getEncoded());
                    myFile1.signerSignature1 = new String(signingDocSigner2.signature);

                    System.out.println("Submit Transaction: UpdateFile file1");
                    //UpdateFile updates a file with ID hashstring1, name 1.pdf, owner Aydar with a new signature
                    contract.submitTransaction(BlockChainCommand.UPDATE_FILE.value(), myFile1.fileID, myFile1.fileName, myFile1.signerPublicKey1, myFile1.signerSignature1, myFile1.signerPublicKey2, myFile1.signerSignature2);

                    System.out.println("\n");
                    System.out.println("Evaluate Transaction: ReadFile file1");
                    //ReadFile returns a file with given fileID
                    result = contract.evaluateTransaction(BlockChainCommand.READ_FILE.value(), myFile1.fileID);
                    System.out.println("result: " + new String(result));
                }
            } catch (Exception e) {
                System.err.println(e);
            }

            try {
                System.out.println("Is there " + myFile2.fileName + " in the Blockchain?");
                result = contract.evaluateTransaction(BlockChainCommand.FILE_EXISTS.value(), myFile2.fileID);
                System.out.println(new String(result));

            } catch (Exception e) {
                System.out.println(e);
            }

            try {
                System.out.println("Is there " + myFile3.fileName + " in the Blockchain?");
                result = contract.evaluateTransaction(BlockChainCommand.FILE_EXISTS.value(), myFile3.fileID);
                System.out.println(new String(result));
            } catch (Exception e) {
                System.out.println(e);
            }

            try {
                Scanner myInput = new Scanner(System.in);  // Create a Scanner object
                System.out.println("Show all files in the Blockchain? [y/n]: ");
                String myChoice = myInput.nextLine();  // Read user input

                if (myChoice.equals(myYes)) {

                    System.out.println("\n");
                    System.out.println("Evaluate Transaction: GetAllFiles");
                    // GetAllFiles returns a file with given fileID
                    result = contract.evaluateTransaction(BlockChainCommand.GET_ALL_FILES.value());
                    System.out.println("result: " + new String(result));
                }
            } catch (Exception e) {
                System.out.println("Could not print all the files");
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
