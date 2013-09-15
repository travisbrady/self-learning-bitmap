self-learning-bitmap
====================

Python and Java versions of Chen &amp; Cao's Self-Learning Bitmap algorithm for distinct value estimation

A Self-Learning Bitmap is a data structure not unlike a HyperLogLog.
The goal is to count the distinct values in some set without storing all of those values.
It has many applications in data stream processing or anywhere where you've got more data than you've
got memory.

When run as a script the Python version reads lines from stdin, adds them to the sbitmap and when
stdin is exhausted it prints its estimate of the number of unique values to stdout.

Example reading from stdin
```bash
$ cat /usr/share/dict/words | wc -l
  235886
$ cat /usr/share/dict/words | python sbitmap.py
  229153.828351
```
Which is ~2.85% error, slightly better than the 3% error target specified.
This can be tuned with the `error` param passed in __init__

Interactive example:
```python
>>> import sbitmap
>>> sb = sbitmap.SBitmap(1000000, 0.01)
>>> for x in ("hi", "bye", "this", "that"): sb.add(x)
>>> print sb.estimate()
4.0016004401
```

## Alpha Notice
All of this code is for demonstration purposes, I wrote it a few months ago when I couldn't find any implementation
of the Self-Learning Bitmap available online.  But needless to say it has not been rigorously tested.

The java directory has a README of its own.




