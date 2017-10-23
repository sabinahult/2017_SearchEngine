# SPWS17 Repository for Programming Workshop 2017

Benchmarking Result 2017-10-23

		Simple Index		InvertedIndex TreeMap		InvertedIndex HashMap
Tiny txt	51351			17368				13593
Small txt	5310308			20389				39646
Medium txt	142370584		26808				40401

We're choosing the HashMap implementation. Our search words almost all start with letters at the start of the alphabet
and after changing that, the Hashmap was faster. We did not do a full benchmarking again, and so are not updating this table
for now.
