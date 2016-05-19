package camt.se331.shoppingcart.entity.transfer;

/**
 * Created by Dto on 4/20/2015.
 */
public class TokenTransfer {
    private final String token;


    public TokenTransfer(String token)
    {
        this.token = token;
    }


    public String getToken()
    {
        return this.token;
    }
}
