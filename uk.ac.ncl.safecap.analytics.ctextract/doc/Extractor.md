# Safecap Extractor

The extractor plug in to Safecap operates on raw control table data already structured 
into columns and tables. Its purpose is to deal with various syntactic patterns that 
may occur in source column cells.

There are three main cases to consider. A *clean* case is a single value or a value 
list (comma  or whitespace separated); such data is simply transferred over to output data 
set.

A *noisy* case is data interspersed with commentary or formatting syntax. For instance, 
in  

```JSON
S123 YES
```

there is a signal name and a word that is to be ignored. Dashed lines also appear 
as noisy data with an added issue of attributing data to correct containers.

Finally, there is the case of a formula-like construct embedded in a cell. For instance, 
consider `T122 & (T123 or T124 OCC 23sec)`. Here we have a piece of text that needs to be broken 
down into parts describing the top-level conjunction, the nested disjunction and a 
binary operator `OCC`. Also, 23sec may need to be simplified to a numeric literal 23. 

The extractor works in three stage. The first stage translates raw input text 
into a structured (that is, possibly nested) list of *tokens*. A token is a piece of text with attributes 
attached, such as the *token kind*. During this process, the tool makes assumptions 
what can constitute a token, the syntax used for grouping tokens and what must 
be treated as whitespaces.

The following regular expression defines default token values

```JSON
[a-zA-Z\@_\-0-9\<\>\/\+\$\#\:\?\!\.\'\`\~\&\*\+\=\^]+{Suffix}*
```

where `Suffix = "(S)"|"(M)"|"(C)"|"(W)"|"(P)"|"(NP)"|"(SP)"`. By default, normal, curly and square brackets 
are treated as grouping brackets and are expected to be balanced. These define the 
nesting of tokens in a token list. Finally, everything else is treated as a whitespace. 
To give as example, string `T122, (T123 or T124 OCC 23sec)` is parsed into:

```JSON
E[T122] E[&] (E[T123] E[or] E[T124] E[OCC] E[23sec])
```

Syntax `E[abc]` stands for "*token of kind E (Element) with literal contents abc*". 
Note that ( ) brackets from the source raw text became semantic brackets in the parsed 
token list.

Initially, a parsed token list is made of tokens of kind E (Element). The tool does 
not know a priori the meaning of a token and assumes that every 
token is semantically significant. 

Notice that in the example there may be many different combinations 
of track names and ampersand and `or` operator and all of these need to be captured somehow. Writing 
a dedicated extraction rule for every possible combination is not feasible. At 
the same time, writing a conventional parser may not be possible given relative lack of formality and 
consistency. Therefore, the strategy is to consider various cases of token lists but 
also try and minimise their number.

This is where the second stage starts. The purpose of the second stage is to reduce 
the number of distinct data patterns by giving hints which elements can be ignored 
(treated as *noise*), which elements are data (e.g., track name) and which 
ones are a fixed part of syntax (e.g., `or` and `OCC`). 

The sole form of user input to the extractor is in the form 
of *rewrite rules* - statements that define how a list of tokens can be transformed 
into something else. A rewrite rule uses a *pattern* to match the whole of a token list or any part 
of it. If there is a positive match than the matching part is replaced with the rule 
*target*. A target is simply another list of tokens that can be parametrised with values 
provided by pattern matching.  

As an example, we can say that `E[&]` is not a semantically meaningful token. There 
are two ways to do this and they have slightly different effect. The first one is to simply delete 
any such token from every possible token list. The following rewrite rule does exactly this:

```JSON
E[&] -> ()
```

Applied to the example above it produces

```JSON
E[T122] (E[T123] E[or] E[T124] E[OCC] E[23sec])
```

Note that underlying data does not change and the tool is operating on views projecting 
actual data through token lists. If a rewrite rule is deleted, the effect of the 
rule is automatically undone.

Deleting a token from all parsed token lists might be a step too far as it could render 
semantically distinct cases syntactically identical. Instead, one might do

```JSON
E[&] -> N
```

