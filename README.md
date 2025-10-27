# Food Ordering System

![order-service-hexagonal-section-2-share.png](images/order-service-hexagonal-section-2-share.png)

```bash
sudo apt update
sudo apt install graphviz -y
```

```bash
mvn com.github.ferstl:depgraph-maven-plugin:aggregate \
-DcreateImage=true \
-DreduceEdges=false \
-Dscope=compile \
"-Dincludes=com.food.ordering.system*:*"
)
```


![dependency-graph.png](images/dependency-graph.png)
