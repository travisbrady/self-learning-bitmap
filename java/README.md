# Java-SBitmap

Simple implementation of the [Self-Learning Bitmap data structure pdf](http://ect.bell-labs.com/who/aychen/sbitmap4p.pdf) for cardinality estimation.

# Example
See SBitmapStdin.java for an example usage
This reads strings off stdin line-by-line and once finished produces an estimate (Card Est).  The result 232,905 is 1.3% less than the actual value 235,886

    $ mvn assembly:assembly
    ...
    $ cat /usr/share/dict/words | java -jar target/sbitmap-1.0-SNAPSHOT-jar-with-dependencies.jar                                                                                                                                                                                                               
    ...
    Lines: 235886
    Level 3356
    Card Est: 232905.427287


From the abstract:

>    Estimating the number of distinct values is a
>    fundamental problem in database that has attracted extensive
>    research over the past two decades, due to its wide applications
>    (especially in the Internet). Many algorithms have been proposed
>    via sampling or sketching for obtaining statistical estimates that
>    only require limited computing and memory resources. However,
>    their performance in terms of relative estimation accuracy usually
>    depends on the unknown cardinalities. In this paper, we address
>    the following question: can a distinct counting algorithm have
>    uniformly reliable performance, i.e. constant relative estimation
>    errors for unknown cardinalities in a wide range, say from tens to
>    millions? We propose a self-learning bitmap algorithm (S-bitmap)
>    to answer this question. The S-bitmap is a bitmap obtained via
>    a novel adaptive sampling process, where the bits corresponding
>    to the sampled items are set to 1, and the sampling rates are
>    learned from the number of distinct items already passed and
>    reduced sequentially as more bits are set to 1. A unique property
>    of S-bitmap is that its relative estimation error is truly stabilized,
>    i.e. invariant to unknown cardinalities in a prescribed range. We
>    demonstrate through both theoretical and empirical studies that
>    with a given memory requirement, S-bitmap is not only uniformly
>    reliable but more accurate than state-of-the-art algorithms such
>    as the multiresolution bitmap [6] and Hyper LogLog algorithms
>    [8] under common practice settings.
