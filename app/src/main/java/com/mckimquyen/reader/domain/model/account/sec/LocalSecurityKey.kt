package com.mckimquyen.reader.domain.model.account.sec

class LocalSecurityKey private constructor() : SecurityKey() {

    constructor(value: String? = DESUtils.empty) : this() {
        decode(value, LocalSecurityKey::class.java).let {

        }
    }
}
