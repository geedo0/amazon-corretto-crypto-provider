// Copyright Amazon.com Inc. or its affiliates. All Rights Reserved.
// SPDX-License-Identifier: Apache-2.0

package com.amazon.corretto.crypto.provider;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;

/**
 * Corresponds to native constants in OpenSSL which represent keytypes.
 */
enum EvpKeyType {
    RSA("RSA", 6, RSAPublicKey.class, RSAPrivateKey.class),
    EC("EC", 408, ECPublicKey.class, ECPrivateKey.class);

    final String jceName;
    final int nativeValue;
    final Class<? extends PublicKey> publicKeyClass;
    final Class<? extends PrivateKey> privateKeyClass;
    private final KeyFactory factory;

    private EvpKeyType(
            final String jceName,
            final int nativeValue,
            final Class<? extends PublicKey> publicKeyClass,
            final Class<? extends PrivateKey> privateKeyClass) {
        this.jceName = jceName;
        this.nativeValue = nativeValue;
        this.publicKeyClass = publicKeyClass;
        this.privateKeyClass = privateKeyClass;
        try {
            this.factory = KeyFactory.getInstance(jceName);
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError("KeyFactory for " + jceName + " not available");
        }
    }

    KeyFactory getKeyFactory() {
        return factory;
    }
}
