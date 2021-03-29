package eu.nk2.impay.utils

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter


fun String.sha1(): String {
    val msdDigest = MessageDigest.getInstance("SHA-1")
    msdDigest.update(this.toByteArray(), 0, this.length)

    return DatatypeConverter.printHexBinary(msdDigest.digest())
}

fun String.md5(): String {
    val msdDigest = MessageDigest.getInstance("MD5")
    msdDigest.update(this.toByteArray(), 0, this.length)

    return DatatypeConverter.printHexBinary(msdDigest.digest())
}
