package com.autumnia.shopsystem.domain.vo;

import lombok.ToString;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@ToString
public class Money {
    private final BigDecimal amount;
    public static final Money zero = new Money(BigDecimal.ZERO);

    // loginc
    public boolean is_greater_than_zero() {
        if ( this.amount == null ) {
            return false;
        }

        if ( this.amount.compareTo(BigDecimal.ZERO) == 0 ) {
            return false;
        }

        return true;
    }

    public boolean is_greate_than( Money money ) {
        if ( this.amount == null ) {
            return false;
        }

        if ( this.amount.compareTo( money.getAmount() ) <= 0 ) {
            return false;
        }

        return true;
    }

    public Money add( Money money ) {
        return new Money( set_scale(this.amount.add( money.getAmount() )));
    }

    public Money substract( Money money ) {
        return new Money( set_scale(this.amount.subtract( money.getAmount() )));
    }

    public Money multiply( int multiplier ) {
        return new Money( set_scale(this.amount.multiply( new BigDecimal(multiplier) )));
    }

    private BigDecimal set_scale( BigDecimal value ) {
        return value.setScale(2, RoundingMode.HALF_EVEN);
    }

    // default
    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
}
