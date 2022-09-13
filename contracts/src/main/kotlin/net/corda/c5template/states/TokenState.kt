package net.corda.c5template.states

import com.r3.corda.lib.tokens.contracts.states.FungibleToken
import com.r3.corda.lib.tokens.contracts.types.IssuedTokenType
import net.corda.c5template.contracts.TokenContract
import net.corda.v5.application.identity.AbstractParty
import net.corda.v5.crypto.SecureHash
import net.corda.v5.ledger.contracts.Amount
import net.corda.v5.ledger.contracts.BelongsToContract

@BelongsToContract(TokenContract::class)
data class TokenState(
    override val amount: Amount<IssuedTokenType>,
    override val holder: AbstractParty,
    override val tokenTypeJarHash: SecureHash?
) : FungibleToken(amount, holder, tokenTypeJarHash)

