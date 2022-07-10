# Search
A search project using AI search algorithms. Created as a university project for Artificial Intelligence course. Study [project_definition.pdf](https://github.com/sajjadroudi/AI-search-project/blob/master/project_definition.pdf) for more details.

### Sample Input
```
6 6
s1 +9 *1 *1 *1 *1
*5 +5 *1 *1 *1 *1
*2 +15 w0 w0 w0 +10
-50 -50 w0 g100 w0 +10
*1 *1 a10 -10 +10 +10
+10 +10 b10 w0 -10 -100
```
### Sample Output
```
exists solution: true
depth: 12
path: (0,0) -> (0,1) -> (1,1) -> (1,0) -> (2,0) -> (3,0) -> (4,0) -> (5,0) -> (5,1) -> (5,2) -> (4,2) -> (4,3) -> (3,3)
```
