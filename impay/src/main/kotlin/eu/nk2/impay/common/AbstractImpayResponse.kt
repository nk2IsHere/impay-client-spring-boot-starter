package eu.nk2.impay.common

abstract class AbstractImpayResponse {

    abstract val status: Int
    abstract val message: String?
    abstract val name: String?
    abstract val code: ImpayError?

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractImpayResponse

        if (status != other.status) return false
        if (message != other.message) return false
        if (name != other.name) return false
        if (code != other.code) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status
        result = 31 * result + (message?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (code?.hashCode() ?: 0)
        return result
    }

    abstract override fun toString(): String
}