to say that all occurrences of *&* are to be treated as commentary. This rule produces

```JSON
E[T122] N (E[T123] E[or] E[T124] E[OCC] E[23sec])
```

Note that N (Noise) elements lose token literal values and all noise tokens are identical.  Hence,
if there were a slightly different case

```
E[T522] E[and] (E[T523] E[or] E[T524] E[OCC] E[12sec])
```

then rules

1. `E[&] -> N`
2. `E[and] -> N`

would reduce both cases to one *merged* case

```JSON
E N (E E[or] E E[OCC] E)
```

Merging happens automatically when there are two token lists that are distinguished only by 
token literals (text inside square brackets) of Element kind tokens. Rewriting and merging reduces the number of cases one 
has to consider. 

It is useful to capture that `or` and `OCC` are operators and form a part of syntactic 
mark up rather than data itself. For this, we turn corresponding element tokens into operator 
tokens

1. `E[or] -> O[or]`
2. `E[OCC] -> O[OCC]`

One may also add normalisation rules that hide syntactic discrepancies in operator 
syntax, e.g.,:

3. `E[occ] -> O[OCC]`
4. `E[occupied] -> O[OCC]`

Applying these rules we obtain a new merged case

```JSON
E N (E O[or] E O[OCC] E)
```

Unlike element tokens, operator tokens cannot be saved into output data set and an attempt 
to do so would result in an error. Also, during merge, operator literals are used to 
distinguish between cases.

Rewrite rules may use regular expressions to match token literals (for E token kind) and also 
break down or merge token literals during rewrite process. For instance, the occupation time value `23sec` 
can be simplified by the following rule:

```JSON
E:0["([0-9]+)sec"] -> E[$0:1]
```
Applied to the example above, it produces `E[T522], (E[T523] or E[T524] E[OCC] E[12])`. Here, 
$0:1 is a *template* that refers to match group 1 of pattern matching element with index 0. 
One may also choose to keep the scaling unit, say seconds or minutes, in the output expression:
```JSON
E:0["([0-9]+)(sec|min)"] -> E[$0:1] O[$0:2]
```

It is possible to rewrite two or more source tokens into one target token. Consider example
```JSON
E[UTM-] E[BA] E[UKM-] E[BA] E[UPL-] E[CD] 
```

These were supposed to be sub route names but in the source data each name is split 
into two parts. They can be all joined with one rule

```JSON
E["(U.+)-"] E:1["[A-Z]{2}"]-> E[$0:1$1:1]
```

that delivers token list
```JSON
E[UTM-BA] E[UKM-BA] E[UPL-CD] 
```

The final extractor stage is called *reduction* and consists in applying rewrite rules that replace 
matched list parts with strictly smaller (typically empty) token lists. In other words, such rules delete, or reduce, 
chunk by chunk, all the matching token lists. In parallel with rewrite, these rules 
also accumulate matched data that goes into an in-memory database (to be saved into 
XML later). 

Reduction rewrite rules, like normal rewrite rules, can match whole or a 
part of a token list. Hence, even relatively complex cases can be handled with a collection 
of simple reduction rule. To give an example, the time occupation sub-expression `E 
O[OCC] E` can be reduced with rule  

```JSON
{Track} O[OCC] {Time} -> ()
```

where `{Track}` and `{Time}` are data *bins* - named destinations accumulating data 
from every reduction case. 

Note that because of the containing disjunction such a rule could be wrong as it loses the
context in which an instance of the occupation operator occurs. 

There is no explicit construct to express repetition and hence one cannot rewrite `E`, `E E`, `E E 
E` and so on into a single merged case. This aligns with the way rewrite 
systems works with the preference of implicit iteration based on step-wise application of rewrite rules 
over an explicit repetition. 

To reduce a homogenous token list of an arbitrary size one asks to match on the implicit start 
token `S` (the token appears automatically and marks the start of a list) and some next element:

```JSON
S {bin-name} -> ()
```

This rule reduces, by removing list head at one step, any list of element tokens and 
stores result in bin `bin-name`.
 


