package com.github.travisbrady.cardinality;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashFunction;
import org.dishevelled.bitset.MutableBitSet;

public class SBitmap
{
    int Nmax, level, m, c, d;
    double md, error, errorsq, r, two_to_minus_d;

    private final MutableBitSet bitset;
    private final static HashFunction hasher = Hashing.murmur3_128();
    private final static double LOG_2 = 0.6931471805599453;

    public SBitmap(int Nmax, double error) {
        this.Nmax = Nmax;
        this.error = error;
        this.errorsq = Math.pow(error, 2.0);

        double m_num = Math.log(1.0 + 2.0 * Nmax * this.errorsq);
        double m_denom = Math.log(1.0 + 2.0 * this.errorsq * Math.pow(1.0 - this.errorsq, -1.0));
        double raw_m = m_num/m_denom;
        this.m = (int)Math.floor(raw_m);
        this.md = (double)m;

        this.bitset = new MutableBitSet((long)m);
        this.level = 0;
        this.c = (int)Math.floor(Math.log(this.md)/LOG_2);
        this.d = Long.SIZE - this.c;
        this.two_to_minus_d = Math.pow(2.0, -this.d);
        this.r = 1.0 - 2.0 * this.errorsq * Math.pow(1.0 + this.errorsq, -1.0);

        System.out.println("error,m,c,d,r");
        System.out.println(error);
        System.out.println(this.m);
        System.out.println(this.c);
        System.out.println(this.d);
        System.out.println(this.r);
    }

    private double getPk(int k) {
        double kd = (double)k;
        double ans = m * Math.pow(this.md + 1.0 - kd, -1.0) * (1.0 + this.errorsq) * Math.pow(this.r, kd);
        return ans;
    }

    private double getQ(int l, double pl) {
        double ld = (double)l;
        return Math.pow(this.md, -1.0) * (this.md - ld + 1) * pl;
    }

    public double estimate() {
        double tl = 0.0;
        System.out.println(String.format("Level %d", this.level));
        for(int i=1; i<this.level + 1; i++) {
            double pk = getPk(i);
            double q = getQ(i, pk);
            double x = Math.pow(q, -1.0);
            //System.out.println(String.format("I: %d Pk: %f q: %f X: %f", i, pk, q, x));
            tl += x;
        }
        return tl;
    }
    
    long getRightMostBits(long val, long nbits) {
        return val & ((1L << nbits) - 1L);
    }

    //@Override
    public boolean offer(String s) {
        final long x = hasher.hashString(s).asLong();
        long j = (x >>> (this.d));

        if(!this.bitset.get(j)) {
            long u = getRightMostBits(x, this.d);
            double ud = (double)u;

            double plNext = getPk(this.level + 1);
            double dude = ud * this.two_to_minus_d;
            if(dude < plNext) {
                this.bitset.set(j);
                this.level++;
                return true;
            }
        }
        return false;
    }
}

