"""
    Based on the paper: Distinct Counting with a Self-Learning Bitmap
    http://ect.bell-labs.com/who/aychen/sbitmap4p.pdf
    Author: Travis Brady
"""
from math import log
from bitarray import bitarray
from cityhash import CityHash64

class SBitmap(object):

    def __init__(self, Nmax, error, hsh=CityHash64, verbose=False):
        self.Nmax = Nmax
        self.hsh = hsh
        self.error = error
        self.error2 = error ** 2
        m_num = log(1 + 2 * Nmax * self.error2)
        m_denom = log(1 + 2*self.error2 * (1 - self.error2)**-1)
        self.m = m_num/m_denom
        self.V = bitarray('0') * int(self.m)
        self.L = 0
        self.c = int(log(self.m, 2))
        self.d = 64 - self.c
        self.r = 1 - 2 * self.error2 * (1 + self.error2)**-1
        self.tt = 0.0
        if verbose:
            print "M", self.m, "c", self.c

    def __repr__(self):
        return repr(self.V)

    def get_pk(self, k):
        m = self.m
        return m * (m + 1 - k)**-1 * (1 + self.error2) * self.r ** k

    def get_q(self, l, pl):
        return self.m ** -1 * (self.m - l + 1) * pl

    def estimate(self):
        tl = 0.0
        for i in range(1, self.L + 1):
            q = self.get_q(i, self.get_pk(i)) ** -1
            tl += q
        return tl

    def get_rightmost_bits(self, thing, nbits):
        return thing & ((1 << nbits) - 1)

    def add(self, item):
        h = self.hsh(item)
        j = h >> (64 - self.c)
        if not self.V[j]:
            u = self.get_rightmost_bits(h, self.d)
            pLnext = self.get_pk(self.L + 1)
            if (u * 2 ** -self.d) < pLnext:
                self.V[j] = 1
                self.L += 1

    def merge(self, other):
        if self.m != other.m:
            raise ValueError("SBitmaps differ in size")
        new_bitmap = self.V | other.V
        res = SBitmap(self.Nmax, self.error, hsh=self.hsh)
        res.V = new_bitmap
        res.L = new_bitmap.count()
        return res

if __name__ == '__main__':
    from sys import stdin, argv
    try:
        Nmax = int(argv[1])
    except:
        Nmax = 1000000
    sb = SBitmap(Nmax, 0.03)
    for line in stdin:
        sb.add(line)
    print sb.estimate()
