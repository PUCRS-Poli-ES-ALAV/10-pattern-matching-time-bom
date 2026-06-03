# Resultados dos experimentos de Pattern Matching

Comando usado para executar os experimentos:

```bash
javac NaivePatternMatching.java RabinKarp.java RabinKarpRollingHash.java KnuthMorrisPratt.java PatternMatchingExperiment.java
java PatternMatchingExperiment
```

## Strings pequenas

Texto: `ABCDCBDCBDACBDABDCBADF`

Padrao: `ADF`

Posicao esperada: `19`

| Implementacao | Tamanho do texto | Tamanho do padrao | Posicao | Esperado | Iteracoes | Instrucoes estimadas | Tempo (ms) | Status |
|---|---:|---:|---:|---:|---:|---:|---:|---|
| Naive | 22 | 3 | 19 | 19 | 45 | 185 | 0.005 | OK |
| Rabin-Karp sem rolling hash | 22 | 3 | 19 | 19 | 86 | 456 | 0.007 | OK |
| Rabin-Karp com rolling hash | 22 | 3 | 19 | 19 | 31 | 219 | 0.007 | OK |
| Knuth-Morris-Pratt | 22 | 3 | 19 | 19 | 24 | 208 | 0.005 | OK |

## Strings grandes

Texto: `600000` caracteres `A` seguidos de `BOM`, totalizando `600003` caracteres.

Padrao: `AAAAABOM`

Posicao esperada: `599995`

| Implementacao | Tamanho do texto | Tamanho do padrao | Posicao | Esperado | Iteracoes | Instrucoes estimadas | Tempo (ms) | Status |
|---|---:|---:|---:|---:|---:|---:|---:|---|
| Naive | 600003 | 8 | 599995 | 599995 | 4199974 | 16799901 | 12.806 | OK |
| Rabin-Karp sem rolling hash | 600003 | 8 | 599995 | 599995 | 5399980 | 27599892 | 11.042 | OK |
| Rabin-Karp com rolling hash | 600003 | 8 | 599995 | 599995 | 600027 | 4800107 | 6.919 | OK |
| Knuth-Morris-Pratt | 600003 | 8 | 599995 | 599995 | 600014 | 6000090 | 8.473 | OK |

## Complexidades no pior caso

| Implementacao | Complexidade |
|---|---|
| Naive | `O(N * M)` |
| Rabin-Karp sem rolling hash | `O(N * M)` |
| Rabin-Karp com rolling hash | `O(N * M)` com muitas colisoes; esperado `O(N + M)` |
| Knuth-Morris-Pratt | `O(N + M)` |

Observacao: as instrucoes sao estimativas contadas manualmente no codigo. O tempo pode variar entre execucoes dependendo da maquina e do estado da JVM.
